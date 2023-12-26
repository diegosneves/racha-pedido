package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.core.*;
import diegosneves.github.rachapedido.dto.InvoiceDTO;
import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.exceptions.CalculateInvoiceException;
import diegosneves.github.rachapedido.mapper.BuilderMapper;
import diegosneves.github.rachapedido.mapper.NotificationEmailMapper;
import diegosneves.github.rachapedido.model.*;
import diegosneves.github.rachapedido.service.contract.EmailServiceContract;
import diegosneves.github.rachapedido.service.contract.InvoiceServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class InvoiceService implements InvoiceServiceContract {

    private static final String CALCULATION_ERROR_MESSAGE = "Houve um problema ao calcular o valor total do pedido.";
    private static final String NULL_PARAMETER_ERROR_MESSAGE = "Um dos parâmetros necessários para a operação de cálculo da fatura está ausente ou nulo.";
    public static final String VOID = "";
    private final EmailServiceContract emailService;

    @Autowired
    public InvoiceService(EmailServiceContract emailService) {
        this.emailService = emailService;
    }

    @Override
    public BillSplit generateInvoice(List<Person> consumers, DiscountType discountType, Double discount, Double deliveryFee, BankAccount selectedBank) {
        this.validateParameters(consumers, discountType, discount, deliveryFee);
        List<Invoice> unpaidInvoices = this.calculateDiscount(consumers, discountType, discount, deliveryFee);
        List<NotificationEmail> notificationEmails = this.preparateSendingEmailNotification(unpaidInvoices, selectedBank);
        return this.statementForPayment(unpaidInvoices, selectedBank, notificationEmails);
    }

    private List<NotificationEmail> preparateSendingEmailNotification(List<Invoice> invoices, BankAccount selectedBank) {
        NotificationEmailMapper mapper = new NotificationEmailMapper();
        List<NotificationEmail> notificationEmailList = invoices.stream().filter(invoice -> invoice.getIsBuyer() == Boolean.FALSE).map(invoice -> BuilderMapper.builderMapper(NotificationEmail.class, invoice, mapper)).toList();
        notificationEmailList.forEach(notificationEmail -> notificationEmail.setLink(selectedBank.paymentLink(notificationEmail.getTotal())));
        notificationEmailList.forEach(notificationEmail -> notificationEmail.setBank(selectedBank.toString()));
        return notificationEmailList; //TODO - Falta Teste para esse metodo
    }

    private BillSplit statementForPayment(List<Invoice> unpaidInvoices, BankAccount selectedBank, List<NotificationEmail> notificationEmails) {
        unpaidInvoices.forEach(invoice -> {
            if (invoice.getIsBuyer() == Boolean.FALSE) {
                invoice.setPaymentLink(selectedBank.paymentLink(invoice.getTotalPayable()));
            } else {
                invoice.setPaymentLink(VOID);
            }
        });
        notificationEmails.forEach(this.emailService::sendPaymentEmail);
        Double total = unpaidInvoices.stream().mapToDouble(Invoice::getTotalPayable).sum();
        List<InvoiceDTO> invoiceDTOs = unpaidInvoices.stream().map(this::convertToInvoiceDTO).toList();
        return BillSplit.builder()
                .invoices(invoiceDTOs)
                .totalPayable(total)
                .build();
    }

    private void validateParameters(List<Person> consumers, DiscountType discountType, Double discount, Double deliveryFee) throws CalculateInvoiceException {
        if (isNull(consumers) || isNull(discountType) || isNull(discount) || isNull(deliveryFee)) {
            throw new CalculateInvoiceException(NULL_PARAMETER_ERROR_MESSAGE);
        }
    }

    private List<Invoice> calculateDiscount(List<Person> persons, DiscountType discountType, Double discount, Double deliveryFee) throws CalculateInvoiceException {
        double total;
        try {
            total = persons.stream().mapToDouble(p -> p.getItems().stream().mapToDouble(Item::getPrice).sum()).sum();
        } catch (Exception e) {
            throw new CalculateInvoiceException(CALCULATION_ERROR_MESSAGE, e);
        }
        Double finalTotal = total;
        return persons.stream().map(dto -> this.calcDiscountForInvoice(dto, discountType, discount, finalTotal, deliveryFee)).toList();
    }

    private Invoice calcDiscountForInvoice(Person person, DiscountType discountType, Double discount, Double total, Double deliveryFee) {
        DiscountStrategy strategy = DiscountStrategy.link(
                new CashDiscountStrategy(),
                new PercentageDiscountStrategy(),
                new NoDiscountStrategy());

        return strategy.calculateDiscount(person, discount, discountType, total, deliveryFee);
    }

    private InvoiceDTO convertToInvoiceDTO(Invoice invoice) {
        return BuilderMapper.builderMapper(InvoiceDTO.class, invoice);
    }

}

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
import diegosneves.github.rachapedido.service.contract.OrderServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class InvoiceService implements InvoiceServiceContract {

    private static final String CALCULATION_ERROR_MESSAGE = "Houve um problema ao calcular o valor total do pedido.";
    private static final String NULL_PARAMETER_ERROR_MESSAGE = "Um dos parâmetros necessários para a operação de cálculo da fatura está ausente ou nulo.";
    private final OrderServiceContract orderService;
    private final EmailServiceContract emailService;

    @Autowired
    public InvoiceService(OrderServiceContract orderService, EmailServiceContract emailService) {
        this.orderService = orderService;
        this.emailService = emailService;
    }

    @Override
    public BillSplit generateInvoice(List<Person> consumers, DiscountType discountType, Double discount, Double deliveryFee, BankAccount selectedBank) {
        this.validateParameters(consumers, discountType, discount, deliveryFee);
        List<Order> closeOrder = this.orderService.closeOrder(consumers);
        List<NotificationEmail> notificationEmails = this.preparateSendingEmailNotification(consumers, selectedBank);
        List<Invoice> unpaidInvoices = this.calculateDiscount(closeOrder, discountType, discount, deliveryFee);
        return this.statementForPayment(unpaidInvoices, selectedBank, notificationEmails);
    }

    private List<NotificationEmail> preparateSendingEmailNotification(List<Person> consumers, BankAccount selectedBank) {
        NotificationEmailMapper mapper = new NotificationEmailMapper();
        List<NotificationEmail> notificationEmailList = consumers.stream().map(person -> BuilderMapper.builderMapper(NotificationEmail.class, person, mapper)).toList();
        notificationEmailList.forEach(notificationEmail -> notificationEmail.setLink(selectedBank.paymentLink()));
        return notificationEmailList; //TODO - Falta Teste para esse metodo
    }

    private BillSplit statementForPayment(List<Invoice> unpaidInvoices, BankAccount selectedBank, List<NotificationEmail> notificationEmails) {
        unpaidInvoices.forEach(invoice -> invoice.setPaymentLink(selectedBank.paymentLink()));
        notificationEmails.forEach(this.emailService::sendPaymentEmail);
        Double total = unpaidInvoices.stream().mapToDouble(Invoice::getTotalPayable).sum();
        return BillSplit.builder()
                .invoices(unpaidInvoices)
                .totalPayable(total)
                .build();
    }

    private void validateParameters(List<Person> consumers, DiscountType discountType, Double discount, Double deliveryFee) throws CalculateInvoiceException {
        if (isNull(consumers) || isNull(discountType) || isNull(discount) || isNull(deliveryFee)) {
            throw new CalculateInvoiceException(NULL_PARAMETER_ERROR_MESSAGE);
        }
    }

    private List<Invoice> calculateDiscount(List<Order> closeOrder, DiscountType discountType, Double discount, Double deliveryFee) throws CalculateInvoiceException {
        List<InvoiceDTO> invoices = closeOrder.stream().map(this::convertToInvoiceDTO).toList();
        double total;
        try {
            total = invoices.stream().mapToDouble(InvoiceDTO::getValueConsumed).sum();
        } catch (Exception e) {
            throw new CalculateInvoiceException(CALCULATION_ERROR_MESSAGE, e);
        }
        Double finalTotal = total;
        return invoices.stream().map(dto -> this.calcDiscountForInvoice(dto, discountType, discount, finalTotal, deliveryFee)).toList();
    }

    private Invoice calcDiscountForInvoice(InvoiceDTO dto, DiscountType discountType, Double discount, Double total, Double deliveryFee){
        DiscountStrategy strategy = DiscountStrategy.link(
                new CashDiscountStrategy(),
                new PercentageDiscountStrategy(),
                new NoDiscountStrategy());

        return strategy.calculateDiscount(dto,discount, discountType, total, deliveryFee);
    }

    private InvoiceDTO convertToInvoiceDTO(Order order) {
        return BuilderMapper.builderMapper(InvoiceDTO.class, order);
    }

}

package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.core.*;
import diegosneves.github.rachapedido.dto.InvoiceDTO;
import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.exceptions.CalculateInvoiceException;
import diegosneves.github.rachapedido.mapper.BuilderMapper;
import diegosneves.github.rachapedido.mapper.BuildingStrategy;
import diegosneves.github.rachapedido.mapper.NotificationEmailMapper;
import diegosneves.github.rachapedido.model.*;
import diegosneves.github.rachapedido.service.contract.EmailServiceContract;
import diegosneves.github.rachapedido.service.contract.InvoiceServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * A classe {@link InvoiceService} é responsável por gerar {@link Invoice faturas} e manipular cálculos e operações relacionados a faturas.
 * Implementa a interface {@link InvoiceServiceContract}.
 *
 * <p>Para gerar uma fatura, a classe recebe uma lista de consumidores, um {@link DiscountType tipo de desconto}, um valor de desconto, uma taxa de entrega e uma {@link BankAccount conta bancária} selecionada
 * como parâmetros. Ela calcula o desconto, prepara notificações para serem enviadas por email, e
 * retorna uma declaração para pagamento com as faturas e o montante total a ser pago.
 *
 * @author diegosneves
 * @see InvoiceServiceContract
 * @see EmailServiceContract
 */
@Service
public class InvoiceService implements InvoiceServiceContract {

    private static final String CALCULATION_ERROR_MESSAGE = "Houve um problema ao calcular o valor total do pedido.";
    private static final String NULL_PARAMETER_ERROR_MESSAGE = "Um dos parâmetros necessários para a operação de cálculo da fatura está ausente ou nulo.";
    private static final String VOID = "";
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

    /**
     * Prepara o envio de {@link NotificationEmail notificações} por email para uma lista de faturas.
     * <p>
     * Este método cria uma lista de e-mails de notificação que estão prontos para serem enviados.
     * Esses e-mails são destinados a notificar os destinatários sobre as faturas que foram emitidas para eles.
     * O link de pagamento para a conta do banco selecionada é anexado a cada e-mail.
     *
     * @param invoices     a lista de {@link Invoice faturas} para as quais as notificações de email serão preparadas
     * @param selectedBank a {@link BankAccount conta bancária} selecionada cujo link de pagamento será anexado aos e-mails de notificação
     * @return uma lista de e-mails de notificação prontos para serem enviados
     */
    private List<NotificationEmail> preparateSendingEmailNotification(List<Invoice> invoices, BankAccount selectedBank) {
        BuildingStrategy<NotificationEmail, Invoice> notificationEmailMapper = new NotificationEmailMapper();
        List<NotificationEmail> notificationEmailList = invoices.stream().filter(invoice -> invoice.getIsBuyer() == Boolean.FALSE).map(notificationEmailMapper::mapper).toList();
        notificationEmailList.forEach(notificationEmail -> notificationEmail.setLink(selectedBank.paymentLink(notificationEmail.getTotal())));
        notificationEmailList.forEach(notificationEmail -> notificationEmail.setBank(selectedBank.toString()));
        return notificationEmailList;
    }

    /**
     * Prepara a declaração para pagamento gerando links de pagamento e enviando emails de pagamento para os destinatários.
     *
     * @param unpaidInvoices     a lista de {@link Invoice faturas} não pagas para as quais a declaração de pagamento será preparada
     * @param selectedBank       a {@link BankAccount conta bancária} selecionada cujo link de pagamento será anexado aos emails de pagamento
     * @param notificationEmails a lista de emails de {@link NotificationEmail notificação} a serem enviados
     * @return um objeto {@link BillSplit} representando a declaração de pagamento
     */
    private BillSplit statementForPayment(List<Invoice> unpaidInvoices, BankAccount selectedBank, List<NotificationEmail> notificationEmails) {
        unpaidInvoices.forEach(invoice -> {
            if (invoice.getIsBuyer() == Boolean.FALSE) {
                invoice.setPaymentLink(selectedBank.paymentLink(invoice.getTotalPayable()));
            } else {
                invoice.setPaymentLink(VOID);
            }
        });
        notificationEmails.forEach(this.emailService::sendEmail);
        Double total = unpaidInvoices.stream().mapToDouble(Invoice::getTotalPayable).sum();
        List<InvoiceDTO> invoiceDTOs = unpaidInvoices.stream().map(this::convertToInvoiceDTO).toList();
        return BillSplit.builder()
                .invoices(invoiceDTOs)
                .totalPayable(total)
                .build();
    }

    /**
     * Valida os parâmetros para a geração de uma fatura.
     *
     * @param consumers    a lista de consumidores
     * @param discountType o tipo de desconto
     * @param discount     o valor do desconto
     * @param deliveryFee  a taxa de entrega
     * @throws CalculateInvoiceException se qualquer um dos parâmetros for nulo
     */
    private void validateParameters(List<Person> consumers, DiscountType discountType, Double discount, Double deliveryFee) throws CalculateInvoiceException {
        if (isNull(consumers) || isNull(discountType) || isNull(discount) || isNull(deliveryFee)) {
            throw new CalculateInvoiceException(NULL_PARAMETER_ERROR_MESSAGE);
        }
    }

    /**
     * Calcula o desconto para uma lista de pessoas aplicando o tipo e o valor do desconto especificados.
     *
     * @param persons      Lista de pessoas para as quais o desconto será calculado
     * @param discountType O tipo de desconto a ser aplicado (DINHEIRO, PERCENTUAL, SEM_DESCONTO)
     * @param discount     A quantia de desconto a ser aplicada
     * @param deliveryFee  A quantia da taxa de entrega
     * @return Lista de faturas com os descontos calculados
     * @throws CalculateInvoiceException Caso ocorra um erro durante o cálculo
     */
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

    /**
     * Calcula o desconto para uma fatura com base na pessoa, tipo de desconto, valor do desconto, total e taxa de entrega.
     *
     * @param person       A pessoa associada à fatura
     * @param discountType O tipo de desconto a ser aplicado (CASH, PERCENTAGE, NO_DISCOUNT)
     * @param discount     O valor do desconto a ser aplicado
     * @param total        O valor total da fatura
     * @param deliveryFee  A taxa de entrega para a fatura
     * @return O objeto de fatura com o desconto calculado
     */
    private Invoice calcDiscountForInvoice(Person person, DiscountType discountType, Double discount, Double total, Double deliveryFee) {
        DiscountStrategy strategy = DiscountStrategy.link(
                new CashDiscountStrategy(),
                new PercentageDiscountStrategy(),
                new NoDiscountStrategy());

        return strategy.calculateDiscount(person, discount, discountType, total, deliveryFee);
    }

    /**
     * Converte um objeto {@link Invoice} para um objeto {@link InvoiceDTO} utilizando a classe de utilidade {@link BuilderMapper}.
     *
     * @param invoice o objeto {@link Invoice} a ser convertido
     * @return o objeto {@link InvoiceDTO} convertido
     */
    private InvoiceDTO convertToInvoiceDTO(Invoice invoice) {
        return BuilderMapper.builderMapper(InvoiceDTO.class, invoice);
    }

}

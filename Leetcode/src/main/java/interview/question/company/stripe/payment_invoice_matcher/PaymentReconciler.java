package interview.question.company.stripe.payment_invoice_matcher;

import interview.question.company.stripe.payment_invoice_matcher.model.Invoice;
import interview.question.company.stripe.payment_invoice_matcher.model.Payment;
import interview.question.company.stripe.payment_invoice_matcher.parser.IInvoiceParser;
import interview.question.company.stripe.payment_invoice_matcher.parser.IPaymentParser;
import interview.question.company.stripe.payment_invoice_matcher.parser.InvoiceParser;
import interview.question.company.stripe.payment_invoice_matcher.parser.PaymentParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class PaymentReconciler {
    private final IPaymentParser paymentParser;
    private final IInvoiceParser invoiceParser;
    private final Map<String, Invoice> invoiceMap;
    private static final String EMPTY_STRING = "";
    private static final String PAYS_OFF = "pays off";
    private static final String SPACE = " ";


    public PaymentReconciler() {
        this.paymentParser = new PaymentParser();
        this.invoiceParser = new InvoiceParser();
        this.invoiceMap = new HashMap<>();
    }

    public String reconcilePayment(String paymentString, List<String> invoices){
        if(paymentString == null || (invoices == null || invoices.isEmpty())){
            System.out.println("nothing to reconcile, returning blank string");
            return  EMPTY_STRING;
        }

        Payment payment = paymentParser.parsePayment(paymentString);
        List<Invoice> invoicesGenerated = invoiceParser.parseInvoices(invoices);
        populateInvoicePool(invoicesGenerated);
        String reconciledString = generateReconciliationString(payment);
        return reconciledString;
    }

    private String generateReconciliationString(Payment payment){ //payment5 pays off 1000 for invoiceC due on 2023-01-30
        String paymentId = payment.getPaymentId();
        String invoicePaidId = payment.getPaidOffInvoiceId();

        if(!invoiceMap.containsKey(invoicePaidId)){
            System.out.println("invoicePaidId : " + invoicePaidId + " not found in invoice pool");
            throw new NoSuchElementException("invoicePaidId not found in invoice pool");
        }

        Invoice associatedInvoice = invoiceMap.get(invoicePaidId);
        Long amountPaidInUnit = payment.getAmountPaidInMinorUnit();
        String dueDate = associatedInvoice.getDueDate();

        StringBuilder reconciledStringBuilder = new StringBuilder();
        reconciledStringBuilder.append(paymentId).append(SPACE)
                .append(PAYS_OFF).append(SPACE)
                .append(amountPaidInUnit).append(SPACE)
                .append("for").append(SPACE)
                .append(invoicePaidId).append(SPACE)
                .append("due on").append(SPACE)
                .append(dueDate);

        return reconciledStringBuilder.toString();
    }

    private void populateInvoicePool(List<Invoice> invoices){
        invoices.stream().collect(Collectors.toMap(Invoice::getInvoiceId, invoice -> invoice));
    }
}

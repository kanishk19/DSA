package interview.question.company.stripe.payment_reconciler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentReconciler {
    static class Payment{
        /**
         * The payment ID (e.g., “payment123”)
         * The payment amount in USD minor units (e.g., $1.00 = 100)
         * The memo line, which always follows the format “Paying off: {INVOICE_ID}”*/

        String paymentId;
        Double amountInMinorUnits;
        String memoLine;
        String paidInvoiceId;

        public Payment(String paymentId, Double amountInMinorUnits, String memoLine, String paidInvoiceId){
            this.paidInvoiceId = paidInvoiceId;
            this.amountInMinorUnits = amountInMinorUnits;
            this.memoLine = memoLine;
            this.paymentId = paymentId;
        }
    }

    static class Invoice{
        /**
         * The invoice ID
         * The due date of the invoice (e.g., “2024-01-01”)
         * The amount due in USD minor units*/

        String invoiceId;
        String dueDate;
        Double amountInMinorUnit;

        public Invoice(String invoiceId, String dueDate, Double amountInMinorUnit){
            this.invoiceId = invoiceId;
            this.dueDate = dueDate;
            this.amountInMinorUnit = amountInMinorUnit;
        }
    }

    private static Map<String, Invoice> invoicePool = new HashMap<>();
    private static final String COMMA_DELIMITER = ",";
    private static final String MEMO_PAYING_OFF_PREFIX = "Paying off: ";
    private static final String SPACE = " ";

    public static void main(String[] args){
        String payment = "payment5,1000,Paying off: invoiceC";
        List<String> invoices = Arrays.asList("invoiceA,2024-01-01,100",
                                                "invoiceB,2024-02-01,200",
                                                "invoiceC,2023-01-30,1000");

        parseInvoices(invoices);
        reconcilePayment(payment);
    }

    public static void reconcilePayment(String paymentString){
        boolean validationResult = validatePaymentString(paymentString);
        if(!validationResult){
            System.out.println("invalid payment string " + paymentString);
            return;
        }

        String[] paymentDetails = paymentString.split(COMMA_DELIMITER);
        Payment payment = buildPayment(paymentDetails);

        reconcilePaymentWithInvoicePool(payment);
    }

    private static void reconcilePaymentWithInvoicePool(Payment payment){
        String paidInvoiceId = payment.paidInvoiceId;
        if(!invoicePool.containsKey(paidInvoiceId)){
            System.out.println("invoiceId not present in invoice pool " + paidInvoiceId);
            return;
        }

        Invoice associatedInvoice = invoicePool.get(paidInvoiceId);
        String reconciledResponse = buildReconciledResponse(payment, associatedInvoice);
        System.out.println(reconciledResponse);
    }

    private static String buildReconciledResponse(Payment payment, Invoice invoice){
        //payment5 pays off 1000 for invoiceC due on 2023-01-30
        String paymentId = payment.paymentId;
        Double amountPaidInMinorUnit = payment.amountInMinorUnits;
        String invoiceId = payment.paidInvoiceId;
        String dueDate = invoice.dueDate;

        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(paymentId).append(SPACE)
                        .append("pays off").append(SPACE)
                        .append(amountPaidInMinorUnit).append(SPACE)
                        .append("for").append(SPACE)
                        .append(invoiceId).append(SPACE)
                        .append("due on").append(SPACE)
                        .append(dueDate);

        return responseBuilder.toString();
    }

    private static Payment buildPayment(String[] paymentDetails){
        String paymentId = paymentDetails[0];
        String amountPaidString = paymentDetails[1];
        String memoLine = paymentDetails[2];
        String invoiceId = parseInvoiceIdFromMemo(memoLine);
        Double amountPaid = null;
        try{
            amountPaid = Double.parseDouble(amountPaidString);
        } catch (NumberFormatException e){
            System.out.println("invalid format for amount string in payment " + amountPaidString);
        }

        Payment payment = new Payment(paymentId, amountPaid, memoLine, invoiceId);
        return payment;
    }

    private static String parseInvoiceIdFromMemo(String memoLine){
        return memoLine.substring(MEMO_PAYING_OFF_PREFIX.length());
    }

    private static boolean validatePaymentString(String payment){
        if(payment == null || payment.length() == 0)
            return false;
        return true;
    }

    public static void parseInvoices(List<String> invoices){
        if(invoices == null || invoices.isEmpty()){
            System.out.println("no invoices to parse");
            return;
        }

        for(String invoiceString: invoices){
            String[] invoiceDetails = invoiceString.split(COMMA_DELIMITER);
            boolean validationResult = validateInvoiceDetails(invoiceDetails);
            if(!validationResult){
                continue;
            }
            Invoice invoice = buildInvoice(invoiceDetails);
            //TODO: another validation for if the invoice id is already present
            invoicePool.put(invoice.invoiceId, invoice);
        }
    }

    private static Invoice buildInvoice(String[] invoiceDetails){
        String invoiceId = invoiceDetails[0];
        String dueDate = invoiceDetails[1];
        String amountString = invoiceDetails[2];
        Double amountInMinorUnit = null;
        try{
            amountInMinorUnit = Double.parseDouble(amountString);
        } catch (NumberFormatException e){
            System.out.println("invalid amount format: " + amountString);
        }
        Invoice invoice = new Invoice(invoiceId, dueDate, amountInMinorUnit);
        return invoice;
    }

    private static boolean validateInvoiceDetails(String[] invoiceDetails){
        if(invoiceDetails == null || invoiceDetails.length != 3){
            System.out.println("invalid invoice details : " + invoiceDetails);
            return false;
        }
        return true;
    }
}

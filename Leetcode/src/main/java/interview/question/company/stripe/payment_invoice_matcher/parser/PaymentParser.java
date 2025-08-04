package interview.question.company.stripe.payment_invoice_matcher.parser;

import interview.question.company.stripe.payment_invoice_matcher.model.Payment;
import interview.question.company.stripe.payment_invoice_matcher.parser.IPaymentParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentParser implements IPaymentParser {

    private static final String MEMO_PREFIX = "Paying off: ";

    @Override
    public Payment parsePayment(String paymentString) { //"payment5,1000,Paying off: invoiceC"
        if(paymentString == null || paymentString.length() == 0){
            System.out.println("invalid string for payment parsing : " + paymentString);
            throw new IllegalArgumentException("invalid payment string");
        }

        String[] paymentParams = paymentString.split(",");
        validatePaymentParams(paymentParams);

        Payment payment = buildPaymentFromPaymentParams(paymentParams);
        return payment;
    }

    private void validatePaymentParams(String[] paymentParams){
        if(paymentParams == null || paymentParams.length != 3){
            System.out.println("invalid payment params " + paymentParams);
            throw new IllegalArgumentException("invalid params in payment String");
        }

        String paymentId = paymentParams[0];
        String amountPaidString = paymentParams[1];
        long amountPaid;
        try{
            amountPaid = Long.parseLong(amountPaidString);
        } catch (NumberFormatException e){
            System.out.println("invalid amount format : " + amountPaidString + " in payment");
            throw new IllegalArgumentException("invalid amount format in payments");
        }

        String memoString = paymentParams[2];
        if(!memoString.startsWith(MEMO_PREFIX)){
            System.out.println("invalid memo line " + memoString);
            throw new IllegalArgumentException("invalid memo line in payment");
        }
    }

    private Payment buildPaymentFromPaymentParams(String[] paymentParams){
        String invoiceId = parseInvoiceIdFromMemo(paymentParams[2]);
        return Payment.builder()
                .paymentId(paymentParams[0])
                .amountPaidInMinorUnit(Long.parseLong(paymentParams[1]))
                .paidOffInvoiceId(invoiceId)
                .build();
    }

    private String parseInvoiceIdFromMemo(String memoString){
        if(memoString == null || memoString.length() == 0 || !memoString.startsWith(MEMO_PREFIX)){
            System.out.println("invalid memo line " + memoString);
            throw new IllegalArgumentException("invalid memo line in payment");
        }
        String invoiceId = memoString.substring(MEMO_PREFIX.length()).trim();
        return invoiceId;
    }

    @Override
    public List<Payment> parsePaymentBatch(List<String> paymentStrings) {
        return paymentStrings.stream().map(this::parsePayment).collect(Collectors.toList());
    }

}

package interview.question.company.stripe.payment_invoice_matcher.parser;

import interview.question.company.stripe.payment_invoice_matcher.model.Payment;

import java.util.List;

public interface IPaymentParser {
    public Payment parsePayment(String paymentString);
    public List<Payment> parsePaymentBatch(List<String> paymentString);
}

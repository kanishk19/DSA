package interview.question.company.stripe.payment_invoice_matcher.parser;

import interview.question.company.stripe.payment_invoice_matcher.model.Invoice;

import java.util.List;

public interface IInvoiceParser {
    public Invoice parseInvoice(String invoice);
    public List<Invoice> parseInvoices(List<String> invoices);

}

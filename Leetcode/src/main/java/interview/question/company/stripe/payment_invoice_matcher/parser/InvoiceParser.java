package interview.question.company.stripe.payment_invoice_matcher.parser;


import interview.question.company.stripe.payment_invoice_matcher.model.Invoice;

import java.util.List;
import java.util.stream.Collectors;

public class InvoiceParser implements IInvoiceParser{
    @Override
    public Invoice parseInvoice(String invoiceString) { //invoiceA,2024-01-01,100
        if(invoiceString == null || invoiceString.length() == 0){
            System.out.println("invalid invoice parsing string: " + invoiceString);
            throw new IllegalArgumentException("invalid invoice parsing string");
        }
        String[] invoiceParams = invoiceString.split(",");
        validateInvoiceString(invoiceParams);
        String invoiceId = invoiceParams[0];
        String dueDate = invoiceParams[1];
        String amountDueString = invoiceParams[2];

        Invoice invoice = buildInvoice(invoiceId, dueDate, amountDueString);
        return invoice;
    }

    private void validateInvoiceString(String[] invoiceParams){
        if(invoiceParams == null || invoiceParams.length != 3){
            System.out.println("invalid invoice params " + invoiceParams);
            throw new IllegalArgumentException("invalid invoice params");
        }

        String invoiceId = invoiceParams[0];
        if(invoiceId == null || invoiceId.length() == 0){
            System.out.println("invalid invoiceId in invoice string: " + invoiceId);
            throw new IllegalArgumentException("invalid invoiceId in invoice string");
        }
        String dueDate = invoiceParams[1];
        if(dueDate == null || dueDate.length() == 0){
            System.out.println("invalid dueDate in invoice string: " + dueDate);
            throw new IllegalArgumentException("invalid dueDate in invoice string");
        }

        String amountDueString = invoiceParams[2];
        try{
            Long.parseLong(amountDueString);
        } catch (NumberFormatException e) {
            System.out.println("invalid amount due in invoice string: " + amountDueString);
            throw new IllegalArgumentException("invalid amount due in invoice string");
        }
    }

    private Invoice buildInvoice(String invoiceId, String dueDate, String amountDueInMinorUnitString){
        Long amountDueInMinorUnit = Long.parseLong(amountDueInMinorUnitString);
        return Invoice.builder()
                .invoiceId(invoiceId)
                .amountInMinorUnit(amountDueInMinorUnit)
                .dueDate(dueDate)
                .build();
    }

    @Override
    public List<Invoice> parseInvoices(List<String> invoices) {
        return invoices.stream().map(this::parseInvoice).collect(Collectors.toList());
    }
}

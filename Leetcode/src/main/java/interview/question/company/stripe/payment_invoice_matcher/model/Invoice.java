package interview.question.company.stripe.payment_invoice_matcher.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Invoice {
    String invoiceId;
    String dueDate;
    Long amountInMinorUnit;
}

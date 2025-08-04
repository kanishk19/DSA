package interview.question.rippling.rule;

import interview.question.rippling.models.Expense;
import interview.question.rippling.models.SellerType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SellerTypeExpenseRule implements IRule {
    private final SellerType sellerType;
    private final String description;
    private final Double limit;


    @Override
    public boolean isViolated(Expense expense) {
        return (sellerTypeValidation(expense.getSellerType()) && limitValidation(expense.getAmountUSD()));
    }

    private boolean limitValidation(Double requestAmountUSD){
        return requestAmountUSD > limit;
    }

    private boolean sellerTypeValidation(SellerType requestSellerType){
        return this.sellerType.equals(requestSellerType);
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String toString(){
        return "SellerTypeExpenseRule: { " +
                    "sellerType: " + this.sellerType.toString() + ", " +
                    "description: " + this.description + ", " +
                    "limit: " + this.limit;
    }
}

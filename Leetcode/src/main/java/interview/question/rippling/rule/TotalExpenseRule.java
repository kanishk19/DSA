package interview.question.rippling.rule;

import interview.question.rippling.models.Expense;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalExpenseRule implements IRule {
    private final String description;
    private final Double limit;

    public TotalExpenseRule(String description, Double limit) {
        this.description = description;
        this.limit = limit;
    }

    @Override
    public boolean isViolated(Expense expense) {
        return expense.getAmountUSD() > limit;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return "TotalExpenseRule: { " +
                    "description : " + this.description + ", " +
                    "limit: " + this.limit.toString();
    }
}

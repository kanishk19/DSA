package interview.question.rippling.rule;

import interview.question.rippling.models.Expense;
import interview.question.rippling.models.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TotalExpenseOnTripRule implements IAggregationRule{
    private final String description;
    private final Double limit;

    @Override
    public boolean isViolated(List<Expense> expenses) {
        double totalSpends = expenses.stream().mapToDouble(Expense :: getAmountUSD).sum();
        return totalSpends > limit;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return "TotalExpenseOnTripRule: { " +
                "description : " + this.description + ", " +
                "limit: " + this.limit.toString();
    }
}

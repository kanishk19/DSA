package interview.question.rippling.rule;

import interview.question.rippling.models.Expense;
import interview.question.rippling.models.ExpenseType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TotalExpenseOnFoodRule implements IAggregationRule{
    private final String description;
    private final Double limit;

    @Override
    public boolean isViolated(List<Expense> expenses) {
        Double foodSpends = expenses.stream().filter(expense -> expense.getExpenseType().equals(ExpenseType.FOOD)).mapToDouble(Expense :: getAmountUSD).sum();
        return foodSpends > limit;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return "TotalExpenseOnFoodRule: { " +
                "description : " + this.description + ", " +
                "limit: " + this.limit.toString();
    }
}

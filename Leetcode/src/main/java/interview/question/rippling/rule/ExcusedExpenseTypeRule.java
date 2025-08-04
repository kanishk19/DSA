package interview.question.rippling.rule;

import interview.question.rippling.models.Expense;
import interview.question.rippling.models.ExpenseType;
import lombok.Builder;

@Builder
public class ExcusedExpenseTypeRule implements IRule{

    private final String description;
    private final ExpenseType expenseType;

    public ExcusedExpenseTypeRule(String description, ExpenseType expenseType) {
        this.description = description;
        this.expenseType = expenseType;
    }

    @Override
    public boolean isViolated(Expense expense) {
        return (expense.getExpenseType().equals(this.expenseType));
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return "ExcusedExpenseRule: { " +
                    "description: " + this.description + ", " +
                    "expenseType: " + this.expenseType;
    }
}

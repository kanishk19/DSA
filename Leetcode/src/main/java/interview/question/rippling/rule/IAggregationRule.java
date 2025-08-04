package interview.question.rippling.rule;

import interview.question.rippling.models.Expense;

import java.util.List;

public interface IAggregationRule extends IRule{
    public boolean isViolated(List<Expense> expenses);

    @Override
    default boolean isViolated(Expense expense){
        throw new UnsupportedOperationException("invalid call for individual validation in aggregation rule" + expense.toString());
    }
}

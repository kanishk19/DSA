package interview.question.rippling.rule;

import interview.question.rippling.models.Expense;

/**
 * Represents a rule that can be applied to an Expense to determine if it is violated.
 */
public interface IRule {
    /**
     * Checks if the given expense violates this rule.
     *
     * @param expense the expense to be validated against the rule
     * @return true if the rule is violated by the expense, false otherwise
     */
    public boolean isViolated(Expense expense);

    /**
     * Provides a human-readable description of the rule.
     *
     * @return the description of the rule
     */
    public String getDescription();
}

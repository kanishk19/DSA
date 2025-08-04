package interview.question.rippling.validator;

import interview.question.rippling.models.Expense;
import interview.question.rippling.rule.IAggregationRule;
import interview.question.rippling.rule.IRule;
import interview.question.rippling.rule.parser.RuleParser;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ExpenseValidator {
    public Map<String, List<String>> evaluateRules(List<IRule> rules, List<Expense> expenses) {
        Map<String, List<String>> violations = new HashMap<>();
        Set<IAggregationRule> aggregationRules = rules.stream().filter(rule -> rule instanceof IAggregationRule).map(rule -> (IAggregationRule)rule).collect(Collectors.toSet());
        Set<IRule> individualRules = rules.stream().filter(rule -> !aggregationRules.contains(rule)).collect(Collectors.toSet());

        //validate individual rules
        for (Expense expense : expenses) {
            List<String> expenseViolations = new ArrayList<>();

            for (IRule rule : individualRules) {
                if (rule.isViolated(expense)) {
                    expenseViolations.add(rule.getDescription());
                }
            }

            if (!expenseViolations.isEmpty()) {
                violations.put(expense.getExpenseId(), expenseViolations);
            }
        }

        //validate aggregation rules
        List<String> aggregateViolations = new ArrayList<>();
        for(IAggregationRule rule: aggregationRules){
            if(rule.isViolated(expenses)) aggregateViolations.add(rule.getDescription());
        }
        if(!aggregateViolations.isEmpty()){
            violations.put("TRIP_SUMMARY_VIOLATIONS", aggregateViolations);
        }
        return violations;
    }

    /**
     * Helper method to parse a list of rule strings into IRule objects.
     * This method is useful for setting up rules in your test cases.
     *
     * @param ruleStrings A list of human-readable rule strings.
     * @return A list of parsed IRule objects.
     */
    public List<IRule> parseRules(List<String> ruleStrings) {
        RuleParser parser = new RuleParser(); // Create a new parser instance
        List<IRule> rules = new ArrayList<>();
        for (String rs : ruleStrings) {
            try {
                rules.add(parser.parse(rs));
            } catch (IllegalArgumentException e) {
                System.err.println("Error parsing rule: " + e.getMessage());
                // In a test environment, you might want to re-throw as a RuntimeException
                // or handle according to your test framework's practices.
            }
        }
        return rules;
    }


}

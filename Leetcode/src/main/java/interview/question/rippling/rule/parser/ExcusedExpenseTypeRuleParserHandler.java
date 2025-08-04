package interview.question.rippling.rule.parser;

import interview.question.rippling.models.ExpenseType;
import interview.question.rippling.rule.ExcusedExpenseTypeRule;
import interview.question.rippling.rule.IRule;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ExcusedExpenseTypeRuleParserHandler implements IRuleParserHandler{
    private final static Pattern PATTERN = Pattern.compile("(\\\\w+) expense type should not be charged");

    @Override
    public boolean canHandle(String ruleDescription) {
        Matcher matcher = PATTERN.matcher(ruleDescription);
        return matcher.matches();
    }

    @Override
    public IRule parseRule(String ruleDescription) {
        Matcher matcher = PATTERN.matcher(ruleDescription);
        if(!matcher.matches()){
            log.error("invalid request for generating ExcusedExpenseRule: {}", ruleDescription);
            throw new IllegalArgumentException("invalid request for generating ExcusedExpenseRule: " + ruleDescription);
        }
        ExpenseType expenseType = ExpenseType.valueOf(matcher.group(1));
        ExcusedExpenseTypeRule excusedExpenseTypeRule = ExcusedExpenseTypeRule.builder().expenseType(expenseType).description(ruleDescription).build();
        log.info("generated ExcusedExpenseRule: {}", excusedExpenseTypeRule);
        return excusedExpenseTypeRule;
    }
}

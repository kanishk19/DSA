package interview.question.rippling.rule.parser;

import interview.question.rippling.rule.IRule;
import interview.question.rippling.rule.TotalExpenseRule;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TotalExpenseRuleParserHandler implements IRuleParserHandler{
    private final static Pattern PATTERN = Pattern.compile("Total expense should not be > (\\\\d+(\\\\.\\\\d+)?)");

    @Override
    public boolean canHandle(String ruleDescription) {
        Matcher matcher = PATTERN.matcher(ruleDescription);
        return matcher.matches();
    }

    @Override
    public IRule parseRule(String ruleDescription) {
        Matcher matcher = PATTERN.matcher(ruleDescription);
        if(!matcher.matches()){
            log.error("invalid request for generating TotalExpenseRule: {}", ruleDescription);
            throw new IllegalArgumentException("invalid request for generating TotalExpenseRule: " + ruleDescription);
        }
        Double amountLimit = Double.parseDouble(matcher.group(1));
        TotalExpenseRule totalExpenseRule = TotalExpenseRule.builder().description(ruleDescription).limit(amountLimit).build();
        log.info("generated totalExpenseRule: {}", totalExpenseRule);
        return totalExpenseRule;
    }
}

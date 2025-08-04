package interview.question.rippling.rule.parser;

import interview.question.rippling.rule.IRule;
import interview.question.rippling.rule.TotalExpenseOnTripRule;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TotalExpenseOnTripRuleParserHandler implements  IRuleParserHandler{
    private final static Pattern PATTERN = Pattern.compile("The total expense on trip should not go beyond (\\\\d+(\\\\.\\\\d+)?) amount");

    @Override
    public boolean canHandle(String ruleDescription) {
        Matcher matcher = PATTERN.matcher(ruleDescription);
        return matcher.matches();
    }

    @Override
    public IRule parseRule(String ruleDescription) {
        Matcher matcher = PATTERN.matcher(ruleDescription);
        if(!matcher.matches()){
            log.error("invalid request for generating TotalExpenseOnTripRule: {}", ruleDescription);
            throw new IllegalArgumentException("invalid request for generating TotalExpenseOnTripRule: " + ruleDescription);
        }
        Double amountLimit = Double.parseDouble(matcher.group(1));
        TotalExpenseOnTripRule totalExpenseOnTripRule = TotalExpenseOnTripRule.builder().limit(amountLimit).description(ruleDescription).build();
        log.info("generate TotalExpenseOnTripRule : {}", totalExpenseOnTripRule);
        return totalExpenseOnTripRule;
    }
}

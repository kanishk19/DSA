package interview.question.rippling.rule.parser;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import interview.question.rippling.rule.IRule;
import interview.question.rippling.rule.TotalExpenseOnFoodRule;
import interview.question.rippling.rule.TotalExpenseOnTripRule;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TotalExpenseOnFoodRuleParserHandler implements  IRuleParserHandler{
    private final static Pattern PATTERN = Pattern.compile("The total expense on food on a trip should not go beyond (\\\\d+(\\\\.\\\\d+)?) amount");

    @Override
    public boolean canHandle(String ruleDescription) {
        Matcher matcher = PATTERN.matcher(ruleDescription);
        return matcher.matches();
    }

    @Override
    public IRule parseRule(String ruleDescription) {
        Matcher matcher = PATTERN.matcher(ruleDescription);
        if(!matcher.matches()){
            log.error("invalid request for generating TotalExpenseOnFoodRuleParserHandler: {}", ruleDescription);
            throw new IllegalArgumentException("invalid request for generating TotalExpenseOnFoodRuleParserHandler: " + ruleDescription);
        }
        Double amountLimit = Double.parseDouble(matcher.group(1));
        TotalExpenseOnFoodRule totalExpenseOnFoodRule = TotalExpenseOnFoodRule.builder().limit(amountLimit).description(ruleDescription).build();
        log.info("generated totalExpenseOnFoodRule : {}", totalExpenseOnFoodRule);
        return totalExpenseOnFoodRule;
    }
}

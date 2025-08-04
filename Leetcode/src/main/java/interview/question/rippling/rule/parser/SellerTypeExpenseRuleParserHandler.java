package interview.question.rippling.rule.parser;

import interview.question.rippling.models.SellerType;
import interview.question.rippling.rule.IRule;
import interview.question.rippling.rule.SellerTypeExpenseRule;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SellerTypeExpenseRuleParserHandler implements IRuleParserHandler{
    private final static Pattern PATTERN = Pattern.compile("Seller type (\\\\w+) should not have expense more than (\\\\d+(\\\\.\\\\d+)?)");

    @Override
    public boolean canHandle(String ruleDescription) {
        Matcher matcher = PATTERN.matcher(ruleDescription);
        return matcher.matches();
    }

    @Override
    public IRule parseRule(String ruleDescription) {
        Matcher matcher = PATTERN.matcher(ruleDescription);
        if(!matcher.matches()){
            log.error("invalid request for generating SellerTypeExpenseRule: {}", ruleDescription);
            throw new IllegalArgumentException("invalid request for generating SellerTypeExpenseRule: " + ruleDescription);
        }
        SellerType sellerType = SellerType.valueOf(matcher.group(1));
        Double amountLimit = Double.parseDouble(matcher.group(2));

        SellerTypeExpenseRule sellerTypeExpenseRule = SellerTypeExpenseRule.builder().sellerType(sellerType).description(ruleDescription).limit(amountLimit).build();
        log.info("generated SellerTypeExpenseRule: {}", sellerTypeExpenseRule);
        return sellerTypeExpenseRule;
    }
}

package interview.question.rippling.rule.parser;

import interview.question.rippling.rule.IRule;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RuleParser {
    private final List<IRuleParserHandler> handlers;

    public RuleParser() {
        this.handlers = new ArrayList<>();
        this.handlers.add(new ExcusedExpenseTypeRuleParserHandler());
        this.handlers.add(new SellerTypeExpenseRuleParserHandler());
        this.handlers.add(new TotalExpenseRuleParserHandler());
        this.handlers.add(new TotalExpenseOnFoodRuleParserHandler());
        this.handlers.add(new TotalExpenseOnTripRuleParserHandler());
    }

    public IRule parse(String ruleString){
        for(IRuleParserHandler ruleParserHandler : handlers){
            if(ruleParserHandler.canHandle(ruleString)){
                log.info("found a match for ruleString: {}, corresponding handler: {}", ruleString, ruleParserHandler.getClass());
                return ruleParserHandler.parseRule(ruleString);
            }
        }
        log.error("invalid request for generating rule: {}", ruleString);
        throw new IllegalArgumentException("invalid request for generating rule: " + ruleString);
    }

}

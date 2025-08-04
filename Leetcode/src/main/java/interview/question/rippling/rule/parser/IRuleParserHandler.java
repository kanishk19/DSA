package interview.question.rippling.rule.parser;

import interview.question.rippling.rule.IRule;

public interface IRuleParserHandler {
    public boolean canHandle(String ruleDescription);
    public IRule parseRule(String ruleDescription);
}

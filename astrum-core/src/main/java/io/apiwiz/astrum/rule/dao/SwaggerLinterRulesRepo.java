package io.apiwiz.astrum.rule.dao;

import io.apiwiz.astrum.rule.model.Rule;

import java.util.List;

public interface SwaggerLinterRulesRepo {

    public List<Rule> getRegexRules(String oasVersion);
    public List<Rule> getOASAssertionRule(String oasVersion);

}

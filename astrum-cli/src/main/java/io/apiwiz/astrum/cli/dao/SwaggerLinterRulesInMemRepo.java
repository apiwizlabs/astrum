package io.apiwiz.astrum.cli.dao;

import io.apiwiz.astrum.core.util.SwaggerLinterUtil;
import io.apiwiz.astrum.rule.dao.SwaggerLinterRulesRepo;
import io.apiwiz.astrum.rule.model.AssertionRule;
import io.apiwiz.astrum.rule.model.RegexRule;
import io.apiwiz.astrum.rule.model.Rule;

import java.util.List;
import java.util.stream.Collectors;

public class SwaggerLinterRulesInMemRepo implements SwaggerLinterRulesRepo {

    private List<Rule> listOfRules;

    public SwaggerLinterRulesInMemRepo(List listOfRules) {
        this.listOfRules = listOfRules;
    }

    @Override
    public List<Rule> getRegexRules(String oasVersion) {
        if(SwaggerLinterUtil.isSwaggerVersion2(oasVersion)) {
            return listOfRules.stream().filter(r -> r.getOasVersion().equalsIgnoreCase("2.0") && r.getRuleType() instanceof RegexRule)
                    .collect(Collectors.toList());
        } else {
            return listOfRules.stream().filter(r -> r.getOasVersion().equalsIgnoreCase("3.0") && r.getRuleType() instanceof RegexRule)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Rule> getOASAssertionRule(String oasVersion) {
        if(SwaggerLinterUtil.isSwaggerVersion2(oasVersion)) {
            return listOfRules.stream().filter(r -> r.getOasVersion().equalsIgnoreCase("2.0") && r.getRuleType() instanceof AssertionRule)
                    .collect(Collectors.toList());
        } else {
            return listOfRules.stream().filter(r -> r.getOasVersion().equalsIgnoreCase("3.0") && r.getRuleType() instanceof AssertionRule)
                    .collect(Collectors.toList());
        }
    }
}

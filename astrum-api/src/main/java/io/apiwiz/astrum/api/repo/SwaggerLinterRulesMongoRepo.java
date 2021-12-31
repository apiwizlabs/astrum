package io.apiwiz.astrum.api.repo;

import io.apiwiz.astrum.core.util.SwaggerLinterUtil;
import io.apiwiz.astrum.rule.dao.SwaggerLinterRulesRepo;
import io.apiwiz.astrum.rule.model.AssertionRule;
import io.apiwiz.astrum.rule.model.RegexRule;
import io.apiwiz.astrum.rule.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SwaggerLinterRulesMongoRepo implements SwaggerLinterRulesRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Rule> getRegexRules(String oasVersion) {
        String version = "3.0";
        if (SwaggerLinterUtil.isSwaggerVersion2(oasVersion)) {
            version = "2.0";
        }
        return mongoTemplate.find(Query.query(Criteria.where("oasVersion").is(version)), Rule.class).stream().filter( r -> r.getRuleType() instanceof RegexRule).collect(Collectors.toList());
    }


    @Override
    public List<Rule> getOASAssertionRule(String oasVersion) {
        String version = "3.0";
        if (SwaggerLinterUtil.isSwaggerVersion2(oasVersion)) {
            version = "2.0";
        }
        return mongoTemplate.find(Query.query(Criteria.where("oasVersion").is(version)), Rule.class).stream().filter( r -> r.getRuleType() instanceof AssertionRule).collect(Collectors.toList());
    }
}

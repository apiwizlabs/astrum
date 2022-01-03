package io.apiwiz.astrum.api.service;

import io.apiwiz.astrum.api.repo.SwaggerLinterRulesMongoRepo;
import io.apiwiz.astrum.api.validator.RequestNotValidException;
import io.apiwiz.astrum.core.impl.SwaggerLinter;
import io.apiwiz.astrum.core.model.SwaggerLintingOutput;
import io.apiwiz.astrum.core.model.SwaggerLintingReport;
import io.apiwiz.astrum.rule.dao.SwaggerLinterRulesRepo;
import io.apiwiz.astrum.rule.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static io.apiwiz.astrum.core.util.SwaggerLinterUtil.formatSwaggerStr;

@Service
public class RuleService {

    private static final String SWAGGER_LINTER_RULES_COLLECTION = "Swagger.LinterRules";


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SwaggerLinterRulesMongoRepo repo;


    @ExceptionHandler(Exception.class)
    public void save(Rule rule) {
        Rule existingRule = mongoTemplate.findOne(Query.query(Criteria.where("name").is(rule.getName())), Rule.class, SWAGGER_LINTER_RULES_COLLECTION);

        if (existingRule == null) {
            isValid(rule.getOasVersion());
            long currentTimeMillis = System.currentTimeMillis();
            rule.setCts(currentTimeMillis);
            rule.setMts(currentTimeMillis);
            mongoTemplate.save(rule, SWAGGER_LINTER_RULES_COLLECTION);
        } else {
            throw new RequestNotValidException(String.format("Rule with name %s already exist ", rule.getName()));
        }
    }

    @ExceptionHandler(Exception.class)
    public void update(Rule rule) {
        Rule existingRule = mongoTemplate.findById(rule.getId(), Rule.class, SWAGGER_LINTER_RULES_COLLECTION);
        if (existingRule != null) {
            existingRule.setMts(System.currentTimeMillis());
            existingRule.setRuleType(rule.getRuleType());
            existingRule.setObjectPath(rule.getObjectPath());
            existingRule.setSeverity(rule.getSeverity());
            existingRule.setDescription(rule.getDescription());
            existingRule.setName(rule.getName());
            existingRule.setOasVersion(rule.getOasVersion());
            mongoTemplate.save(existingRule, SWAGGER_LINTER_RULES_COLLECTION);
        }
    }

    @ExceptionHandler(Exception.class)
    public void delete(String ruleId) {
        Rule rule = mongoTemplate.findById(ruleId, Rule.class);
        if (rule != null) {
            mongoTemplate.remove(Query.query(Criteria.where("_id").is(ruleId)), Rule.class, SWAGGER_LINTER_RULES_COLLECTION);
        } else {
            mongoTemplate.remove(Query.query(Criteria.where("name").is(ruleId)), Rule.class, SWAGGER_LINTER_RULES_COLLECTION);
        }
    }

    @ExceptionHandler(Exception.class)
    public Rule getRuleById(String ruleId) {
        Rule rule = mongoTemplate.findById(ruleId, Rule.class);
        if (rule != null) {
            return rule;
        } else {
            return mongoTemplate.findOne(Query.query(Criteria.where("name").is(ruleId)), Rule.class, SWAGGER_LINTER_RULES_COLLECTION);
        }
    }

    @ExceptionHandler(Exception.class)
    public List<Rule> getAllRules() {
        return mongoTemplate.findAll(Rule.class, SWAGGER_LINTER_RULES_COLLECTION);
    }

    @ExceptionHandler(Exception.class)
    private void isValid(String oasVersion) {
        if (!oasVersion.equals("2.0") && !oasVersion.equals("3.0")) {
            throw new RequestNotValidException("Invalid oasVersion: " + oasVersion);
        }
    }

    public List<SwaggerLintingReport> lint(MultipartFile swaggerFile) {
        String swaggerStr = "";
        try {
            swaggerStr = new String(swaggerFile.getBytes());
        } catch (IOException e) {
            throw new RequestNotValidException("Invalid Swagger File");
        }
        SwaggerLinter linter = new SwaggerLinter(repo);
        return linter.lint(swaggerStr);
    }

    public String format(MultipartFile swaggerFile) {
        try {
            return formatSwaggerStr(new String(swaggerFile.getBytes()));
        } catch (IOException e) {
            throw new RequestNotValidException("Invalid Swagger File");
        }
    }
}


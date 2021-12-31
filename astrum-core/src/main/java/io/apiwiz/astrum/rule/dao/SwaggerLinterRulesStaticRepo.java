package io.apiwiz.astrum.rule.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.apiwiz.astrum.rule.model.*;

import java.util.ArrayList;
import java.util.List;
public class SwaggerLinterRulesStaticRepo implements SwaggerLinterRulesRepo {


	public List<Rule> getRegexRules(String oasVersion) {
		List<Rule> regexRuleList = new ArrayList<>();

		if(null != oasVersion && (oasVersion.startsWith("\"2") || oasVersion.startsWith("2"))) {
			return getOasRegexRules();
		}

		return getOas3RegexRules();

	}

	@Override
	public List<Rule> getOASAssertionRule(String oasVersion) {
		List<Rule> assertionRules = new ArrayList<>();
		Rule rule = new Rule();
		AssertionRule assertionRule = new AssertionRule();
		assertionRule.setOperation(AssertOperation.equalTo);
		assertionRule.setValue("https");
		rule.setRuleType(assertionRule);
		rule.setDescription("All Schemes Should be https");
		rule.setName("AssertRule1");
		rule.setObjectPath("$.schemes.*");
		rule.setSeverity(Severity.WARN);
		assertionRules.add(rule);

		return assertionRules;
	}


	private List<Rule> getOas3RegexRules() {
		List<Rule> regexRuleList = new ArrayList<>();
		Rule regexRule = new Rule();
		regexRule.setName("RegexRule1");
		regexRule.setDescription("server url should not have a trailing slash");
		regexRule.setSeverity(Severity.ERROR);
		RegexRule regexRuleType = new RegexRule();
		regexRuleType.setRegex(".+(?<!\\/)$");
		regexRule.setRuleType(regexRuleType);
		regexRule.setObjectPath("$.servers[*].url");
		regexRuleList.add(regexRule);
		return regexRuleList;
	}


	public static void main(String[] args) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		SwaggerLinterRulesStaticRepo repo = new SwaggerLinterRulesStaticRepo();
		Rule value = repo.getOASAssertionRule().get(0);
		System.out.println(objectMapper.writeValueAsString(value));
	}

	private List<Rule> getOasRegexRules() {
		List<Rule> regexRuleList = new ArrayList<>();
		Rule regexRule = new Rule();
		regexRule.setName("RegexRule1");
		regexRule.setDescription("Operation Id should be Camel Case");
		regexRule.setSeverity(Severity.WARN);
		RegexRule regexRuleType = new RegexRule();
		regexRuleType.setRegex("^[a-zA-Z]*$");
		regexRule.setRuleType(regexRuleType);
		regexRule.setObjectPath("$.paths.*.*.operationId");
		//$.paths.*~
		regexRuleList.add(regexRule);
		return regexRuleList;
	}

	public List<Rule> getOASAssertionRule() {
		List<Rule> assertionRules = new ArrayList<>();
		Rule rule = new Rule();
		AssertionRule assertionRule = new AssertionRule();
		assertionRule.setOperation(AssertOperation.equalTo);
		assertionRule.setValue("https");
		rule.setRuleType(assertionRule);
		rule.setDescription("All Schemes Should be https");
		rule.setName("AssertRule1");
		rule.setObjectPath("$.schemes.*");
		rule.setSeverity(Severity.WARN);
		assertionRules.add(rule);

		return assertionRules;
	}
}

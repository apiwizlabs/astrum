package io.apiwiz.astrum.cli.dao;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.apiwiz.astrum.core.util.SwaggerLinterUtil;
import io.apiwiz.astrum.rule.model.AssertionRule;
import io.apiwiz.astrum.rule.model.RegexRule;
import io.apiwiz.astrum.rule.model.Rule;
import io.apiwiz.astrum.rule.model.SwaggerLinterRuleType;

@RunWith(MockitoJUnitRunner.class)
public class SwaggerLinterRulesInMemRepoTest {
	@Mock
	SwaggerLinterUtil linterUtil;
	@InjectMocks
	SwaggerLinterRulesInMemRepo linterRules;

	@Test
	public void testLinterRule() {
		List<Rule> list = new ArrayList<>();
		Rule rule = Mockito.mock(Rule.class);
		list.add(rule);
		SwaggerLinterRulesInMemRepo memRepo = new SwaggerLinterRulesInMemRepo(list);
	}

	@Test
	public void testGetRegexRules() {
		// Data
		List<Rule> list = new ArrayList<>();
		Rule rule = new Rule();
		rule.setOasVersion("2.0");
		SwaggerLinterRuleType ruleType = new RegexRule();
		rule.setRuleType(ruleType);
		list.add(rule);
		Rule rule2 = new Rule();
		rule2.setOasVersion("4.0");
		list.add(rule2);
		SwaggerLinterRulesInMemRepo memRepo = new SwaggerLinterRulesInMemRepo(list);

		// SystemUnderTest
		List<Rule> regexRules = memRepo.getRegexRules(rule.getOasVersion());

		assertTrue(regexRules.size() == 1);
		assertTrue(regexRules.get(0).getRuleType() instanceof RegexRule);

	}

	@Test
	public void testGetOASAssertionRule() {
		List<Rule> list = new ArrayList<>();
		SwaggerLinterRuleType ruleType = new AssertionRule();
		Rule rule = new Rule();
		rule.setOasVersion("2.0");
		rule.setRuleType(ruleType);
		list.add(rule);
		Rule rule1 = new Rule();
		rule1.setOasVersion("4.0");
		list.add(rule1);
		SwaggerLinterRulesInMemRepo memRepo = new SwaggerLinterRulesInMemRepo(list);
		List<Rule> assertRule = memRepo.getOASAssertionRule(rule.getOasVersion());

		assertTrue(assertRule.size() == 1);
		assertTrue(assertRule.get(0).getRuleType() instanceof AssertionRule);


	}
	
	public static void main(String[] args) {
		String name = "Akash";
		
		Integer intV = 10;

		
	}
}

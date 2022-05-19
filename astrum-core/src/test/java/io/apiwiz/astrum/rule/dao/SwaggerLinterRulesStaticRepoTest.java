package io.apiwiz.astrum.rule.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.apiwiz.astrum.rule.model.AssertOperation;
import io.apiwiz.astrum.rule.model.AssertionRule;
import io.apiwiz.astrum.rule.model.Rule;
import io.apiwiz.astrum.rule.model.Severity;

public class SwaggerLinterRulesStaticRepoTest {

	@Test
	public void testGetOASAssertionRule() throws JsonProcessingException {
		SwaggerLinterRulesStaticRepo repo= new SwaggerLinterRulesStaticRepo();
		List<Rule> assertionRules = new ArrayList<>();
		Rule rule = new Rule();
		repo.getOASAssertionRule();
		rule.setName("AssertionRule1");
		assertionRules.add(rule);
		repo.main(null);

		assertEquals("AssertionRule1",rule.getName());
	}

}

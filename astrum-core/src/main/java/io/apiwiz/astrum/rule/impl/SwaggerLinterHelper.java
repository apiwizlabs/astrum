package io.apiwiz.astrum.rule.impl;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.apiwiz.astrum.core.impl.CustomJsonNodeFactory;
import io.apiwiz.astrum.core.impl.CustomParserFactory;
import io.apiwiz.astrum.core.model.SwaggerLintingReport;
import io.apiwiz.astrum.rule.dao.SwaggerLinterRulesRepo;
import io.apiwiz.astrum.rule.model.AssertOperation;
import io.apiwiz.astrum.rule.model.AssertionRule;
import io.apiwiz.astrum.rule.model.RegexRule;
import io.apiwiz.astrum.rule.model.Rule;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SwaggerLinterHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerLinterHelper.class);

	private SwaggerLinterRulesRepo repo;

	private Configuration configuration;

	private CustomJsonNodeFactory factory;

	public SwaggerLinterHelper(SwaggerLinterRulesRepo repo) {
		this.repo = repo;
		CustomParserFactory customParserFactory = new CustomParserFactory();
		ObjectMapper om = new ObjectMapper(customParserFactory);
		factory = new CustomJsonNodeFactory(om.getDeserializationConfig().getNodeFactory(),
				customParserFactory);
		om.setConfig(om.getDeserializationConfig().with(factory));
		configuration = Configuration.builder().mappingProvider(new JacksonMappingProvider(om))
				.jsonProvider(new JacksonJsonNodeJsonProvider(om)).options(Option.ALWAYS_RETURN_LIST).build();
	}


	public List<SwaggerLintingReport> applyRegexRules(String formattedSwaggerJson, String oasVersion) {
		List<Rule> rules = repo.getRegexRules(oasVersion);
		List<SwaggerLintingReport> reportList = new ArrayList<>();
		for (Rule rule : rules) {
			RegexRule regexRule = (RegexRule) rule.getRuleType();
			String regex = regexRule.getRegex();
			String jsonPath = rule.getObjectPath();

			ArrayNode findings = JsonPath.parse(formattedSwaggerJson, configuration).read(jsonPath);

			for (JsonNode finding : findings) {
				if(!finding.asText().matches(regex)) {
					generateLintingReport(reportList, rule, finding);
				}
			}

		}

		return reportList;
	}

	public List<SwaggerLintingReport> applyAssertionRule(String formattedSwaggerJson, String oasVersion) {
		List<Rule> rules = repo.getOASAssertionRule(oasVersion);
		List<SwaggerLintingReport> reportList = new ArrayList<>();
		for (Rule rule : rules) {
			AssertionRule assertionRule = (AssertionRule) rule.getRuleType();
			AssertOperation operation = assertionRule.getOperation();
			String jsonPath = rule.getObjectPath();

			ArrayNode findings = null;
			try {
				findings = JsonPath.parse(formattedSwaggerJson, configuration).read(jsonPath);
			} catch (PathNotFoundException e) {
				LOGGER.warn("Skipping rule {} as the respective path {} doesn't exist in the swagger", rule.getName(), rule.getObjectPath());
				if(operation.equals(AssertOperation.present)) {
					generateLintingReport(reportList, rule, null);
				}
			}

			if(findings != null) {
				for (JsonNode finding : findings) {

					switch (operation) {
						case equalTo:
							assertEquals(reportList, rule, assertionRule, finding);
							break;
						case present:
							assertPresent(reportList, rule, finding);
							break;
						case absent:
							assertAbsent(reportList, rule, finding);
							break;
						case doesNotContain:
							assertDoesNotContains(reportList, rule, assertionRule, finding);
							break;
						case contains:
							assertContains(reportList, rule, assertionRule, finding);
							break;
						case doesNotMatch:
							assertDoesNotMatch(reportList, rule, assertionRule, finding);
							break;
					}
				}
			}
		}
		return reportList;
	}

	private void assertEquals(List<SwaggerLintingReport> reportList, Rule rule, AssertionRule assertionRule, JsonNode finding) {
		try {
			Assert.assertEquals(finding.asText(), assertionRule.getValue());
		} catch (AssertionError ex) {
			generateLintingReport(reportList, rule, finding);
		}
	}

	private void assertContains(List<SwaggerLintingReport> reportList, Rule rule, AssertionRule assertionRule, JsonNode finding) {
		try {
			Assert.assertTrue(finding.asText().contains(assertionRule.getValue().toString()));
		} catch (AssertionError ex) {
			generateLintingReport(reportList, rule, finding);
		}
	}

	private void assertDoesNotContains(List<SwaggerLintingReport> reportList, Rule rule, AssertionRule assertionRule, JsonNode finding) {
		try {
			Assert.assertFalse(finding.asText().contains(assertionRule.getValue().toString()));
		} catch (AssertionError ex) {
			generateLintingReport(reportList, rule, finding);
		}
	}

	private void assertPresent(List<SwaggerLintingReport> reportList, Rule rule, JsonNode finding) {
		try {
			Assert.assertNotNull(finding.asText());
		} catch (AssertionError ex) {
			generateLintingReport(reportList, rule, finding);
		}
	}

	private void assertAbsent(List<SwaggerLintingReport> reportList, Rule rule, JsonNode finding) {
		try {
			Assert.assertNull(finding.asText());
		} catch (AssertionError ex) {
			generateLintingReport(reportList, rule, finding);
		}
	}

	private void assertDoesNotMatch(List<SwaggerLintingReport> reportList, Rule rule, AssertionRule assertionRule, JsonNode finding) {
		try {
			Assert.assertNotEquals(finding.asText(), assertionRule.getValue().toString());
		} catch (AssertionError ex) {
			generateLintingReport(reportList, rule, finding);
		}
	}

	private void generateLintingReport(List<SwaggerLintingReport> reportList, Rule rule, JsonNode finding) {
		SwaggerLintingReport report = new SwaggerLintingReport();
		report.setRuleName(rule.getName());
		report.setMessage(rule.getDescription());
		report.setSeverity(rule.getSeverity().name().toLowerCase(Locale.ROOT));
		if(finding != null) {
			JsonLocation location = factory.getLocationForNode(finding);
			int lineNum = location.getLineNr();
			report.setLineNumber(lineNum);
		}
		reportList.add(report);
	}

}

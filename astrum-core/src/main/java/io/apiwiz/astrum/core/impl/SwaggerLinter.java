package io.apiwiz.astrum.core.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.apiwiz.astrum.core.converter.SchemaValidationErrorConverter;
import io.apiwiz.astrum.core.converter.SwaggerParserErrorConverter;
import io.apiwiz.astrum.core.exception.SwaggerParseException;
import io.apiwiz.astrum.core.model.SchemaValidationError;
import io.apiwiz.astrum.core.model.SwaggerLintingReport;
import io.apiwiz.astrum.core.model.ValidationResponse;
import io.apiwiz.astrum.rule.dao.SwaggerLinterRulesRepo;
import io.apiwiz.astrum.rule.impl.SwaggerLinterHelper;
import io.swagger.util.Json;
import io.swagger.util.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SwaggerLinter {

	private static final Logger log = LoggerFactory.getLogger(SwaggerLinter.class);

	private ValidateSchema validateSchema = new ValidateSchema();

	private ObjectMapper jsonMapper = Json.mapper();
	private ObjectMapper yamlMapper = Yaml.mapper();

	private SwaggerLinterRulesRepo rulesRepository;

	public SwaggerLinter(SwaggerLinterRulesRepo repo) {
		this.rulesRepository = repo;
	}


	public List<SwaggerLintingReport> lint(String swaggerStr) {
		SwaggerLinterHelper customLinterFactory = new SwaggerLinterHelper(rulesRepository);
		List<SwaggerLintingReport> reportList = new ArrayList<>();

		try {
			JsonNode jsonNode = validateSchema.readNode(swaggerStr);
			String formattedSwaggerJson = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
			ValidationResponse validationResponse = validateSchema.debugByContent(formattedSwaggerJson);


			if(validationResponse.getMessages() != null) {
				for(String message : validationResponse.getMessages()) {
					SwaggerParserErrorConverter converter = new SwaggerParserErrorConverter();
					SwaggerLintingReport report = converter.convert(message, formattedSwaggerJson);
					if(report != null) {
						reportList.add(report);
					}
				}
			}

			if (validationResponse.getSchemaValidationMessages() != null) {
				reportSchemaValidationFailures(formattedSwaggerJson, reportList, validationResponse);
			}

			reportList.addAll(customLinterFactory.applyRegexRules(formattedSwaggerJson, validateSchema.getVersion(jsonNode)));
			reportList.addAll(customLinterFactory.applyAssertionRule(formattedSwaggerJson, validateSchema.getVersion(jsonNode)));

		} catch (SwaggerParseException e) {
			SwaggerLintingReport report = new SwaggerLintingReport();
			report.setSeverity(e.getPm().getLogLevel().name());
			report.setMessage(e.getPm().getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return reportList;

	}
	private void reportSchemaValidationFailures(String swaggerJson, List<SwaggerLintingReport> reportList,
			ValidationResponse validationResponse) throws IOException {
		for ( SchemaValidationError error : validationResponse.getSchemaValidationMessages()) {
			SchemaValidationErrorConverter schemaValidationErrorConverter = new SchemaValidationErrorConverter();
			SwaggerLintingReport swaggerLintingReport = schemaValidationErrorConverter.convert(error, swaggerJson);
			reportList.add(swaggerLintingReport);
		}
	}

}

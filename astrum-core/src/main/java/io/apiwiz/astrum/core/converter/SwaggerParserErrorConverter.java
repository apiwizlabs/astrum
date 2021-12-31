package io.apiwiz.astrum.core.converter;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.apiwiz.astrum.core.impl.CustomJsonNodeFactory;
import io.apiwiz.astrum.core.impl.CustomParserFactory;
import io.apiwiz.astrum.core.model.SwaggerLintingReport;
import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import java.util.stream.Stream;
public class SwaggerParserErrorConverter {

	public static final String LOCATION_MISSING = "is missing";
	public static final String LOCATION_INVALID_TYPE = "is not of type";
	public static final String LOCATION_REPEATED = "is repeated";
	public static final String LOCATION_UNSUPPORTED = "is unsupported";
	public static final String UNDEFINED_PATH = "needs to be defined as a path parameter in path or operation level";

	private Configuration configuration;
	private CustomJsonNodeFactory factory;

	public SwaggerParserErrorConverter() {
		CustomParserFactory customParserFactory = new CustomParserFactory();
		ObjectMapper om = new ObjectMapper(customParserFactory);
		factory = new CustomJsonNodeFactory(om.getDeserializationConfig().getNodeFactory(),
				customParserFactory);
		om.setConfig(om.getDeserializationConfig().with(factory));
		configuration = Configuration.builder().mappingProvider(new JacksonMappingProvider(om))
				.jsonProvider(new JacksonJsonNodeJsonProvider(om)).options(Option.ALWAYS_RETURN_LIST).build();
	}

	public SwaggerLintingReport convert(String message, String swaggerJson) {

		if(!Stream.of(LOCATION_MISSING, LOCATION_INVALID_TYPE, LOCATION_REPEATED, LOCATION_UNSUPPORTED, UNDEFINED_PATH).anyMatch(message::endsWith)) {
			return null;
		}
		SwaggerLintingReport report = new SwaggerLintingReport();
		report.setRuleName("Schema Parse Error");
		report.setMessage(message);
		report.setSeverity("error");

		String jsonPath = extractJsonPath(message);

		ArrayNode findings = findNodeByBruteForce(swaggerJson, jsonPath);

		for (JsonNode finding : findings) {
			JsonLocation location = factory.getLocationForNode(finding);
			int lineNum = location.getLineNr();
			report.setLineNumber(lineNum);
		}


		return report;

	}

	private String extractJsonPath(String message) {
		String[] parseMsg = message.split("\\s+");
		String jsonPath = parseMsg[1];
		if(jsonPath.endsWith(".")) {
			jsonPath = jsonPath.substring(0, jsonPath.lastIndexOf("."));
		}
		if(jsonPath.contains("'")) {
			jsonPath = jsonPath.replaceAll("\'", "");
		}
		return jsonPath;
	}

	private ArrayNode findNodeByBruteForce(String swaggerJson, String jsonPath) {
		DocumentContext document = JsonPath.parse(swaggerJson, configuration);
		try {
			return document.read(jsonPath);
		}catch (PathNotFoundException ex) {
			jsonPath = jsonPath.substring(0, jsonPath.lastIndexOf("."));
			return findNodeByBruteForce(swaggerJson, jsonPath);
		}
	}
}

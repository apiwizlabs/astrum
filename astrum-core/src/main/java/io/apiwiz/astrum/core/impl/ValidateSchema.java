package io.apiwiz.astrum.core.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonschema.core.report.ListProcessingReport;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.apiwiz.astrum.core.exception.SwaggerParseException;
import io.apiwiz.astrum.core.model.SchemaValidationError;
import io.apiwiz.astrum.core.model.ValidationResponse;
import io.swagger.parser.SwaggerParser;
import io.swagger.parser.util.SwaggerDeserializationResult;
import io.swagger.util.Json;
import io.swagger.util.Yaml;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ValidateSchema {

	static final String SCHEMA_FILE = "schema3.json";

	static final String SCHEMA2_FILE = "schema.json";

	static final String INVALID_VERSION = "Deprecated Swagger version.  Please visit http://swagger.io for information on upgrading to Swagger/OpenAPI 2.0 or OpenAPI 3.0";

	static Logger LOGGER = LoggerFactory.getLogger(ValidateSchema.class);
	static long LAST_FETCH = 0;
	static long LAST_FETCH_V3 = 0;
	static ObjectMapper JsonMapper = Json.mapper();
	static ObjectMapper YamlMapper = Yaml.mapper();
	private JsonSchema schemaV2;
	private JsonSchema schemaV3;

	public ValidationResponse debugByContent(String content) throws SwaggerParseException {
		ValidationResponse output = new ValidationResponse();
		JsonNode spec = readNode(content);
		if (spec == null) {
			ProcessingMessage pm = new ProcessingMessage();
			pm.setLogLevel(LogLevel.ERROR);
			pm.setMessage("Unable to read content.  It may be invalid JSON or YAML");
			throw new SwaggerParseException(pm);
		}

		boolean isVersion2 = false;

		// get the version, return deprecated if version 1.x
		String version = getVersion(spec);
		if (version != null && (version.startsWith("\"1") || version.startsWith("1"))) {
			ProcessingMessage pm = new ProcessingMessage();
			pm.setLogLevel(LogLevel.ERROR);
			pm.setMessage(INVALID_VERSION);
			throw new SwaggerParseException(pm);
		} else if (version != null && (version.startsWith("\"2") || version.startsWith("2"))) {
			isVersion2 = true;
			SwaggerDeserializationResult result = null;
			try {
				result = readSwagger(content);
			} catch (Exception e) {
				LOGGER.debug("can't read Swagger contents", e);

				ProcessingMessage pm = new ProcessingMessage();
				pm.setLogLevel(LogLevel.ERROR);
				pm.setMessage("unable to parse Swagger: " + e.getMessage());
				throw new SwaggerParseException(pm);
			}
			if (result != null) {
				for (String message : result.getMessages()) {
					output.addMessage(message);
				}
			}
		} else if (version == null || (version.startsWith("\"3") || version.startsWith("3"))) {
			SwaggerParseResult result = null;
			try {
				result = readOpenApi(content);
			} catch (Exception e) {
				LOGGER.debug("can't read OpenAPI contents", e);

				ProcessingMessage pm = new ProcessingMessage();
				pm.setLogLevel(LogLevel.ERROR);
				pm.setMessage("unable to parse OpenAPI: " + e.getMessage());
				throw new SwaggerParseException(pm);
			}
			if (result != null) {
				for (String message : result.getMessages()) {
					output.addMessage(message);
				}
			}
		}
		// do actual JSON schema validation
		JsonSchema schema = null;
		try {
			schema = getSchema(isVersion2);
			ProcessingReport report = schema.validate(spec, true);
			ListProcessingReport lp = new ListProcessingReport();
			lp.mergeWith(report);
			java.util.Iterator<ProcessingMessage> it = lp.iterator();
			while (it.hasNext()) {
				ProcessingMessage pm = it.next();
				output.addValidationMessage(new SchemaValidationError(pm.asJson()));
			}
		} catch (Exception e) {
			ProcessingMessage pm = new ProcessingMessage();
			pm.setLogLevel(LogLevel.FATAL);
			pm.setMessage("Internal Error");
			throw new SwaggerParseException(pm);
		}

		return output;
	}

	private SwaggerDeserializationResult readSwagger(String content) throws IllegalArgumentException {
		SwaggerParser parser = new SwaggerParser();
		return parser.readWithInfo(content);
	}

	private JsonSchema getSchema(boolean isVersion2) throws Exception {
		if (isVersion2) {
			return getSchemaV2();
		} else {
			return getSchemaV3();
		}
	}

	private SwaggerParseResult readOpenApi(String content) throws IllegalArgumentException {
		OpenAPIV3Parser parser = new OpenAPIV3Parser();
		return parser.readContents(content, null, null);
	}

	private JsonSchema getSchemaV3() throws Exception {
		if (schemaV3 != null && (System.currentTimeMillis() - LAST_FETCH_V3) < 600000) {
			return schemaV3;
		}
		try {
			LOGGER.debug("returning online schema v3");
			LAST_FETCH_V3 = System.currentTimeMillis();
			// schemaV3 = resolveJsonSchema(getUrlContents(SCHEMA_URL), true);
			schemaV3 = resolveJsonSchema(getResourceFileAsString(SCHEMA_FILE), true);
			return schemaV3;
		} catch (Exception e) {
			LOGGER.warn("error fetching schema v3 from GitHub, using local copy");
			// schemaV3 =
			// resolveJsonSchema(getResourceFileAsString(SCHEMA_FILE), true);
			LAST_FETCH_V3 = System.currentTimeMillis();
			return schemaV3;
		}
	}

	private JsonSchema getSchemaV2() throws Exception {
		if (schemaV2 != null && (System.currentTimeMillis() - LAST_FETCH) < 600000) {
			return schemaV2;
		}
		try {
			LOGGER.debug("returning online schema");
			LAST_FETCH = System.currentTimeMillis();
			schemaV2 = resolveJsonSchema(getResourceFileAsString(SCHEMA2_FILE));
			return schemaV2;
		} catch (Exception e) {
			LOGGER.warn("error fetching schema from GitHub, using local copy");
			LAST_FETCH = System.currentTimeMillis();
			return schemaV2;
		}
	}

	private JsonSchema resolveJsonSchema(String schemaAsString) throws Exception {
		return resolveJsonSchema(schemaAsString, false);
	}

	private JsonSchema resolveJsonSchema(String schemaAsString, boolean removeId) throws Exception {
		JsonNode schemaObject = JsonMapper.readTree(schemaAsString);
		if (removeId) {
			ObjectNode oNode = (ObjectNode) schemaObject;
			if (oNode.get("id") != null) {
				oNode.remove("id");
			}
			if (oNode.get("$schema") != null) {
				oNode.remove("$schema");
			}
			if (oNode.get("description") != null) {
				oNode.remove("description");
			}
		}
		JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		return factory.getJsonSchema(schemaObject);
	}

	public JsonNode readNode(String text) {
		try {
			if (text.trim().startsWith("{")) {
				return JsonMapper.readTree(text);
			} else {
				return YamlMapper.readTree(text);
			}
		} catch (IOException e) {
			return null;
		}
	}

	public String getVersion(JsonNode node) {
		if (node == null) {
			return null;
		}

		JsonNode version = node.get("openapi");
		if (version != null) {
			return version.toString();
		}

		version = node.get("swagger");
		if (version != null) {
			return version.toString();
		}
		version = node.get("swaggerVersion");
		if (version != null) {
			return version.toString();
		}

		LOGGER.debug("version not found!");
		return null;
	}

	private String getResourceFileAsString(String fileName) {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
		if (is != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			return reader.lines().collect(Collectors.joining(System.lineSeparator()));
		}
		return null;
	}
}

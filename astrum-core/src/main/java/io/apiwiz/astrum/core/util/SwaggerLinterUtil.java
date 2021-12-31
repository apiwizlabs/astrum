package io.apiwiz.astrum.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.apiwiz.astrum.core.impl.ValidateSchema;

public class SwaggerLinterUtil {

	public static final String ROOT_PATH = "$";
	public static final String DOT = ".";


	public static String getJsonPathFromPointer(String jsonPointer) {
		StringBuilder jsonPath = new StringBuilder();
		jsonPath.append(ROOT_PATH);

		String[] pathElements = jsonPointer.split("/");
		for ( int i = 0; i < pathElements.length; i++) {
			jsonPath.append(pathElements[i]);
			if( i != pathElements.length - 1) {
				jsonPath.append(DOT);
			}
		}
		return jsonPath.toString();
	}

	public static boolean isSwaggerVersion2(String version) {
		if (version != null && (version.startsWith("\"2") || version.startsWith("2"))) {
			return true;
		} else {
			return false;
		}
	}

	public static String getVersion(JsonNode node) {
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
		return null;
	}


	public static String formatSwaggerStr(String swaggerString) throws JsonProcessingException {
		ValidateSchema validateSchema = new ValidateSchema();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = validateSchema.readNode(swaggerString);
		String formattedSwaggerJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
		return formattedSwaggerJson;
	}



}

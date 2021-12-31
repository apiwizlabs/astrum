package io.apiwiz.astrum.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)

public class LinterResponse {

	private List<SwaggerLintingOutput> swaggerLintingOutputs = null;
}

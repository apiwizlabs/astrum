package io.apiwiz.astrum.core.converter;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jayway.jsonpath.*;
import io.apiwiz.astrum.core.impl.CustomJsonNodeFactory;
import io.apiwiz.astrum.core.impl.CustomParserFactory;
import io.apiwiz.astrum.core.model.Instance;
import io.apiwiz.astrum.core.model.SchemaValidationError;
import io.apiwiz.astrum.core.model.SwaggerLintingReport;
import io.apiwiz.astrum.core.util.SwaggerLinterUtil;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

public class SchemaValidationErrorConverter {

    private Configuration configuration;
    private CustomJsonNodeFactory factory;

    public SchemaValidationErrorConverter() {
        CustomParserFactory customParserFactory = new CustomParserFactory();
        ObjectMapper om = new ObjectMapper(customParserFactory);
        factory = new CustomJsonNodeFactory(om.getDeserializationConfig().getNodeFactory(),
                customParserFactory);
        om.setConfig(om.getDeserializationConfig().with(factory));
        configuration = Configuration.builder().mappingProvider(new JacksonMappingProvider(om))
                .jsonProvider(new JacksonJsonNodeJsonProvider(om)).options(Option.ALWAYS_RETURN_LIST).build();
    }

    public SwaggerLintingReport convert(SchemaValidationError error, String swaggerJson) {
        SwaggerLintingReport report = new SwaggerLintingReport();
        report.setRuleName("Schema Validation Error");
        report.setMessage(error.getMessage());
        report.setSeverity(error.getLevel());

        DocumentContext document = JsonPath.parse(swaggerJson, configuration);
        Instance instance = error.getInstance();
        if (instance != null) {
            try{
            ArrayNode findings = document.read(SwaggerLinterUtil.getJsonPathFromPointer(instance.getPointer()));

            for (JsonNode finding : findings) {
                JsonLocation location = factory.getLocationForNode(finding);
                int lineNum = location.getLineNr();
                report.setLineNumber(lineNum);
            }
        } catch (PathNotFoundException p) {

            }
        }

        return report;
    }
}

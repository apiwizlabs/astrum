package io.apiwiz.astrum.rule.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AssertionRule.class, name = "ASSERT"),
        @JsonSubTypes.Type(value = RegexRule.class, name = "REGEX")})
public class SwaggerLinterRuleType {


}

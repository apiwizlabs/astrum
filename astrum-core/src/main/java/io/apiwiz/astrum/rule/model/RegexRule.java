package io.apiwiz.astrum.rule.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("REGEX")
public class RegexRule extends SwaggerLinterRuleType {

	private String operation;

	private String regex;

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}

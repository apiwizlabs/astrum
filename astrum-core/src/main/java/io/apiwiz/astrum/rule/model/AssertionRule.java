package io.apiwiz.astrum.rule.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ASSERT")
public class AssertionRule extends SwaggerLinterRuleType {

	private AssertOperation operation;
	private Object value;

	public AssertOperation getOperation() {
		return operation;
	}

	public void setOperation(AssertOperation operation) {
		this.operation = operation;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}


}

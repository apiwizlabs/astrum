package io.apiwiz.astrum.api.model;

public class ErrorResponse {
	private String userMessage;

	private String errors;

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public ErrorResponse(String userMessage, String string) {
		super();
		this.userMessage = userMessage;
		this.errors = string;
	}

}

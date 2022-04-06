package io.apiwiz.astrum.api.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ErrorResponseTest {
	
	public String userMessage="Message";

	public String errors="Errors";
	
	ErrorResponse errorResponse=new ErrorResponse(userMessage, errors);

	@Test
	void testGetUserMessage() {
		errorResponse.getUserMessage();
	}

	@Test
	void testSetUserMessage() {
		errorResponse.setUserMessage(userMessage);
	}

	@Test
	void testGetErrors() {
		errorResponse.getErrors();
	}

	@Test
	void testSetErrors() {
		errorResponse.setErrors(errors);
	}



}

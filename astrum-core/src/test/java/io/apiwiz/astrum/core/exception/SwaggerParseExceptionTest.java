package io.apiwiz.astrum.core.exception;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.fge.jsonschema.core.report.ProcessingMessage;

public class SwaggerParseExceptionTest {

	@Test
	public void testSwaggerParseException() {
		ProcessingMessage pm=new ProcessingMessage();
		SwaggerParseException exception=new SwaggerParseException(pm);
		exception.getPm();
		
		
	}


}

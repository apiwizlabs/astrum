package io.apiwiz.astrum.core.exception;

import com.github.fge.jsonschema.core.report.ProcessingMessage;
public class SwaggerParseException extends Exception{

	private ProcessingMessage pm;

	public SwaggerParseException(ProcessingMessage pm) {
		super(pm.asJson().asText());
		this.pm = pm;
	}



	public ProcessingMessage getPm() {
		return pm;
	}
}

package io.apiwiz.astrum.api.validator;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestNotValidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8687350188812699710L;
	
	public RequestNotValidException(String message) {
		super(message);
	}

}

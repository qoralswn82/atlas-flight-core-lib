package org.atlas.flight.core.exception;

import lombok.Getter;
import org.atlas.flight.core.message.ResponseCodeInterface;

@Getter
public class ApiException extends RuntimeException {
	private final ResponseCodeInterface responseCode;
	
	public ApiException(ResponseCodeInterface code) {
		this.responseCode = code;
	}
}

package org.atlas.flight.core.message;

public interface ResponseCodeInterface {
	ResponseCode getResponseCode();
	
	default String getResultCode() {
		return getResponseCode().getResultCode();
	}
}

package org.atlas.flight.core.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseCode {
	private final String resultCode;
	private final String resultMessage;
	private final String resultDetailMessage;
}

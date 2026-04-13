package org.atlas.flight.core.util.encrypt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 암복호화 사용될 비밀키
 * 각 기능 모듈 application.yaml
 * crypto:
 *   secret-key: {{my-super-secret-key-1234}}
 * */
@Getter
@Setter
@Builder
@ConfigurationProperties(prefix = "crypto")
public class CryptoProperties {
	private String secretKey;
}
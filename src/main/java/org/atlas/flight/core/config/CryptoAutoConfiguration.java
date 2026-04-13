package org.atlas.flight.core.config;

import org.atlas.flight.core.util.encrypt.CryptoConverter;
import org.atlas.flight.core.util.encrypt.CryptoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CryptoProperties.class)
public class CryptoAutoConfiguration {

	@Bean
	public CryptoConverter cryptoConverter(CryptoProperties props) {
		return new CryptoConverter(props);
	}
}
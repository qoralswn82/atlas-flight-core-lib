package org.atlas.flight.core.util.encrypt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CryptoConverterTest {

	private static final String SECRET_KEY = "wj8rPzht9ajdenro";

	private CryptoConverter converter;

	@BeforeEach
	void setUp() {
		CryptoProperties properties = CryptoProperties.builder()
				.secretKey(SECRET_KEY)
				.build();
		converter = new CryptoConverter(properties);
	}

	@Test
	void convertToDatabaseColumn_then_convertToEntityAttribute_roundTrips() {
		String plain = "hello";
		String stored = converter.convertToDatabaseColumn(plain);
		assertEquals(plain, converter.convertToEntityAttribute(stored));
	}

	@Test
	void convertToDatabaseColumn_null_returnsNull() {
		assertNull(converter.convertToDatabaseColumn(null));
	}

	@Test
	void convertToEntityAttribute_null_returnsNull() {
		assertNull(converter.convertToEntityAttribute(null));
	}
}

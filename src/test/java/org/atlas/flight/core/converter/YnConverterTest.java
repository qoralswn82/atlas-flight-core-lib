package org.atlas.flight.core.converter;

import org.atlas.flight.core.enums.Yn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class YnConverterTest {

	private final YnConverter converter = new YnConverter();

	@Test
	void convertToDatabaseColumn_mapsEnumToCode() {
		assertEquals("Y", converter.convertToDatabaseColumn(Yn.Y));
		assertEquals("N", converter.convertToDatabaseColumn(Yn.N));
	}

	@Test
	void convertToEntityAttribute_mapsCodeToEnum() {
		assertEquals(Yn.Y, converter.convertToEntityAttribute("Y"));
		assertEquals(Yn.N, converter.convertToEntityAttribute("N"));
	}

	@Test
	void convertToDatabaseColumn_null_returnsNull() {
		assertNull(converter.convertToDatabaseColumn(null));
	}

	@Test
	void convertToEntityAttribute_null_returnsNull() {
		assertNull(converter.convertToEntityAttribute(null));
	}

	@Test
	void convertToEntityAttribute_unexpectedValue_throws() {
		assertThrows(IllegalArgumentException.class, () -> converter.convertToEntityAttribute("X"));
	}
}

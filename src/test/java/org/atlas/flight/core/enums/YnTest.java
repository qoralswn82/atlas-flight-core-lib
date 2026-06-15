package org.atlas.flight.core.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class YnTest {

	@Test
	void of_mapsBoolean() {
		assertEquals(Yn.Y, Yn.of(true));
		assertEquals(Yn.N, Yn.of(false));
	}

	@Test
	void isYes_isNo() {
		assertTrue(Yn.Y.isYes());
		assertFalse(Yn.Y.isNo());
		assertTrue(Yn.N.isNo());
		assertFalse(Yn.N.isYes());
	}

	@Test
	void toBoolean_mapsEnum() {
		assertTrue(Yn.Y.toBoolean());
		assertFalse(Yn.N.toBoolean());
	}
}

package org.atlas.flight.core.enums;

/**
 * 전역 Y/N 플래그 타입.
 *
 * <p>DB 컬럼(VARCHAR(1) "Y"/"N")과 1:1 대응하며, {@link org.atlas.flight.core.converter.YnConverter}
 * 를 통해 엔티티 속성으로 매핑한다. 상수 이름 자체가 곧 저장값("Y"/"N")이므로 별도 value 필드는 두지 않는다.
 */
public enum Yn {
	Y,
	N;

	/** 값이 Y이면 true. */
	public boolean isYes() {
		return this == Y;
	}

	/** 값이 N이면 true. */
	public boolean isNo() {
		return this == N;
	}

	/** boolean → Yn (true = Y, false = N). */
	public static Yn of(boolean value) {
		return value ? Y : N;
	}

	/** Yn → boolean (Y = true, N = false). */
	public boolean toBoolean() {
		return this == Y;
	}
}

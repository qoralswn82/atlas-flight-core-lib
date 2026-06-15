package org.atlas.flight.core.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.atlas.flight.core.enums.Yn;

/**
 * {@link Yn} ↔ DB 문자열("Y"/"N") JPA 컨버터.
 *
 * <p>무상태이므로 Spring 빈 등록이 필요 없다. 엔티티에서 명시적으로 지정해 사용한다 —
 * {@link org.atlas.flight.core.util.encrypt.CryptoConverter} 와 동일한 방식이다.
 *
 * <pre>
 * &#64;Convert(converter = YnConverter.class)
 * &#64;Column(name = "DEL_YN", length = 1, nullable = false)
 * private Yn delYn;
 * </pre>
 */
@Converter
public class YnConverter implements AttributeConverter<Yn, String> {

	@Override
	public String convertToDatabaseColumn(Yn attribute) {
		return attribute == null ? null : attribute.name();
	}

	@Override
	public Yn convertToEntityAttribute(String dbData) {
		return dbData == null ? null : Yn.valueOf(dbData);
	}
}

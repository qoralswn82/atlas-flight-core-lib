package org.atlas.flight.core.util.encrypt;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * JPA 암복호화 엔티티 converter
 *
 *
 * ex)
 *
 * @Entity
 * public class Member {
 *
 *     @Convert(converter = CryptoConverter.class)
 *     private String email;
 *
 *     @Convert(converter = CryptoConverter.class)
 *     private String password;
 * }
 *
 * */
@Component
@Converter
@RequiredArgsConstructor
public class CryptoConverter implements AttributeConverter<String, String> {


	private final CryptoProperties properties;
	@Override
	public String convertToDatabaseColumn(String attribute) {
		if (attribute == null) return null;
		return CryptoUtil.AES_Encrypt(properties.getSecretKey(), attribute);
	}

	// 조회 후 → 복호화
	@Override
	public String convertToEntityAttribute(String dbData) {
		if (dbData == null) return null;
		return CryptoUtil.AES_Decrypt(properties.getSecretKey(), dbData);
	}
}
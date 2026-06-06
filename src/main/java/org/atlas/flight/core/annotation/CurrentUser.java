package org.atlas.flight.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Gateway가 검증한 JWT에서 추출한 사용자 아이디를 주입받기 위한 어노테이션.
 * Gateway는 검증 후 X-User-Id 헤더로 userId를 전달한다.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}

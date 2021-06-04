package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Parameter = { Request, Response }
 * Return Type = Response
 * 모든 EndPoint는 위 조건을 만족해야 합니다.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface EndPoint
{
    String value() default "/index";
}

package com.rkecom.json.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target ( ElementType.FIELD )
@Retention ( RetentionPolicy.RUNTIME )
public @interface JsonConditionalInclude {
    boolean excludeNull() default false;
    boolean excludeEmpty() default false;
}

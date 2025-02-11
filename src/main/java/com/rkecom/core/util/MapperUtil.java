package com.rkecom.core.util;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Consumer;

public class MapperUtil {
    private MapperUtil() {
    }

    public static < T > void updateField( T currentValue, T newValue, Consumer<T> setter ) {
        if(Objects.nonNull ( newValue ) && !areEqual ( currentValue, newValue ) ) {
            setter.accept ( newValue );
        }
    }

    private static <T> boolean areEqual(T currentValue, T newValue) {
        if (currentValue == newValue) {
            return true; // Same reference, no update needed
        }
        if (currentValue == null || newValue == null) {
            return false; // One is null, meaning they are not equal
        }
        if (currentValue instanceof BigDecimal c && newValue instanceof BigDecimal n) {
            return c.compareTo(n) == 0;
        }
        if (currentValue instanceof Number c && newValue instanceof Number n) {
            return c.doubleValue() == n.doubleValue();
        }
        return Objects.equals(currentValue, newValue);
    }
}

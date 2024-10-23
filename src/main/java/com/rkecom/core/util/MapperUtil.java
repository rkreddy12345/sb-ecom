package com.rkecom.core.util;

import java.util.Objects;
import java.util.function.Consumer;

public class MapperUtil {
    private MapperUtil() {
    }

    public static < T > void updateField( T currentValue, T newValue, Consumer<T> setter ) {
        if(Objects.nonNull ( newValue ) && !newValue.equals ( currentValue ) ) {
            setter.accept ( newValue );
        }
    }
}

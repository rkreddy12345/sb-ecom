package com.rkecom.core.util;

import java.util.Objects;
import java.util.function.Consumer;

public class MapperUtil {
    private MapperUtil() {
    }

    public static < T > void updateField( T newValue, Consumer<T> setter ) {
        if(Objects.nonNull (newValue)){
            setter.accept (newValue);
        }
    }

}

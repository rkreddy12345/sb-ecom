package com.rkecom.json.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.rkecom.json.util.annotation.JsonExcludeNullAndEmpty;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

//custom serializer to handle map's null, empty values
public class MapEntryValueSerializer<K, V> extends JsonSerializer<Map<K, V>> implements ContextualSerializer {

    private final boolean excludeNull;
    private final boolean excludeEmpty;

    // Default constructor without context
    public MapEntryValueSerializer() {
        this.excludeNull = false;
        this.excludeEmpty = false;
    }

    // Constructor with context settings
    public MapEntryValueSerializer(boolean excludeNull, boolean excludeEmpty) {
        this.excludeNull = excludeNull;
        this.excludeEmpty = excludeEmpty;
    }

    @Override
    public void serialize(Map<K, V> map, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();

            boolean includeValue = !(excludeNull && Objects.isNull(value))
                    && !(excludeEmpty && value instanceof String valueString && valueString.isEmpty());

            if (includeValue) {
                gen.writeObjectField(key.toString(), value);
            }
        }
        gen.writeEndObject();
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        if (property != null && property.getAnnotation(JsonExcludeNullAndEmpty.class) != null) {
            JsonExcludeNullAndEmpty annotation = property.getAnnotation(JsonExcludeNullAndEmpty.class);
            return new MapEntryValueSerializer<>(annotation.excludeNull(), annotation.excludeEmpty());
        }
        return new MapEntryValueSerializer<>();
    }
}

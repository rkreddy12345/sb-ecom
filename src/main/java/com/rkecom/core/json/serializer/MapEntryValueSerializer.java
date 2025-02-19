package com.rkecom.core.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.rkecom.core.json.annotation.JsonConditionalInclude;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Custom serializer for {@link Map} objects that selectively excludes null and empty values
 * based on the configuration provided through annotations.
 *<br>
 * This serializer checks the values of map entries and determines if they should be included in the serialized output.
 * It excludes entries where the value is either null or empty, according to the specified conditions.
 */
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
                    && !(excludeEmpty && isEmptyValue(value));

            if (includeValue) {
                gen.writeObjectField(key.toString(), value);
            }
        }
        gen.writeEndObject();
    }

    /**
     * Utility method to determine if a value is considered "empty."
     */
    private boolean isEmptyValue(Object value) {
        if(Objects.isNull(value)) {
            return true;
        }
        return switch (value){
            case String string->string.isEmpty();
            case Object[] array->array.length==0;
            case Collection<?> collection->collection.isEmpty();
            case Map<?, ?> map->map.isEmpty();
            default -> false;
        };
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        if (property != null && property.getAnnotation(JsonConditionalInclude.class) != null) {
            JsonConditionalInclude annotation = property.getAnnotation(JsonConditionalInclude.class);
            return new MapEntryValueSerializer<>(annotation.excludeNull(), annotation.excludeEmpty());
        }
        return new MapEntryValueSerializer<>();
    }
}

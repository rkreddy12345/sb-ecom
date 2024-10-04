package com.rkecom.json.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class NonNullMapValuesSerializer< K, V > extends JsonSerializer< Map <K, V> > {

    @Override
    public void serialize ( Map < K, V > map, JsonGenerator gen, SerializerProvider serializers ) throws IOException {
        gen.writeStartObject();
        for ( Map.Entry < K, V > entry : map.entrySet() ) {
            if ( entry.getValue() != null ) {
                gen.writeObjectField ( entry.getKey().toString(), entry.getValue() );
            }
        }
        gen.writeEndObject();
    }
}

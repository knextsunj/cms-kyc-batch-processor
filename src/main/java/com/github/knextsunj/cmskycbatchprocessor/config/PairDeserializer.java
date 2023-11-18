package com.github.knextsunj.cmskycbatchprocessor.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;

public class PairDeserializer extends JsonDeserializer<Pair> {
    @Override
    public Pair deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        p.nextValue();
        String left= p.getValueAsString();
        p.nextValue();
        String right= p.getValueAsString();
        return Pair.of(left, right);
    }
}

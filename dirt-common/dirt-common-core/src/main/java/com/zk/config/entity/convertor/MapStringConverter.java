package com.zk.config.entity.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

@Converter
public class MapStringConverter implements AttributeConverter<Map, String> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map map) {
        try {
            String s = objectMapper.writeValueAsString(map);
            return s;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, new TypeReference<Map>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new HashMap();
    }
}
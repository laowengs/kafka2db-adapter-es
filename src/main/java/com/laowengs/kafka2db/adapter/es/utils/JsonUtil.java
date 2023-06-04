package com.laowengs.kafka2db.adapter.es.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class JsonUtil {
    private static ObjectMapper objectMapper =new JsonMapper();
    public static <T> T convertObj(String jsonString,Class<T> tClass){
        try {
            return objectMapper.readValue(jsonString, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json序列化异常:"+jsonString,e);
        }
    }


}

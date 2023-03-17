package com.deng.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author :deng
 * @version :1.0
 * @description : 用户名序列化器，对名字做处理
 * @since :1.8
 */
public class UsernameSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        // 使用** 替换 用户名，用户名替换
        jsonGenerator.writeString(s.substring(0, 4) + "****" + s.substring(8));
    }

}
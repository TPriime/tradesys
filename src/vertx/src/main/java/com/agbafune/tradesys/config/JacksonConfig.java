package com.agbafune.tradesys.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.vertx.core.json.jackson.DatabindCodec;

public class JacksonConfig {
    public static void configure() {
        ObjectMapper mapper = DatabindCodec.mapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

//        ObjectMapper prettyMapper = DatabindCodec.prettyMapper();
//        prettyMapper.registerModule(new JavaTimeModule());
//        prettyMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}

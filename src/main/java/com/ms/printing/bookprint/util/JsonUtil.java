package com.ms.printing.bookprint.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

@Component
public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    @Resource
    private ObjectMapperProvider objectMapperProvider;

    public <T> T parse(String json, Class<T> clazz) {
        try {
            if (StringUtils.isNotBlank(json)) {
                return objectMapperProvider.getObjectMapper().readValue(json, clazz);
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to parse json string : {}", json, e);
        }
        return null;
    }

    public <T> String toJson(T object) {
        try {
            if (!Objects.isNull(object)) {
                return objectMapperProvider.getObjectMapper().writeValueAsString(object);
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to convert object to json for : {}", object.toString(), e);
        }
        return null;
    }

}

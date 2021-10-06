package com.ms.printing.bookprint.converters;

import com.ms.printing.bookprint.util.JsonUtil;

import javax.annotation.Resource;

public abstract class AbstractConverter<S, D> implements CustomMapperConverter<S, D> {

    @Resource
    protected JsonUtil jsonUtil;

    protected D transform(S source, Class<D> clazz) {
        String jsonStr = jsonUtil.toJson(source);
        return jsonUtil.parse(jsonStr, clazz);
    }
}

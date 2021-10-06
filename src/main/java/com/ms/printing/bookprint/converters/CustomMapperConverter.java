package com.ms.printing.bookprint.converters;

public interface CustomMapperConverter<S, D> {
    boolean canConvert(Object source, Class<?> clazz);
    D convert(S source);
}

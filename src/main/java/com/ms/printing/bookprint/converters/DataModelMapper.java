package com.ms.printing.bookprint.converters;

import com.ms.printing.bookprint.exceptions.BookPrintException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DataModelMapper {

    @Resource
    private List<CustomMapperConverter> customMapperConverters;

    public <S, D> D map(S source, Class<D> clazz) {
        for (CustomMapperConverter converter : customMapperConverters) {
            if (converter.canConvert(source, clazz)) {
                return (D) converter.convert(source);
            }
        }
        throw new BookPrintException("Invalid data model types conversion from " + source + " to " + clazz);
    }
}

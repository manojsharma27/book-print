package com.ms.printing.bookprint.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Brochure extends Product {

    public static final String TYPE = "Brochure";

    private int folds;

    @Override
    public String getType() {
        return TYPE;
    }
}

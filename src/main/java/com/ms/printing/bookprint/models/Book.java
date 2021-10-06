package com.ms.printing.bookprint.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.printing.bookprint.enums.BookType;
import com.ms.printing.bookprint.enums.CoverType;
import com.ms.printing.bookprint.enums.PaperType;
import com.ms.printing.bookprint.enums.Size;
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
public class Book extends Product {

    public static final String TYPE = "Book";

    private BookType bookType;
    private Binding binding;
    private PaperType paperType;
    private CoverType coverType;
    private Size size;
    private int pages;

    @JsonIgnore
    public String getType() {
        return TYPE;
    }
}

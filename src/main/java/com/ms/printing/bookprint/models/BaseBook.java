package com.ms.printing.bookprint.models;

import com.ms.printing.bookprint.enums.BookType;
import com.ms.printing.bookprint.enums.PaperType;
import com.ms.printing.bookprint.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseBook {
    private BookType bookType;
    private Binding binding;
    private PaperType paperType;
    private Size size;
    private int pages;
}

package com.ms.printing.bookprint.models;


import com.ms.printing.bookprint.enums.CoverType;
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
public class Book {
    private CoverType coverType;
}

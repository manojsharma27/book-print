package com.ms.printing.bookprint.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String address;
    private int pincode;
    private String phoneNumber;
}

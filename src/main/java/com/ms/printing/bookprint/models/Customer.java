package com.ms.printing.bookprint.models;


import com.ms.printing.bookprint.constants.ServiceConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
    private UUID id;

    @NotNull(message = "INVALID_FIRST_NAME")
    private String firstname;

    @NotNull(message = "INVALID_LAST_NAME")
    private String lastname;

    @NotNull(message = "INVALID_EMAIL")
    @Pattern(regexp = ServiceConstants.EMAIL,
            message = "INVALID_EMAIL")
    private String email;

    @NotNull(message = "INVALID_PASSWORD")
    private String password;

    private String address;
    private int pincode;
    private String phoneNumber;
}

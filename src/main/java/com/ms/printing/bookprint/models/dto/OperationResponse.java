package com.ms.printing.bookprint.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationResponse extends BaseResponse {
    private UUID cartId;
    private UUID customerId;
    private UUID productId;
    private UUID orderId;
    private Integer quantity;
    private Boolean checkedOut;
}

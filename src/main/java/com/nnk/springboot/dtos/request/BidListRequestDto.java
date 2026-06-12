package com.nnk.springboot.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidListRequestDto {

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @Positive(message = "Bid Quantity must be positive")
    @NotNull(message = "Bid Quantity is mandatory")
    private Double bidQuantity;
}

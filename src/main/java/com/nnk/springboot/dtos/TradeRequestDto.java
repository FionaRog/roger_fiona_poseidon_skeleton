package com.nnk.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Form data used to create or update a Trade from MVC forms.
 */
@Getter
@Setter
public class TradeRequestDto {

    @NotBlank(message = "Field must not be blank")
    private String account;
    @NotBlank(message = "Field must not be blank")
    private String type;
    @NotNull(message = "Field must not be blank")
    private Double buyQuantity;
}

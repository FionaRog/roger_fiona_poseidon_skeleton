package com.nnk.springboot.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Form data used to create or update a CurvePoint.
 */
@Getter
@Setter
public class CurveRequestDto {

    @NotNull(message = "Must not be null")
    private Integer curveId;

    @NotNull(message = "Term requested")
    private Double term;

    @NotNull(message = "Term requested")
    private Double value;
}

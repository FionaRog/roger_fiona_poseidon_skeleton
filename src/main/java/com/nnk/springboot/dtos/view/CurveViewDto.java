package com.nnk.springboot.dtos.view;

import lombok.Getter;
import lombok.Setter;

/**
 * Display data for a CurvePoint.
 */
@Getter
@Setter
public class CurveViewDto {

    private Integer id;

    private Integer curveId;

    private Double term;

    private Double value;
}

package com.nnk.springboot.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Display data used to render Trade records in MVC views.
 */
@Getter
@Setter
public class TradeViewDto {

    private Integer tradeId;
    private String account;
    private String type;
    private Double buyQuantity;

}

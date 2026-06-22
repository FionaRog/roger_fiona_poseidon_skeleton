package com.nnk.springboot.dtos.view;

import lombok.Getter;
import lombok.Setter;

/**
 * Display data exposed to BidList Thymeleaf views.
 */
@Getter
@Setter
public class BidListViewDto {

    private Integer id;

    private String account;

    private String type;

    private Double bidQuantity;
}

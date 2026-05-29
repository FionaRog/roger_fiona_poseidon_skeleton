package com.nnk.springboot.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidListViewDto {

    private Integer id;

    private String account;

    private String type;

    private Double bidQuantity;
}

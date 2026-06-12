package com.nnk.springboot.dtos.view;

import lombok.Getter;
import lombok.Setter;

/**
 * Display data used to render RuleName records in MVC views.
 */
@Getter
@Setter
public class RuleNameViewDto {

    private Integer id;
    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;
}

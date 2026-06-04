package com.nnk.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Form data used to create or update a RuleName from MVC forms.
 */
@Getter
@Setter
public class RuleNameRequestDto {

    @NotBlank(message = "Field must not be blank")
    private String name;
    @NotBlank(message = "Field must not be blank")
    private String description;
    @NotBlank(message = "Field must not be blank")
    private String json;
    @NotBlank(message = "Field must not be blank")
    private String template;
    @NotBlank(message = "Field must not be blank")
    private String sqlStr;
    @NotBlank(message = "Field must not be blank")
    private String sqlPart;
}

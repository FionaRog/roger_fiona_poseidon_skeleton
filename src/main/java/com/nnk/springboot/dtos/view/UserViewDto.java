package com.nnk.springboot.dtos.view;

import lombok.Getter;
import lombok.Setter;

/**
 * Display data used to render User records in MVC views.
 */
@Getter
@Setter
public class UserViewDto {

    private Integer id;
    private String username;
    private String fullname;
    private String role;
}

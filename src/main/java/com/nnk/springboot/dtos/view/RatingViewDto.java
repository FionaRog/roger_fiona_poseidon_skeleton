package com.nnk.springboot.dtos.view;

import lombok.Getter;
import lombok.Setter;

/**
 * Display data exposed to Rating views.
 */
@Getter
@Setter
public class RatingViewDto {

    private Integer id;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private Integer orderNumber;
}

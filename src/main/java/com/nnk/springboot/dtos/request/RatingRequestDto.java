package com.nnk.springboot.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Form data used to create or update a Rating from MVC forms.
 */
@Getter
@Setter
public class RatingRequestDto {

    @NotBlank(message = "Moodys Rating must not be blank")
    private String moodysRating;

    @NotBlank(message = "Sand P Rating must not be blank")
    private String sandPRating;

    @NotBlank(message = "Fitch Rating must not be blank")
    private String fitchRating;

    @NotNull(message = "Order is required")
    private Integer orderNumber;
}

package com.nnk.springboot.services;

import com.nnk.springboot.dtos.RatingRequestDto;
import com.nnk.springboot.dtos.RatingViewDto;

import java.util.List;

/**
 * Defines business operations for managing Rating records.
 */
public interface IRatingService {

    /**
     * Retrieves all Rating records.
     *
     * @return Rating records formatted for display
     */
    List<RatingViewDto> getAllRatings();

    /**
     * Retrieves a Rating by its technical identifier.
     *
     * @param id the technical identifier of the Rating
     * @return the matching Rating formatted for display
     */
    RatingViewDto getRatingById(Integer id);

    /**
     * Creates a Rating from submitted form data.
     *
     * @param ratingRequestDto the form data used to create the Rating
     * @return the created Rating formatted for display
     */
    RatingViewDto addRating(RatingRequestDto ratingRequestDto);

    /**
     * Updates an existing Rating.
     *
     * @param id the technical identifier of the Rating to update
     * @param ratingRequestDto the form data containing updated values
     * @return the updated Rating formatted for display
     */
    RatingViewDto updateRating(Integer id, RatingRequestDto ratingRequestDto);

    /**
     * Deletes an existing Rating.
     *
     * @param id the technical identifier of the Rating to delete
     */
    void deleteRating(Integer id);


}

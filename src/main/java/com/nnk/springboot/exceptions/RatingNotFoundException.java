package com.nnk.springboot.exceptions;

/**
 * Exception thrown when a Rating cannot be found by id.
 */
public class RatingNotFoundException extends RuntimeException {

    /**
     * Creates an exception for a missing Rating.
     *
     * @param id the technical identifier that could not be found
     */
    public RatingNotFoundException(Integer id) {

        super("Rating not found with id " + id);
    }
}

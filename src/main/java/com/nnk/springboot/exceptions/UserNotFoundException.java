package com.nnk.springboot.exceptions;

/**
 * Exception thrown when a User cannot be found from its identifier.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Creates a not-found exception for a User identifier.
     *
     * @param id the technical identifier that was not found
     */
    public UserNotFoundException(Integer id) {

        super("User not found with id " + id);
    }
}

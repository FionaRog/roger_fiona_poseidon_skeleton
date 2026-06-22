package com.nnk.springboot.exceptions;

/**
 * Exception thrown when a BidList entry cannot be found by its identifier.
 */
public class BidListNotFoundException extends RuntimeException {

    /**
     * Creates an exception for a missing BidList entry.
     *
     * @param bidListId the identifier that did not match any BidList entry
     */
    public BidListNotFoundException(Integer bidListId) {

        super("BidList not found with id " + bidListId);
    }
}

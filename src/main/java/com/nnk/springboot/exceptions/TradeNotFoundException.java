package com.nnk.springboot.exceptions;

/**
 * Exception thrown when a Trade cannot be found from its identifier.
 */
public class TradeNotFoundException extends RuntimeException {

    /**
     * Creates a not-found exception for a Trade identifier.
     *
     * @param id the technical identifier that was not found
     */
    public TradeNotFoundException(Integer id) {

        super("Trade not found with id " + id);
    }
}

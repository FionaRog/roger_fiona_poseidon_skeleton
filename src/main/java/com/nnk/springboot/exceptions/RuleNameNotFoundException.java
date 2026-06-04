package com.nnk.springboot.exceptions;

/**
 * Exception thrown when a RuleName cannot be found from its identifier.
 */
public class RuleNameNotFoundException extends RuntimeException {

    /**
     * Creates a not-found exception for a RuleName identifier.
     *
     * @param id the technical identifier that was not found
     */
    public RuleNameNotFoundException(Integer id) {

        super("RuleName not found with id " + id);    }
}

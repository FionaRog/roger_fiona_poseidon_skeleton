package com.nnk.springboot.exceptions;

/**
 * Exception thrown when a CurvePoint cannot be found by id.
 */
public class CurvePointNotFoundException extends RuntimeException {

    /**
     * Creates an exception for a missing CurvePoint.
     *
     * @param id the technical identifier that could not be found
     */
    public CurvePointNotFoundException(Integer id) {

      super("CurvePoint not found with id " + id);
    }
}

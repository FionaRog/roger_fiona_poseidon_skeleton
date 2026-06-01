package com.nnk.springboot.services;

import com.nnk.springboot.dtos.CurveRequestDto;
import com.nnk.springboot.dtos.CurveViewDto;

import java.util.List;

/**
 * Defines business operations for managing CurvePoint records.
 */
public interface ICurveService {

    /**
     * Retrieves all CurvePoint records.
     *
     * @return CurvePoint records formatted for display
     */
    List<CurveViewDto> getAllCurve();

    /**
     * Retrieves a CurvePoint by its identifier.
     *
     * @param id the technical identifier of the CurvePoint
     * @return the matching CurvePoint formatted for display
     */
    CurveViewDto getCurveById(Integer id);

    /**
     * Creates a CurvePoint from submitted form data.
     *
     * @param curveRequestDto the form data used to create the CurvePoint
     * @return the created CurvePoint formatted for display
     */
    CurveViewDto addCurve(CurveRequestDto curveRequestDto);

    /**
     * Updates an existing CurvePoint.
     *
     * @param id the technical identifier of the CurvePoint to update
     * @param curveRequestDto the form data containing updated values
     * @return the updated CurvePoint formatted for display
     */
    CurveViewDto updateCurve(Integer id, CurveRequestDto curveRequestDto);

    /**
     * Deletes an existing CurvePoint.
     *
     * @param id the technical identifier of the CurvePoint to delete
     */
    void deleteCurve(Integer id);
}

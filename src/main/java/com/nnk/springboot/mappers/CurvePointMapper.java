package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dtos.request.CurveRequestDto;
import com.nnk.springboot.dtos.view.CurveViewDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps CurvePoint entities to DTOs and DTOs to entities.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurvePointMapper {

    /**
     * Converts submitted form data to a CurvePoint entity.
     *
     * @param curveRequestDto the form data to convert
     * @return the corresponding CurvePoint entity
     */
    CurvePoint toEntity(CurveRequestDto curveRequestDto);

    /**
     * Converts a CurvePoint entity to display data.
     *
     * @param curvePoint the entity to convert
     * @return the corresponding display DTO
     */
    CurveViewDto toDto(CurvePoint curvePoint);
}

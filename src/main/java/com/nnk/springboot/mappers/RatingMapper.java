package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dtos.RatingRequestDto;
import com.nnk.springboot.dtos.RatingViewDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps Rating entities to DTOs and DTOs to entities.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatingMapper {

    /**
     * Converts submitted form data to a Rating entity.
     *
     * @param ratingRequestDto the form data to convert
     * @return the corresponding Rating entity
     */
    Rating toEntity (RatingRequestDto ratingRequestDto);

    /**
     * Converts a Rating entity to display data.
     *
     * @param rating the entity to convert
     * @return the corresponding display DTO
     */
    RatingViewDto toDto (Rating rating);
}

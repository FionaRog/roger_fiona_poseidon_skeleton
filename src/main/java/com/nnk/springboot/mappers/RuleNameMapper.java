package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dtos.request.RuleNameRequestDto;
import com.nnk.springboot.dtos.view.RuleNameViewDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps RuleName entities to DTOs and DTOs to entities.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RuleNameMapper {

    /**
     * Converts submitted form data to a RuleName entity.
     *
     * @param requestDto the form data to convert
     * @return the corresponding RuleName entity
     */
    RuleName toEntity (RuleNameRequestDto requestDto);

    /**
     * Converts a RuleName entity to display data.
     *
     * @param ruleName the entity to convert
     * @return the corresponding display DTO
     */
    RuleNameViewDto toDto (RuleName ruleName);
}

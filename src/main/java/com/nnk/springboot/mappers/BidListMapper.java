package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.request.BidListRequestDto;
import com.nnk.springboot.dtos.view.BidListViewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Maps BidList entities to DTOs and DTOs to entities.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BidListMapper {

    /**
     * Converts submitted form data to a BidList entity.
     *
     * @param bidListRequestDto the form data to convert
     * @return the corresponding BidList entity
     */
    BidList toEntity(BidListRequestDto bidListRequestDto);

    /**
     * Converts a BidList entity to display data.
     *
     * @param bidList the entity to convert
     * @return the corresponding display DTO
     */
    @Mapping(source = "bidListId", target = "id")
    BidListViewDto toDto(BidList bidList);
}

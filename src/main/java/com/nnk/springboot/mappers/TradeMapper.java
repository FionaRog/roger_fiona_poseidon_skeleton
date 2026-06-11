package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dtos.TradeRequestDto;
import com.nnk.springboot.dtos.TradeViewDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Maps Trade entities to DTOs and DTOs to entities.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TradeMapper {

    /**
     * Converts submitted form data to a Trade entity.
     *
     * @param tradeRequestDto the form data to convert
     * @return the corresponding Trade entity
     */
    Trade toEntity(TradeRequestDto tradeRequestDto);

    /**
     * Converts a Trade entity to display data.
     *
     * @param trade the entity to convert
     * @return the corresponding display DTO
     */
    TradeViewDto toDto(Trade trade);
}

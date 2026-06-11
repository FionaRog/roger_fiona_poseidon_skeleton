package com.nnk.springboot.services;

import com.nnk.springboot.dtos.TradeRequestDto;
import com.nnk.springboot.dtos.TradeViewDto;

import java.util.List;

/**
 * Defines business operations for managing Trade records.
 */
public interface ITradeService {

    /**
     * Retrieves all Trade records.
     *
     * @return Trade records formatted for display
     */
    List<TradeViewDto> getAllTrades();

    /**
     * Retrieves a Trade by its technical identifier.
     *
     * @param id the technical identifier of the Trade
     * @return the matching Trade formatted for display
     */
    TradeViewDto getTradeById(Integer id);

    /**
     * Creates a Trade from submitted form data.
     *
     * @param tradeRequestDto the form data used to create the Trade
     * @return the created Trade formatted for display
     */
    TradeViewDto addTrade(TradeRequestDto tradeRequestDto);

    /**
     * Updates an existing Trade.
     *
     * @param id the technical identifier of the Trade to update
     * @param tradeRequestDto the form data containing updated values
     * @return the updated Trade formatted for display
     */
    TradeViewDto updateTrade(Integer id, TradeRequestDto tradeRequestDto);

    /**
     * Deletes an existing Trade.
     *
     * @param id the technical identifier of the Trade to delete
     */
    void deleteTrade(Integer id);
}

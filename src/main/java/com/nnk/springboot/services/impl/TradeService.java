package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dtos.request.TradeRequestDto;
import com.nnk.springboot.dtos.view.TradeViewDto;
import com.nnk.springboot.exceptions.TradeNotFoundException;
import com.nnk.springboot.mappers.TradeMapper;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.ITradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for Trade business operations.
 */
@Slf4j
@Service
public class TradeService implements ITradeService {

    private final TradeRepository tradeRepository;

    private final TradeMapper tradeMapper;

    public TradeService(TradeRepository tradeRepository, TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
    }

    /**
     * Retrieves all Trade records and maps them to display DTOs.
     *
     * @return all Trade records formatted for display
     */
    public List<TradeViewDto> getAllTrades() {
        log.info("getting all Trades");

        List<Trade> result = tradeRepository.findAll();
        log.info("Found {} Trades", result.size());

        return result.stream()
                .map(tradeMapper::toDto)
                .toList();
    }

    /**
     * Retrieves one Trade by id.
     *
     * @param id the technical identifier of the Trade
     * @return the matching Trade formatted for display
     * @throws TradeNotFoundException when no Trade exists for the given id
     */
    public TradeViewDto getTradeById(Integer id) {
        log.info("getting Trade with ID {}", id);

        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Trade with ID {} not found", id);
                    return new TradeNotFoundException(id);
                });

        return tradeMapper.toDto(trade);
    }

    /**
     * Creates and persists a new Trade.
     *
     * @param tradeRequestDto the submitted form data
     * @return the created Trade formatted for display
     */
    @Transactional
    public TradeViewDto addTrade(TradeRequestDto tradeRequestDto) {
        log.info("Adding Trade");

        Trade trade = tradeMapper.toEntity(tradeRequestDto);

        Trade savedTrade = tradeRepository.save(trade);
        log.info("Trade added with id{}", savedTrade.getTradeId());

        return tradeMapper.toDto(savedTrade);
    }

    /**
     * Updates an existing Trade.
     *
     * @param id              the technical identifier of the Trade to update
     * @param tradeRequestDto the submitted form data containing updated values
     * @return the updated Trade formatted for display
     * @throws TradeNotFoundException when no Trade exists for the given id
     */
    @Transactional
    public TradeViewDto updateTrade(Integer id, TradeRequestDto tradeRequestDto) {
        log.info("updating Trade with ID {}", id);

        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Trade with ID {} not found", id);
                    return new TradeNotFoundException(id);
                });

        trade.setAccount(tradeRequestDto.getAccount());
        trade.setType(tradeRequestDto.getType());
        trade.setBuyQuantity(tradeRequestDto.getBuyQuantity());

        Trade savedTrade = tradeRepository.save(trade);
        log.info("Trade updated with id{}", savedTrade.getTradeId());

        return tradeMapper.toDto(savedTrade);
    }

    /**
     * Deletes an existing Trade.
     *
     * @param id the technical identifier of the Trade to delete
     * @throws TradeNotFoundException when no Trade exists for the given id
     */
    @Transactional
    public void deleteTrade(Integer id) {
        log.info("deleting Trade with ID {}", id);

        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Trade with ID {} not found", id);
                    return new TradeNotFoundException(id);
                });

        tradeRepository.delete(trade);
        log.info("deleted Trade with ID {}", id);
    }
}

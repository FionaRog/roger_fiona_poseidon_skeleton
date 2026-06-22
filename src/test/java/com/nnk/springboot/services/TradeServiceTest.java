package com.nnk.springboot.services;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dtos.request.TradeRequestDto;
import com.nnk.springboot.dtos.view.TradeViewDto;
import com.nnk.springboot.exceptions.TradeNotFoundException;
import com.nnk.springboot.mappers.TradeMapper;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.impl.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private TradeMapper tradeMapper;

    private ITradeService tradeService;

    @BeforeEach
    void setUp() {
        tradeService = new TradeService(tradeRepository, tradeMapper);
    }

    @Test
    @DisplayName("Should return all Trades")
    public void getAllTrades() {

        Trade trade = new Trade();
        trade.setBuyQuantity(10.0);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setTradeId(1);

        TradeViewDto viewDto = new TradeViewDto();
        viewDto.setBuyQuantity(10.0);
        viewDto.setAccount("Account");
        viewDto.setType("Type");
        viewDto.setTradeId(1);

        when(tradeRepository.findAll()).thenReturn(List.of(trade));
        when(tradeMapper.toDto(trade)).thenReturn(viewDto);

        List<TradeViewDto> result = tradeService.getAllTrades();
        assertEquals(1, result.size());
        assertEquals("Account", result.get(0).getAccount());
        verify(tradeRepository).findAll();
        verify(tradeMapper).toDto(trade);
    }

    @Test
    @DisplayName("Should return Trade by ID")
    public void getTradeById() {
        Trade trade = new Trade();
        trade.setBuyQuantity(10.0);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setTradeId(1);

        TradeViewDto viewDto = new TradeViewDto();
        viewDto.setBuyQuantity(10.0);
        viewDto.setAccount("Account");
        viewDto.setType("Type");
        viewDto.setTradeId(1);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeMapper.toDto(trade)).thenReturn(viewDto);

        TradeViewDto result = tradeService.getTradeById(1);

        assertEquals(1, result.getTradeId());
        verify(tradeRepository).findById(1);
        verify(tradeMapper).toDto(trade);
    }

    @Test
    @DisplayName("Should throw exception when Trade not found")
    public void getTradeNotFound() {

        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TradeNotFoundException.class, () -> tradeService.getTradeById(1));

        verify(tradeRepository).findById(1);
        verifyNoInteractions(tradeMapper);
    }

    @Test
    @DisplayName("Should add Trade")
    public void addTrade() {
        TradeRequestDto requestDto = new TradeRequestDto();
        requestDto.setBuyQuantity(10.0);
        requestDto.setAccount("Account");
        requestDto.setType("Type");

        Trade trade = new Trade();
        trade.setBuyQuantity(10.0);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setTradeId(1);

        Trade savedTrade = new Trade();
        savedTrade.setBuyQuantity(10.0);
        savedTrade.setAccount("Account");
        savedTrade.setType("Type");
        savedTrade.setTradeId(1);

        TradeViewDto viewDto = new TradeViewDto();
        viewDto.setBuyQuantity(10.0);
        viewDto.setAccount("Account");
        viewDto.setType("Type");
        viewDto.setTradeId(1);

        when(tradeMapper.toEntity(requestDto)).thenReturn(trade);
        when(tradeRepository.save(trade)).thenReturn(savedTrade);
        when(tradeMapper.toDto(savedTrade)).thenReturn(viewDto);

        TradeViewDto result = tradeService.addTrade(requestDto);

        assertEquals(1, result.getTradeId());
        assertEquals("Account", result.getAccount());
        verify(tradeMapper).toEntity(requestDto);
        verify(tradeRepository).save(trade);
        verify(tradeMapper).toDto(savedTrade);
    }

    @Test
    @DisplayName("Should update Trade")
    public void updateTrade() {
        TradeRequestDto requestDto = new TradeRequestDto();
        requestDto.setBuyQuantity(20.0);
        requestDto.setAccount("New Account");
        requestDto.setType("New Type");

        Trade oldTrade = new Trade();
        oldTrade.setBuyQuantity(10.0);
        oldTrade.setAccount("Old Account");
        oldTrade.setType("Old Type");
        oldTrade.setTradeId(1);

        Trade updatedTrade = new Trade();
        updatedTrade.setBuyQuantity(20.0);
        updatedTrade.setAccount("New Account");
        updatedTrade.setType("New Type");
        updatedTrade.setTradeId(1);

        TradeViewDto viewDto = new TradeViewDto();
        viewDto.setBuyQuantity(20.0);
        viewDto.setAccount("New Account");
        viewDto.setType("New Type");
        viewDto.setTradeId(1);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(oldTrade));
        when(tradeMapper.toDto(updatedTrade)).thenReturn(viewDto);
        when(tradeRepository.save(oldTrade)).thenReturn(updatedTrade);

        TradeViewDto result = tradeService.updateTrade(1, requestDto);

        assertEquals(1, result.getTradeId());
        assertEquals("New Type", result.getType());

        verify(tradeRepository).findById(1);
        verify(tradeMapper).toDto(updatedTrade);
        verify(tradeRepository).save(oldTrade);
    }

    @Test
    @DisplayName("Should throw exception when updating an unknown Trade")
    public void updateTradeNotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TradeNotFoundException.class, () -> tradeService.updateTrade(1, new TradeRequestDto()));

        verify(tradeRepository).findById(1);
        verify(tradeRepository, never()).save(any(Trade.class));
    }

    @Test
    @DisplayName("Should delete Trade")
    public void deleteTrade() {
        Trade trade = new Trade();
        trade.setTradeId(1);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        tradeService.deleteTrade(1);

        verify(tradeRepository).findById(1);
        verify(tradeRepository).delete(trade);
    }


}

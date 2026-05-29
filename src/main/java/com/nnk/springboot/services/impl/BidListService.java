package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListRequestDto;
import com.nnk.springboot.dtos.BidListViewDto;
import com.nnk.springboot.exceptions.BidListNotFoundException;
import com.nnk.springboot.mappers.BidListMapper;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.IBidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BidListService implements IBidListService {

    private final BidListRepository bidListRepository;

    private final BidListMapper bidListMapper;

    public BidListService(BidListRepository bidListRepository, BidListMapper bidListMapper) {
        this.bidListRepository = bidListRepository;
        this.bidListMapper = bidListMapper;
    }

    public List<BidListViewDto> getBidList(){
        log.info("Fetching all BidLists");

        List<BidList> result = bidListRepository.findAll();
        log.info("Found {} BidLists",result.size());

        return result.stream().map(bidListMapper::toDto).toList();
    }

    public BidListViewDto getBidListById(Integer id){
        log.info("Fetching bidList by id {}",id);

        BidList bidList = bidListRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("BidList not found with id {}",id);
                    return new BidListNotFoundException(id);
                });

        return bidListMapper.toDto(bidList);
    }

    // utile de sécurisé en récupérant l'user (oui pour les logs)?
    @Transactional
    public BidListViewDto addBidList(BidListRequestDto bidListRequestDto){
        log.info("Creating BidList for account={}, type={}",
                bidListRequestDto.getAccount(),
                bidListRequestDto.getType());

        BidList bidList = bidListMapper.toEntity(bidListRequestDto);

        BidList savedBidList = bidListRepository.save(bidList);
        log.info("Created BidList with id={}", savedBidList.getBidListId());

        return bidListMapper.toDto(savedBidList);
    }

    @Transactional
    public BidListViewDto updateBidList(Integer id, BidListRequestDto bidListRequestDto){
        log.info("Updating BidList with id={}", id);

        BidList bidList = bidListRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot update BidList: id={} not found", id);
                    return new BidListNotFoundException(id);
                });

        bidList.setAccount(bidListRequestDto.getAccount());
        bidList.setType(bidListRequestDto.getType());
        bidList.setBidQuantity(bidListRequestDto.getBidQuantity());

        BidList savedBidList = bidListRepository.save(bidList);
        log.info("Updated BidList with id={}", savedBidList.getBidListId());

        return bidListMapper.toDto(savedBidList);
    }

    @Transactional
    public void deleteBidList(Integer id){
        log.info("Deleting BidList with id={}", id);

        BidList deleteBidList = bidListRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot delete BidList: id={} not found", id);
                    return new BidListNotFoundException(id);
                });

        bidListRepository.delete(deleteBidList);
        log.info("Deleted BidList with id={}", id);
    }


}

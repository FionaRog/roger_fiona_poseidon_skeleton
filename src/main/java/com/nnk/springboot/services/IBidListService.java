package com.nnk.springboot.services;

import com.nnk.springboot.dtos.request.BidListRequestDto;
import com.nnk.springboot.dtos.view.BidListViewDto;

import java.util.List;

public interface IBidListService {

    List<BidListViewDto> getBidList();

    BidListViewDto getBidListById(Integer id);

    BidListViewDto addBidList(BidListRequestDto bidListRequestDto);

    BidListViewDto updateBidList(Integer id, BidListRequestDto bidListRequestDto);

    void deleteBidList(Integer id);
}

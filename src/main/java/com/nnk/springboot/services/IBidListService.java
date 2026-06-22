package com.nnk.springboot.services;

import com.nnk.springboot.dtos.request.BidListRequestDto;
import com.nnk.springboot.dtos.view.BidListViewDto;

import java.util.List;

/**
 * Service contract for BidList CRUD operations.
 */
public interface IBidListService {

    /**
     * Retrieves all BidList entries.
     *
     * @return BidList entries formatted for display
     */
    List<BidListViewDto> getBidList();

    /**
     * Retrieves a BidList entry by id.
     *
     * @param id the technical identifier of the BidList entry
     * @return the matching BidList entry formatted for display
     */
    BidListViewDto getBidListById(Integer id);

    /**
     * Creates a new BidList entry from form data.
     *
     * @param bidListRequestDto the form data used to create the BidList entry
     * @return the created BidList entry formatted for display
     */
    BidListViewDto addBidList(BidListRequestDto bidListRequestDto);

    /**
     * Updates an existing BidList entry with submitted form data.
     *
     * @param id the technical identifier of the BidList entry to update
     * @param bidListRequestDto the form data containing updated values
     * @return the updated BidList entry formatted for display
     */
    BidListViewDto updateBidList(Integer id, BidListRequestDto bidListRequestDto);

    /**
     * Deletes an existing BidList entry.
     *
     * @param id the technical identifier of the BidList entry to delete
     */
    void deleteBidList(Integer id);
}

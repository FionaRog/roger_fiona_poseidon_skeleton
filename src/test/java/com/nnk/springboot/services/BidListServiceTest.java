package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.request.BidListRequestDto;
import com.nnk.springboot.dtos.view.BidListViewDto;
import com.nnk.springboot.exceptions.BidListNotFoundException;
import com.nnk.springboot.mappers.BidListMapper;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.impl.BidListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

	@Mock
	private BidListRepository bidListRepository;

	@Mock
	private BidListMapper bidListMapper;

	private IBidListService bidListService;

	@BeforeEach
	void setUp() {
		bidListService = new BidListService(bidListRepository, bidListMapper);
	}

	@Test
	@DisplayName("Should return all BidLists")
	public void getBidListTest() {
		BidList bid = new BidList();
		bid.setBidQuantity(1.0);
		bid.setAccount("account");
		bid.setType("type");

		BidListViewDto viewDto = new BidListViewDto();
		viewDto.setAccount("account");
		viewDto.setType("type");
		viewDto.setBidQuantity(1.0);
		viewDto.setId(1);

		when(bidListRepository.findAll()).thenReturn(List.of(bid));
		when(bidListMapper.toDto(bid)).thenReturn(viewDto);

		List<BidListViewDto> result = bidListService.getBidList();

		assertEquals(1, result.size());
		assertEquals("account", result.get(0).getAccount());
		verify(bidListRepository).findAll();
		verify(bidListMapper).toDto(bid);
	}

	@Test
	@DisplayName("Should return BidList by id")
	public void getBidListByIdTest() {
		BidList bid = new BidList();
		bid.setBidListId(1);

		BidListViewDto viewDto = new BidListViewDto();
		viewDto.setId(1);

		when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));
		when(bidListMapper.toDto(bid)).thenReturn(viewDto);

		BidListViewDto result = bidListService.getBidListById(1);

		assertEquals(1, result.getId());
		verify(bidListRepository).findById(1);
		verify(bidListMapper).toDto(bid);
	}

	@Test
	@DisplayName("Should throw exception when BidList is not found")
	public void getBidListNotFoundTest() {
		when(bidListRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(BidListNotFoundException.class, () -> bidListService.getBidListById(1));

		verify(bidListRepository).findById(1);
		verifyNoInteractions(bidListMapper);
	}

	@Test
	@DisplayName("Should add BidList")
	public void addBidListTest() {
		BidListRequestDto requestDto = new BidListRequestDto();
		requestDto.setAccount("account");
		requestDto.setType("type");
		requestDto.setBidQuantity(1.0);

		BidList bidList = new BidList();
		bidList.setBidQuantity(1.0);
		bidList.setAccount("account");
		bidList.setType("type");

		BidList savedBidList = new BidList();
		savedBidList.setBidListId(1);
		savedBidList.setBidQuantity(1.0);
		savedBidList.setAccount("account");
		savedBidList.setType("type");

		BidListViewDto viewDto = new BidListViewDto();
		viewDto.setId(1);
		viewDto.setAccount("account");
		viewDto.setType("type");
		viewDto.setBidQuantity(1.0);

		when(bidListMapper.toEntity(requestDto)).thenReturn(bidList);
		when(bidListRepository.save(bidList)).thenReturn(savedBidList);
		when(bidListMapper.toDto(savedBidList)).thenReturn(viewDto);

		BidListViewDto result = bidListService.addBidList(requestDto);

		assertEquals(1, result.getId());
		assertEquals("type", result.getType());
		verify(bidListMapper).toEntity(requestDto);
		verify(bidListRepository).save(bidList);
		verify(bidListMapper).toDto(savedBidList);
	}

	@Test
	@DisplayName("Should update BidList")
	public void updateBidListTest() {
		BidListRequestDto requestDto = new BidListRequestDto();
		requestDto.setAccount("Updated Account");
		requestDto.setType("Updated Type");
		requestDto.setBidQuantity(2.0);

		BidList existingBid = new BidList();
		existingBid.setBidListId(1);
		existingBid.setAccount("Old Account");
		existingBid.setType("Old Type");
		existingBid.setBidQuantity(1.0);

		BidList savedBid = new BidList();
		savedBid.setBidListId(1);
		savedBid.setAccount("Updated Account");
		savedBid.setType("Updated Type");
		savedBid.setBidQuantity(2.0);

		BidListViewDto viewDto = new BidListViewDto();
		viewDto.setId(1);
		viewDto.setAccount("Updated Account");
		viewDto.setType("Updated Type");
		viewDto.setBidQuantity(2.0);

		when(bidListRepository.findById(1)).thenReturn(Optional.of(existingBid));
		when(bidListRepository.save(existingBid)).thenReturn(savedBid);
		when(bidListMapper.toDto(savedBid)).thenReturn(viewDto);

		BidListViewDto result = bidListService.updateBidList(1, requestDto);

		assertEquals("Updated Account", result.getAccount());
		assertEquals(2.0, result.getBidQuantity());

		verify(bidListRepository).findById(1);
		verify(bidListRepository).save(existingBid);
		verify(bidListMapper).toDto(savedBid);
	}

	@Test
	@DisplayName("Should throw exception when updating an unknown BidList")
	public void updateBidListNotFoundTest() {
		when(bidListRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(BidListNotFoundException.class, () -> bidListService.updateBidList(1, new BidListRequestDto()));

		verify(bidListRepository).findById(1);
		verify(bidListRepository, never()).save(any(BidList.class));
	}

	@Test
	@DisplayName("Should delete BidList")
	public void deleteBidListTest() {
		BidList  bidList = new BidList();
		bidList.setBidListId(1);

		when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

		bidListService.deleteBidList(1);

		verify(bidListRepository).findById(1);
		verify(bidListRepository).delete(bidList);
	}
}

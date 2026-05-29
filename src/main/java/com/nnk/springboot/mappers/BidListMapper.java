package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListRequestDto;
import com.nnk.springboot.dtos.BidListViewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BidListMapper {

    BidList toEntity (BidListRequestDto bidListRequestDto);

    @Mapping(source= "bidListId", target= "id" )
    BidListViewDto toDto (BidList bidList);
}

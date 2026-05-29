package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dtos.BidListRequestDto;
import com.nnk.springboot.dtos.BidListViewDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-29T10:46:19+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.17 (Eclipse Adoptium)"
)
@Component
public class BidListMapperImpl implements BidListMapper {

    @Override
    public BidList toEntity(BidListRequestDto bidListRequestDto) {
        if ( bidListRequestDto == null ) {
            return null;
        }

        BidList bidList = new BidList();

        bidList.setAccount( bidListRequestDto.getAccount() );
        bidList.setType( bidListRequestDto.getType() );
        bidList.setBidQuantity( bidListRequestDto.getBidQuantity() );

        return bidList;
    }

    @Override
    public BidListViewDto toDto(BidList bidList) {
        if ( bidList == null ) {
            return null;
        }

        BidListViewDto bidListViewDto = new BidListViewDto();

        bidListViewDto.setId( bidList.getBidListId() );
        bidListViewDto.setAccount( bidList.getAccount() );
        bidListViewDto.setType( bidList.getType() );
        bidListViewDto.setBidQuantity( bidList.getBidQuantity() );

        return bidListViewDto;
    }
}

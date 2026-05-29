package com.nnk.springboot.exceptions;


public class BidListNotFoundException extends RuntimeException{

    public BidListNotFoundException(Integer bidListId){

        super("BidList not found with id "+bidListId);
    }
}

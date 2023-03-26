package com.nnk.springboot.exception;

public class BidListNotFoundException extends RuntimeException{

    public BidListNotFoundException(String msg){
        super(msg);
    }
}

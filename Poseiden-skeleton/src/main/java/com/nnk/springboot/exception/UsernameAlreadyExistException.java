package com.nnk.springboot.exception;

public class UsernameAlreadyExistException  extends RuntimeException{

    public UsernameAlreadyExistException(String msg){
        super(msg);
    }
}

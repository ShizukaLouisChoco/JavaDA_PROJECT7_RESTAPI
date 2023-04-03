package com.nnk.springboot.exception;

public class CurvePointAlreadyExistException extends RuntimeException {
    public CurvePointAlreadyExistException(String msg) {
        super(msg);
    }
}

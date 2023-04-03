package com.nnk.springboot.exception;

public class CurvePointNotFoundException extends RuntimeException {
    public CurvePointNotFoundException(String msg) {
        super(msg);
    }
}

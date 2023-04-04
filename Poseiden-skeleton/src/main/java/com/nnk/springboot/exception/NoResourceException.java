package com.nnk.springboot.exception;

public class NoResourceException extends RuntimeException {
    public NoResourceException(Integer id) {
        super("Resource not found with id :" + id);
    }
}

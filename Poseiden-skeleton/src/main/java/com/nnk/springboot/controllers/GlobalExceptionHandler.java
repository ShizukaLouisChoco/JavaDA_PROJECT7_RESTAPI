package com.nnk.springboot.controllers;

import com.nnk.springboot.exception.NoResourceException;
import com.nnk.springboot.exception.ResourceAlreadyExistException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoResourceException.class)
    public String handleNoResourceException(NoResourceException ex, Model model) {
        model.addAttribute("errorMsg", ex.getMessage());

        return "error";
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public String handleResourceAlreadyExistException(ResourceAlreadyExistException ex, Model model) {
        model.addAttribute("errorMsg", ex.getMessage());
        return "error";
    }


}

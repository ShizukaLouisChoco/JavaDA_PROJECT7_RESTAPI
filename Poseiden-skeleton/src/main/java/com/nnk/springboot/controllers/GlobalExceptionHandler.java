package com.nnk.springboot.controllers;

import com.nnk.springboot.exception.NoResourceException;
import com.nnk.springboot.exception.ResourceAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoResourceException.class)
    public String handleNoResourceException(NoResourceException ex, Model model) {
        log.info("exception handling because of no resource exception");
        model.addAttribute("errorMsg", ex.getMessage());

        return "error";
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public String handleResourceAlreadyExistException(ResourceAlreadyExistException ex, Model model) {
        log.info("exception handling because of resource already exists exception");
        model.addAttribute("errorMsg", ex.getMessage());
        return "error";
    }


}

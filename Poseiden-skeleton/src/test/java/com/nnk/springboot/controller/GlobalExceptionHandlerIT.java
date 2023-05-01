package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.GlobalExceptionHandler;
import com.nnk.springboot.exception.NoResourceException;
import com.nnk.springboot.exception.ResourceAlreadyExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class GlobalExceptionHandlerIT {

    @Mock
    private HttpServletResponse response;

    @Mock
    private Model model;

    @Test
    void handleNoResourceException() {
        // GIVEN
        NoResourceException ex = new NoResourceException(1);
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // WHEN
        String result = handler.handleNoResourceException(ex, model);

        // THEN
        verify(model).addAttribute("errorMsg", ex.getMessage());

        assert(result.equals("error"));
    }

    @Test
    void handleResourceAlreadyExistException() {
        // GIVEN
        ResourceAlreadyExistException ex = new ResourceAlreadyExistException("Resource already exists");
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // WHEN
        String result = handler.handleResourceAlreadyExistException(ex, model);

        // THEN
        verify(model).addAttribute("errorMsg", ex.getMessage());

        assert(result.equals("error"));
    }
}

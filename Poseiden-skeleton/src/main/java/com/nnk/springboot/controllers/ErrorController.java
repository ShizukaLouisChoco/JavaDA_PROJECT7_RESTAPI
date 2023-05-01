package com.nnk.springboot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {


    @GetMapping("/error")
    public String error(Model model, HttpServletResponse response) {
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        //if(httpStatus.is5xxServerError()| httpStatus.is4xxClientError()){
        model.addAttribute("errorStatus",httpStatus);
        return "error";
    }


}

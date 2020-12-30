package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.exceptions.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleInalidInputException(Exception exception, Model model) {
        model.addAttribute("exception", exception);
        return "404";
    }
}

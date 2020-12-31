package com.training.trainingblogapp.controllers;

import com.training.trainingblogapp.exceptions.InvalidInputException;
import com.training.trainingblogapp.exceptions.UserNotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateInputException;

@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleInalidInputException(Exception exception, Model model) {
        String exceptionMessage = exception.getMessage();
        model.addAttribute("exception", exceptionMessage);
        model.addAttribute("request", "404 Page Not Found");
        return "error";
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotAuthorizedException(Exception exception, Model model) {
        String exceptionMessage = exception.getMessage();
        model.addAttribute("exception", exceptionMessage);
        model.addAttribute("request", "401 Not Authorized");
        return "error";
    }


    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleWrongInputException(Model model) {
        model.addAttribute("exception", "Oops...wrong input");
        model.addAttribute("request", "404 Page Not Found");
        return "error";
    }

    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class, MissingServletRequestParameterException.class,
            ServletRequestBindingException.class, MethodArgumentNotValidException.class, MissingServletRequestPartException.class,
            NoHandlerFoundException.class})
    public String handleGlobalException(Model model){
        model.addAttribute("exception", "...I am lost dude");
        model.addAttribute("request", "404 Page Not Found");
        return "error";
    }
}

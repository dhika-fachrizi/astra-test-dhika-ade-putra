package com.dhika.astralife.controller;

import com.dhika.astralife.exception.InvalidDataException;
import com.dhika.astralife.exception.InvalidDateException;
import com.dhika.astralife.exception.NotFoundException;
import com.dhika.astralife.model.EmployeeModel;
import com.dhika.astralife.model.ResponseModel;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ErrorController {



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {InvalidDateException.class})
    public ResponseModel<List<String>> dateHandler(InvalidDateException e){

        log.error(e.getStackTrace().toString());

        List<String> strings = new ArrayList<>();
        strings.add(e.getMessage());

        return ResponseModel.<List<String>>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(strings)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {InvalidDataException.class})
    public ResponseModel<List<String>> createDataHandler(InvalidDataException e){

        log.error(e.getStackTrace().toString());

        List<String> strings = new ArrayList<>();
        strings.add(e.getMessage());

        return ResponseModel.<List<String>>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(strings)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseModel<List<String>> dataHandler(NotFoundException e){

        log.error(e.getStackTrace().toString());

        List<String> strings = new ArrayList<>();
        strings.add(e.getMessage());

        return ResponseModel.<List<String>>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(strings)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseModel<List<String>> dataNotFound(MethodArgumentNotValidException e){

        log.error(e.getMessage() + "||" + e.getCause());

        List<String> errors = e.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());

        return ResponseModel.<List<String>>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(errors)
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public ResponseModel<String> internalServerError(Exception e){

        log.error(e.getMessage() + "||" + e.getCause());

        return ResponseModel.<String>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
    }

}

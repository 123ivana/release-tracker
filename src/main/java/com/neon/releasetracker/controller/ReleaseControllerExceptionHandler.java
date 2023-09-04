package com.neon.releasetracker.controller;

import com.neon.releasetracker.dto.RestApiErrorResponseDto;
import com.neon.releasetracker.exception.ReleaseNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ReleaseControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseControllerExceptionHandler.class);


    @ExceptionHandler(ReleaseNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    RestApiErrorResponseDto handleEntityNotFoundException(ReleaseNotFoundException exception) {
        LOGGER.error("Release not found exception occurred", exception);
        return new RestApiErrorResponseDto(HttpStatus.NOT_FOUND.name(), exception.getMessage());
    }

}

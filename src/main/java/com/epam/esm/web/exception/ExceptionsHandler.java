package com.epam.esm.web.exception;

import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.WrongDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.epam.esm.web.exception.ErrorsCode.*;
import static org.springframework.http.HttpStatus.*;

/**
 * Class {@code ExceptionsHandler} presents errors which will be returned from controller in case throw exception.
 * @author Oleksandr Myronenko
 */
@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(NoSuchEntityException.class)
    public final ResponseEntity<Object> handleNoSuchEntityException(NoSuchEntityException exception){
        String details = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse,INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(WrongDataException.class)
    public final ResponseEntity<Object> handleWrongDataException(WrongDataException exception){
        String details = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(WRONG_DATA_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse,BAD_REQUEST);
    }
}

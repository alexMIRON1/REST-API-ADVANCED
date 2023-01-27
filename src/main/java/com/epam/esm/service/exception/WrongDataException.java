package com.epam.esm.service.exception;


/**
 * {@code WrongDataException} is generated in case when some data are wrong
 * @author Oleksandr Myronenko
 * @see java.lang.RuntimeException
 */
public class WrongDataException extends RuntimeException{
    public WrongDataException(String message) {
        super(message);
    }
}

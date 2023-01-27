package com.epam.esm.service.exception;
/**
 * {@code UnsupportedOperationException} is generated in case when operation does not exist
 * @author Oleksandr Myronenko
 * @see java.lang.RuntimeException
 */
public class UnsupportedOperationException extends RuntimeException{
    public UnsupportedOperationException(String message) {
        super(message);
    }
}

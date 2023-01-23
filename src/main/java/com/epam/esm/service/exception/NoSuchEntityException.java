package com.epam.esm.service.exception;

/**
 * {@code NoSuchEntityException} is generated in case when entity does not exist
 * @author Oleksandr Myronenko
 * @see java.lang.RuntimeException
 */
public class NoSuchEntityException extends RuntimeException{


    public NoSuchEntityException(String message) {
        super(message);
    }
}

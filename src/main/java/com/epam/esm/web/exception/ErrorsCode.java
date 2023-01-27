package com.epam.esm.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum {@code ErrorsCode} presents values which will be set.
 * @author Oleksandr Myronenko
 */
@RequiredArgsConstructor
@Getter
public enum ErrorsCode {
    NOT_FOUND_EXCEPTION(40401,"NOT_FOUND"),
    WRONG_DATA_EXCEPTION(40001,"WRONG_DATA");
    private final int code;
    private final String message;

    @Override
    public String toString() {
        return code + " " +
                message;
    }
}

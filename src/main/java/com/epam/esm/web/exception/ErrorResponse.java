package com.epam.esm.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class {@code ErrorResponse } represents objects that will be returned as error.
 * @author Oleksandr Myronenko
 */

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;

}

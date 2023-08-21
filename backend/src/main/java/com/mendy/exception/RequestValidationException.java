package com.mendy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mendy
 * @create 2023-08-02 6:18
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RequestValidationException extends RuntimeException {

    public RequestValidationException(String message) {
        super(message);
    }

}

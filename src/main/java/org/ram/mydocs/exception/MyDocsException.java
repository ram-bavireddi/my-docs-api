package org.ram.mydocs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MyDocsException extends RuntimeException {

    private HttpStatus status;

    public MyDocsException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public MyDocsException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}

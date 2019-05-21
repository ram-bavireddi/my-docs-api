package org.ram.mydocs.exception;

import org.ram.mydocs.resource.BaseResource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyDocsExceptionHandler {

    @ExceptionHandler(MyDocsException.class)
    public BaseResource handle(MyDocsException ex) {
        return BaseResource.from(ex.getStatus().value(), ex.getMessage());
    }
}

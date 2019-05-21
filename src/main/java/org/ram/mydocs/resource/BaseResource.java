package org.ram.mydocs.resource;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BaseResource {
    private int status;
    private String message;

    public static BaseResource from(int status, String message) {
        return BaseResource.builder().status(status).message(message).build();
    }
}

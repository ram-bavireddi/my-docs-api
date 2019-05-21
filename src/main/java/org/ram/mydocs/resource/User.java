package org.ram.mydocs.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String email;
    private String authorizationUrl;
}

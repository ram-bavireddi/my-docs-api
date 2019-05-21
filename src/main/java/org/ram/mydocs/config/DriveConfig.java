package org.ram.mydocs.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Getter
@Configuration
public class DriveConfig {

    @Value("${google.oauth.callback.uri}")
    private String callback;

    @Value("${google.secret.key.path}")
    private Resource secretKeys;
}

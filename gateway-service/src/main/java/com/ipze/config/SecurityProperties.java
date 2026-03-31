package com.ipze.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private List<String> excludedUrls;

    private String secretKey;
}
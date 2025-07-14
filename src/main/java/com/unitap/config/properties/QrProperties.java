package com.unitap.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "qr")
public class QrProperties {
    private Integer width = 300;
    private Integer height = 300;
    private String imageFormat = "PNG";
    private String linkPattern = "https://unitap.ru/card/%s";
}

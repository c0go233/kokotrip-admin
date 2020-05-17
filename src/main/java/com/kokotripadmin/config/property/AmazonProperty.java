package com.kokotripadmin.config.property;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class AmazonProperty {
    private String accessKey;
    private String secretKey;
}

package com.kokotripadmin.config.property;


import org.springframework.stereotype.Component;

@Component
public class WebMvcProperty {

    private String messageSourcePath;
    private String messageSourceDefaultEncoding;

    public String getMessageSourcePath() {
        return messageSourcePath;
    }

    public void setMessageSourcePath(String messageSourcePath) {
        this.messageSourcePath = messageSourcePath;
    }

    public String getMessageSourceDefaultEncoding() {
        return messageSourceDefaultEncoding;
    }

    public void setMessageSourceDefaultEncoding(String messageSourceDefaultEncoding) {
        this.messageSourceDefaultEncoding = messageSourceDefaultEncoding;
    }
}

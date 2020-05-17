package com.kokotripadmin.exception.support_language;

public class SupportLanguageNotFoundException extends Exception {

    public SupportLanguageNotFoundException() {
        super("번역지원언어를 찾을수가 없습니다.");
    }

    public SupportLanguageNotFoundException(Integer supportLanguageId) {
        super("지원언어(id=" + supportLanguageId + ") 가 없는디???????");
    }
}

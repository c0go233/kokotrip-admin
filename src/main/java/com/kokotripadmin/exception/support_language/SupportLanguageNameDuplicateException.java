package com.kokotripadmin.exception.support_language;

public class SupportLanguageNameDuplicateException extends Exception {
    public SupportLanguageNameDuplicateException(String supportLanguageName) {
        super(supportLanguageName + " 은(는) 이미 추가한 번역언어입니다.");
    }
}

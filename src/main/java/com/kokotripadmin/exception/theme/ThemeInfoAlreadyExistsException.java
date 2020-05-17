package com.kokotripadmin.exception.theme;

public class ThemeInfoAlreadyExistsException extends Exception {

    public ThemeInfoAlreadyExistsException(String themeName, String supportLanguageName) {
        super("상위분류: " + themeName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}

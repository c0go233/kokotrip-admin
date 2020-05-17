package com.kokotripadmin.exception.theme;

public class ThemeInfoNotEditableException extends Exception {

    public ThemeInfoNotEditableException() {
        super("한국어 번역정보는 편집할수없습니다. 상위분류를 직접 편집하세요.");
    }
}

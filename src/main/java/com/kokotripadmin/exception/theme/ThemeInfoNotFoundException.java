package com.kokotripadmin.exception.theme;

public class ThemeInfoNotFoundException extends Exception {

    public ThemeInfoNotFoundException() {
        super("상위분류 번역정보를 찾을수 없습니다.");
    }
}

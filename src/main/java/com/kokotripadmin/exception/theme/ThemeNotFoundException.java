package com.kokotripadmin.exception.theme;

public class ThemeNotFoundException extends Exception {

    public ThemeNotFoundException() {
        super("상위분류를 찾지 못했습니다.");
    }

    public ThemeNotFoundException(Integer themeId) {
        super("상위 카테고리(id=" + themeId + ") 존재하지가 않는다우");
    }
}

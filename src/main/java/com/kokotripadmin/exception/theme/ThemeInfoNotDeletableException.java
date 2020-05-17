package com.kokotripadmin.exception.theme;

public class ThemeInfoNotDeletableException extends Exception {
    public ThemeInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 상위분류를 삭제하세요.");
    }
}

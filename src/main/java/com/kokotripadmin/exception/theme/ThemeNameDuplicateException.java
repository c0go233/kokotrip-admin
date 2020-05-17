package com.kokotripadmin.exception.theme;

public class ThemeNameDuplicateException extends Exception {


    public ThemeNameDuplicateException(String themeName) {
        super(themeName + " 는(은) 이미 추가했다 상위 카테고리 리스트에서 찾아봐라~~!!");
    }
}

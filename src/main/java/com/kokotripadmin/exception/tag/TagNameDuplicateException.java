package com.kokotripadmin.exception.tag;

public class TagNameDuplicateException extends Exception {

    public TagNameDuplicateException(String tagName) {
        super("하위분류: " + tagName + " 는(은) 이미 존재합니다");
    }
}
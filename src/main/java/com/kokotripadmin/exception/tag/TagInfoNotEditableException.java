package com.kokotripadmin.exception.tag;

public class TagInfoNotEditableException extends Exception {

    public TagInfoNotEditableException() {
        super("한국어 번역정보는 편집할수없습니다. 하위분류를 직접 편집하세요.");
    }
}

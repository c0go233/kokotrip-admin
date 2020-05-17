package com.kokotripadmin.exception.tag;

public class TagInfoAlreadyExistsException extends Exception {

    public TagInfoAlreadyExistsException(String tagName, String supportLanguageName) {
        super("하위분류: " + tagName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}

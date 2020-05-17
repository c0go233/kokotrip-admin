package com.kokotripadmin.exception.tag;

public class TagInfoNotDeletableException extends Exception {

    public TagInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 하위분류를 삭제하세요.");
    }
}

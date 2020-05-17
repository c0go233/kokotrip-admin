package com.kokotripadmin.exception.tag;

public class TagInfoNotFoundException extends Exception {
    public TagInfoNotFoundException() {
        super("소분류가 없거나, 선택된 지원언어로 작성된 소분류정보가 없습니다");
    }
}

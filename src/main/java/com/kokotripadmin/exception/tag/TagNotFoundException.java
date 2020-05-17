package com.kokotripadmin.exception.tag;

public class TagNotFoundException extends Exception {

    public TagNotFoundException() {
        super("하위 카테고리를 찾을수 없습니다.");
    }

    public TagNotFoundException(int tagId) {
        super("태그 (id=" + tagId + ") 가 없는디???????");
    }
}

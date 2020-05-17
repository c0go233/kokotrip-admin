package com.kokotripadmin.exception.photozone;

public class PhotoZoneInfoNotEditableException extends Exception {

    public PhotoZoneInfoNotEditableException() {
        super("한국어 번역정보는 편집가능하지 않습니다. 포토존정보를 직접편집하세요.");
    }
}

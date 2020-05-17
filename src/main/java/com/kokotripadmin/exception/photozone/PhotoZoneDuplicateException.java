package com.kokotripadmin.exception.photozone;

public class PhotoZoneDuplicateException extends Exception {

    public PhotoZoneDuplicateException() {
        super("이미 추가된 포토존입니다");
    }
}

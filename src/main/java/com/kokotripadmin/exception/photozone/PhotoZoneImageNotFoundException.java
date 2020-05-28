package com.kokotripadmin.exception.photozone;

public class PhotoZoneImageNotFoundException extends Exception {
    public PhotoZoneImageNotFoundException() {
        super("포토존 이미지를 찾기 못했습니다");
    }
}

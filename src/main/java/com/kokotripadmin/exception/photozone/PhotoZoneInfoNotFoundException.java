package com.kokotripadmin.exception.photozone;

public class PhotoZoneInfoNotFoundException extends Exception {

    public PhotoZoneInfoNotFoundException() {
        super("포토존 번역정보를 찾지 못했습니다. 포토존 번역정보를 먼저 작성해주세요.");
    }
}

package com.kokotripadmin.exception.photozone;

public class PhotoZoneNotFoundException extends Exception {

    public PhotoZoneNotFoundException() {
        super("포토존을 찾지 못했습니다. 다시 시도해주세요.");
    }
}

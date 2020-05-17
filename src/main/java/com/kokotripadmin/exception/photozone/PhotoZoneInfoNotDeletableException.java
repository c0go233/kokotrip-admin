package com.kokotripadmin.exception.photozone;

public class PhotoZoneInfoNotDeletableException extends Exception {

    public PhotoZoneInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 포토존을 삭제하세요.");
    }
}

package com.kokotripadmin.exception.photozone;

public class PhotoZoneInfoAlreadyExistsException extends Throwable {
    public PhotoZoneInfoAlreadyExistsException(String photoZoneName, String supportLanguageName) {
        super("포토존: " + photoZoneName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}

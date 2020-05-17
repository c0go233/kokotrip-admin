package com.kokotripadmin.exception.photozone;

public class PhotoZoneTourSpotDuplicateException extends Exception {

    public PhotoZoneTourSpotDuplicateException() {
        super("소속 여행지는 연계 여행지로 선택할수 없습니다.");
    }
}

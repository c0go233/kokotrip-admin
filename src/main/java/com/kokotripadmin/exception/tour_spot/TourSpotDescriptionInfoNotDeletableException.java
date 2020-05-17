package com.kokotripadmin.exception.tour_spot;

public class TourSpotDescriptionInfoNotDeletableException extends Exception {

    public TourSpotDescriptionInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 여행지설명을 삭제하세요.");
    }
}

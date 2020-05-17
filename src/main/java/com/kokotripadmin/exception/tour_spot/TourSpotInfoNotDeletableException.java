package com.kokotripadmin.exception.tour_spot;

public class TourSpotInfoNotDeletableException extends Exception {

    public TourSpotInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 여행지를 삭제하세요.");
    }
}

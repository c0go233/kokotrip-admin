package com.kokotripadmin.exception.tour_spot;

public class TourSpotInfoNotEditableException extends Exception {
    public TourSpotInfoNotEditableException() {
        super("한국어 번역정보는 편집가능하지 않습니다. 여행지를 직접편집하세요.");
    }
}

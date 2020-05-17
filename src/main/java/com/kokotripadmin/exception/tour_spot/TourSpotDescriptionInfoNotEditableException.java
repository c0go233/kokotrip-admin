package com.kokotripadmin.exception.tour_spot;

public class TourSpotDescriptionInfoNotEditableException extends Exception {

    public TourSpotDescriptionInfoNotEditableException() {
        super("한국어 번역정보는 편집가능하지 않습니다. 여행지 설명을 직접편집하세요.");
    }
}

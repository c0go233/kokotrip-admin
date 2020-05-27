package com.kokotripadmin.exception.tour_spot;

public class TourSpotDescriptionImageNotFoundException extends Exception {
    public TourSpotDescriptionImageNotFoundException() {
        super("여행지 설명 이미지를 찾기 못했습니다");
    }
}

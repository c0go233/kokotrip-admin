package com.kokotripadmin.exception.tour_spot;

public class TourSpotInfoNotFoundException extends Exception {


    public TourSpotInfoNotFoundException() {
        super("여행지 번역정보를 찾지 못했습니다. 여행지 번역정보를 먼저 작성해주세요.");
    }


}

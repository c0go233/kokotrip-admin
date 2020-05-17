package com.kokotripadmin.exception.tour_spot;

public class TourSpotDescriptionAlreadyExistsException extends Exception {

    public TourSpotDescriptionAlreadyExistsException(String tourSpotDescriptionName) {
        super(tourSpotDescriptionName + " 는(은) 이미 존재하는 여행지설명 입니다.");
    }
}

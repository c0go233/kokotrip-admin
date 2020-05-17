package com.kokotripadmin.exception.tour_spot;

public class TourSpotInfoAlreadyExistsException extends Exception {

    public TourSpotInfoAlreadyExistsException(String tourSpotName,
                                              String supportLanguageName) {
        super("여행지: " + tourSpotName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}

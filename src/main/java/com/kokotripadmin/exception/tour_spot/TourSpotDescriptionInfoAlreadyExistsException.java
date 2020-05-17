package com.kokotripadmin.exception.tour_spot;

public class TourSpotDescriptionInfoAlreadyExistsException extends Exception {


    public TourSpotDescriptionInfoAlreadyExistsException(String tourSpotDescriptionName,
                                                         String supportLanguageName) {
        super("여행지 설명: " + tourSpotDescriptionName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}

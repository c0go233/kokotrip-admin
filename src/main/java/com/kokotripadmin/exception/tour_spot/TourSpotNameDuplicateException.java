package com.kokotripadmin.exception.tour_spot;

public class TourSpotNameDuplicateException extends Exception {

    public TourSpotNameDuplicateException(String tourSpotName) {
        super(tourSpotName +  " 은(는) 이미 추가했다!!!!! 여행지 리스트에서 찾아봐라~");
    }
}

package com.kokotripadmin.exception.tour_spot;

public class TourSpotNotFoundException extends Exception {

    public TourSpotNotFoundException() {
        super("도시를 찾을수 없습니다.");
    }

    public TourSpotNotFoundException(int tourSpotId) {
        super("도시(id=" + tourSpotId + ") 가 없는디???????");
    }
}

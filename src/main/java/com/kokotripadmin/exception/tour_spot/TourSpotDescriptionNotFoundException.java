package com.kokotripadmin.exception.tour_spot;

public class TourSpotDescriptionNotFoundException extends Exception {

    public TourSpotDescriptionNotFoundException() {
        super("여행지 ");
    }

    public TourSpotDescriptionNotFoundException(int tourSpotDescriptionId) {
        super("여행지 상세설명 (id=" + tourSpotDescriptionId + ") 이 없는디???????");
    }
}

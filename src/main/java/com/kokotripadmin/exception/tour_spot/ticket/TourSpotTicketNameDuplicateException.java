package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketNameDuplicateException extends Exception {

    public TourSpotTicketNameDuplicateException(String tourSpotTicketName) {
        super(tourSpotTicketName + " 은(는) 이미 추가한 여행지티켓입니다.");
    }
}

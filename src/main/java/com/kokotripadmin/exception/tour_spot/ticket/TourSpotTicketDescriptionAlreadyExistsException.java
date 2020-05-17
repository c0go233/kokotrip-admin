package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketDescriptionAlreadyExistsException extends Exception {

    public TourSpotTicketDescriptionAlreadyExistsException(String tourSpotTicketDescriptionName) {
        super(tourSpotTicketDescriptionName + " 는(은) 이미 존재하는 여행지설명 입니다.");
    }
}

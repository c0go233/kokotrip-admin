package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketNotFoundException extends Exception {

    public TourSpotTicketNotFoundException() {
        super("여행티켓이 존재하지않습니다.");
    }

    public TourSpotTicketNotFoundException(Integer tourSpotTicketId) {
        super("여행지 티켓(id=" + tourSpotTicketId + ") 이 없는딩??");
    }
}

package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketPriceNotFoundException extends Exception {

    public TourSpotTicketPriceNotFoundException(Integer tourSpotTicketPriceId) {
        super("여행지 티켓가격(id=" + tourSpotTicketPriceId + ") 이 없는딩??");
    }
}

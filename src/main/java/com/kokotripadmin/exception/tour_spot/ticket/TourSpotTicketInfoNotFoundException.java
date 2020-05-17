package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketInfoNotFoundException extends Exception {

    public TourSpotTicketInfoNotFoundException() {
        super("여행지티켓 번역정보를 찾을수없습니다");
    }
}

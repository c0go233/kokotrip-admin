package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketDescriptionNotFoundException extends Exception {

    public TourSpotTicketDescriptionNotFoundException() {
        super("여행지 티켓 설명 정보를 찾을수 없습니다.");
    }
}

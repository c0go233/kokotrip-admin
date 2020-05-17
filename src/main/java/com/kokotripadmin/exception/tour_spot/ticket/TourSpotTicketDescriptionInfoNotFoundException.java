package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketDescriptionInfoNotFoundException extends Exception {

    public TourSpotTicketDescriptionInfoNotFoundException() {
        super("여행지 티켓 설명 지원언어 정보를 찾을수 없습니다.");
    }
}

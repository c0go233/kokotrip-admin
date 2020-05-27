package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketImageNotFoundException extends Exception {
    public TourSpotTicketImageNotFoundException() {
        super("여행지 티켓 이미지를 찾기 못했습니다");
    }
}

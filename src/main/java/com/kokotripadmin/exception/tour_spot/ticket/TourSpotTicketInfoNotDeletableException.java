package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketInfoNotDeletableException extends Exception {

    public TourSpotTicketInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 여행지티켓을 삭제하세요.");
    }
}

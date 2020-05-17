package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketDescriptionInfoNotDeletableException extends Exception {

    public TourSpotTicketDescriptionInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 여행지티켓설명을 삭제하세요.");
    }
}

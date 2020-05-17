package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketDescriptionInfoNotEditableException extends Exception {

    public TourSpotTicketDescriptionInfoNotEditableException() {
        super("한국어 번역정보는 편집가능하지 않습니다. 여행지타켓설명을 직접편집하세요.");
    }

}

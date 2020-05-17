package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketDescriptionInfoAlreadyExistsException extends Exception {


    public TourSpotTicketDescriptionInfoAlreadyExistsException(String tourSpotTicketDescriptionName, String supportLanguageName) {
        super("여행지티켓설명: " + tourSpotTicketDescriptionName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}

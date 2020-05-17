package com.kokotripadmin.exception.tour_spot.ticket;

public class TourSpotTicketInfoAlreadyExistsException extends Exception {

    public TourSpotTicketInfoAlreadyExistsException(String tourSpotTicketName, String supportLanguageName) {
        super("여행지티켓: " + tourSpotTicketName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}

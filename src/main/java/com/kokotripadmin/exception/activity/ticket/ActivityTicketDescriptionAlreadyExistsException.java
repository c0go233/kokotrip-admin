package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketDescriptionAlreadyExistsException extends Exception {

    public ActivityTicketDescriptionAlreadyExistsException(String tourSpotTicketDescriptionName) {
        super(tourSpotTicketDescriptionName + " 는(은) 이미 존재하는 여행지설명 입니다.");
    }
}

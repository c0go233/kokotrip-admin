package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketNameDuplicateException extends Exception {

    public ActivityTicketNameDuplicateException(String tourSpotTicketName) {
        super(tourSpotTicketName + " 은(는) 이미 추가한 여행지티켓입니다.");
    }
}

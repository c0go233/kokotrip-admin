package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketDescriptionInfoAlreadyExistsException extends Exception {


    public ActivityTicketDescriptionInfoAlreadyExistsException(String tourSpotTicketDescriptionName, String supportLanguageName) {
        super("여행지티켓설명: " + tourSpotTicketDescriptionName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}

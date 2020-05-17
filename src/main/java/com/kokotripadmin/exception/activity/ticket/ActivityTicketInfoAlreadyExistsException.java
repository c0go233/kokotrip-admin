package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketInfoAlreadyExistsException extends Exception {

    public ActivityTicketInfoAlreadyExistsException(String tourSpotTicketName, String supportLanguageName) {
        super("여행지티켓: " + tourSpotTicketName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}

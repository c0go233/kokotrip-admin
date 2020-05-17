package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketInfoNotEditableException extends Exception {

    public ActivityTicketInfoNotEditableException() {
        super("한국어 번역정보는 편집가능하지 않습니다. 여행지타켓을 직접편집하세요.");
    }
}

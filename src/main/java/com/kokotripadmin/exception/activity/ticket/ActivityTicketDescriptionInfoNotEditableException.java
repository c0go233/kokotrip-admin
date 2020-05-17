package com.kokotripadmin.exception.activity.ticket;

public class ActivityTicketDescriptionInfoNotEditableException extends Exception {

    public ActivityTicketDescriptionInfoNotEditableException() {
        super("한국어 번역정보는 편집가능하지 않습니다. 여행지타켓설명을 직접편집하세요.");
    }

}

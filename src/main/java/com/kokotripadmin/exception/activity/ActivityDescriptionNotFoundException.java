package com.kokotripadmin.exception.activity;

public class ActivityDescriptionNotFoundException extends Exception {

    public ActivityDescriptionNotFoundException() {
        super("액티비티 설명을 찾지 못했습니다.");
    }
}

package com.kokotripadmin.exception.activity;

public class ActivityDescriptionInfoNotFoundException extends Exception {
    public ActivityDescriptionInfoNotFoundException() {
        super("액티비티설명 지원언어 정보를 찾지 못했습니다.");
    }
}

package com.kokotripadmin.exception.activity;

public class ActivityInfoNotFoundException extends Exception {

    public ActivityInfoNotFoundException() {
        super("액티비티 지원언어 정보를 찾을수 없습니다.");
    }
}

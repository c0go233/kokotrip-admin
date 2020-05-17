package com.kokotripadmin.exception.activity;

public class ActivityNotFoundException extends Exception {

    public ActivityNotFoundException() {
        super("액티비티를 찾지 못했습니다. 다시 시도해주세요.");
    }
}

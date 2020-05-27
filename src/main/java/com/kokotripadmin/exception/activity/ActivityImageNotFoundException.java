package com.kokotripadmin.exception.activity;

public class ActivityImageNotFoundException extends Exception {
    public ActivityImageNotFoundException() {
        super("액티비티 이미지를 찾기 못했습니다");
    }
}

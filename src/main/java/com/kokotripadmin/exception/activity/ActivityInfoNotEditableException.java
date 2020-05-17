package com.kokotripadmin.exception.activity;

public class ActivityInfoNotEditableException extends Exception {

    public ActivityInfoNotEditableException() {
        super("한국어 번역정보는 편집가능하지 않습니다. 액티비티 번역 정보를 직접편집하세요.");
    }
}

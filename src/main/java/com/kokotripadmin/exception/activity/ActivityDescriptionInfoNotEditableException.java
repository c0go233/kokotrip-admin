package com.kokotripadmin.exception.activity;

public class ActivityDescriptionInfoNotEditableException extends Exception {

    public ActivityDescriptionInfoNotEditableException() {

        super("한국어 번역정보는 편집가능하지 않습니다. 액티비티정보를 직접편집하세요.");
    }
}

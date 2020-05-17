package com.kokotripadmin.exception.activity;

public class ActivityDescriptionInfoNotDeletableException extends Exception {


    public ActivityDescriptionInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 액티비티 설명을 삭제하세요.");
    }
}

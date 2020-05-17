package com.kokotripadmin.exception.activity;

public class ActivityInfoNotDeletableException extends Exception {


    public ActivityInfoNotDeletableException() {
        super("한국어 번역정보는 삭제할수없습니다. 액티비티를 삭제하세요.");

    }
}

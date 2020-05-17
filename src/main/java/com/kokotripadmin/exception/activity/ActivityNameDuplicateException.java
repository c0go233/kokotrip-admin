package com.kokotripadmin.exception.activity;

public class ActivityNameDuplicateException extends Throwable {

    public ActivityNameDuplicateException(String activityName) {
        super(activityName +  " 은(는) 이미 추가한 액티비티입니다.");
    }
}

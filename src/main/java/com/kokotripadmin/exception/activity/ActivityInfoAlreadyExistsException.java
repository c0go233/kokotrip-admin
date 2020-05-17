package com.kokotripadmin.exception.activity;

public class ActivityInfoAlreadyExistsException extends Exception {

    public ActivityInfoAlreadyExistsException(String activityName, String supportLanguageName) {
        super("액티비티: " + activityName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}

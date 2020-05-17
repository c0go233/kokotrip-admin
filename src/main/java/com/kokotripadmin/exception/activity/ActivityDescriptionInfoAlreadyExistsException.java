package com.kokotripadmin.exception.activity;

public class ActivityDescriptionInfoAlreadyExistsException extends Exception {

    public ActivityDescriptionInfoAlreadyExistsException(String activityDescriptionName, String supportLanguageName) {
        super("액티비티 설명: " + activityDescriptionName + " 는(은) 이미 " + supportLanguageName + " 로 작성된 번역정보가 있습니다.");
    }
}

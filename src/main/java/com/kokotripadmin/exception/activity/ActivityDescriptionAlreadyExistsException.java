package com.kokotripadmin.exception.activity;

public class ActivityDescriptionAlreadyExistsException extends Exception {


    public ActivityDescriptionAlreadyExistsException(String activityDescriptionName) {
        super(activityDescriptionName + " 는(은) 이미 존재하는 액티비티설명 입니다.");
    }
}

package com.kokotripadmin.exception.state;

public class StateNameDuplicateException extends Exception {

    public StateNameDuplicateException(String stateName) {
        super(stateName +  " 는(은) 이미 추가한 시,도 입니다");
    }
}

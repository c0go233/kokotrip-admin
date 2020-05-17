package com.kokotripadmin.exception.state;

public class StateNotFoundException extends Exception {

    public StateNotFoundException() {
        super("시, 도 가 존재하지 않습니다.");
    }
}

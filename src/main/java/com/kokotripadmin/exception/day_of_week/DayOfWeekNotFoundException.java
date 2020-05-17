package com.kokotripadmin.exception.day_of_week;

public class DayOfWeekNotFoundException extends Exception {

    public DayOfWeekNotFoundException() {
        super("요청하신 요일은 존재하지않습니다.");
    }
}

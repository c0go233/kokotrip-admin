package com.kokotripadmin.exception.trading_hour_type;

public class TradingHourTypeNotFoundException extends Exception {

    public TradingHourTypeNotFoundException() {
        super("요청하신 영업시간타입은 존재하지 않습니다.");
    }
}

package com.kokotripadmin.constant;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public enum TradingHourTypeEnum {
    영업(1),
    휴무(2)
    ;

    private final int id;

    TradingHourTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Map<Integer, String> convertToLinkedHashMap() {
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        for (TradingHourTypeEnum tradingHourTypeEnum : TradingHourTypeEnum.values()) {
            linkedHashMap.put(tradingHourTypeEnum.getId(), tradingHourTypeEnum.name());
        }

        return linkedHashMap;
    }

    public static Optional<TradingHourTypeEnum> valueOf(int id) {
        return Arrays.stream(values()).filter(tradingHourTypeEnum -> tradingHourTypeEnum.id == id).findFirst();
    }
}

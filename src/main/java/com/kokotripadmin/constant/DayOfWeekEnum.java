package com.kokotripadmin.constant;

import java.util.*;

public enum DayOfWeekEnum {
    월요일(2),
    화요일(3),
    수요일(4),
    목요일(5),
    금요일(6),
    토요일(7),
    일요일(1)
    ;

    private final int id;

    DayOfWeekEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static LinkedHashMap<Integer, String> convertToLinkedHashMap() {
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        for (DayOfWeekEnum dayOfWeekEnum : DayOfWeekEnum.values()) {
            linkedHashMap.put(dayOfWeekEnum.getId(), dayOfWeekEnum.name());
        }
        return linkedHashMap;
    }

    public static Optional<DayOfWeekEnum> valueOf(int id) {
        return Arrays.stream(values()).filter(dayOfWeekEnum -> dayOfWeekEnum.id == id).findFirst();
    }
}

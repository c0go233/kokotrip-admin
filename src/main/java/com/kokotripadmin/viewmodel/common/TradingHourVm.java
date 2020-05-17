package com.kokotripadmin.viewmodel.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;

@Getter
@Setter
public class TradingHourVm {

    private Integer id;
    private Integer dayOfWeekId;
    private String  dayOfWeekName;
    private Integer tradingHourTypeId;
    private String  tradingHourTypeName;
    private Time    openTime;
    private Time    closeTime;
}

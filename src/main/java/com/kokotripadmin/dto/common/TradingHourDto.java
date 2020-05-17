package com.kokotripadmin.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;


@Getter
@Setter
public class TradingHourDto {

    private Integer id;
    private Integer dayOfWeekId;
    private String dayOfWeekName;
    private Integer tradingHourTypeId;
    private String tradingHourTypeName;
    private Time openTime;
    private Time closeTime;

}

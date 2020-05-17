package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.entity.common.TradingHourType;
import com.kokotripadmin.exception.trading_hour_type.TradingHourTypeNotFoundException;

import java.util.Map;

public interface TradingHourTypeEntityService {
    TradingHourType findEntityById(Integer tradingHourTypeId) throws TradingHourTypeNotFoundException;
}

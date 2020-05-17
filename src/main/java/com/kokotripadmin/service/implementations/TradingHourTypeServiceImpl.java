package com.kokotripadmin.service.implementations;


import com.kokotripadmin.dao.interfaces.common.TradingHourTypeDao;
import com.kokotripadmin.entity.common.TradingHourType;
import com.kokotripadmin.exception.trading_hour_type.TradingHourTypeNotFoundException;
import com.kokotripadmin.service.entityinterfaces.TradingHourTypeEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TradingHourTypeServiceImpl implements TradingHourTypeEntityService {


    private final TradingHourTypeDao tradingHourTypeDao;

    @Autowired
    public TradingHourTypeServiceImpl(TradingHourTypeDao tradingHourTypeDao) {
        this.tradingHourTypeDao = tradingHourTypeDao;
    }

    public TradingHourType findEntityById(Integer tradingHourTypeId) throws TradingHourTypeNotFoundException {
        TradingHourType tradingHourType = tradingHourTypeDao.findById(tradingHourTypeId).orElseThrow(() -> new TradingHourTypeNotFoundException());
        return  tradingHourType;
    }

}

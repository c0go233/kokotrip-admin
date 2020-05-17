package com.kokotripadmin.dao.interfaces.common;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.common.TradingHourType;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingHourTypeDao extends GenericDao<TradingHourType, Integer> {
}

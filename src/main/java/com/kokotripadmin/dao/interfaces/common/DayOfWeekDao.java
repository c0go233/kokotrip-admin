package com.kokotripadmin.dao.interfaces.common;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.common.DayOfWeek;
import org.springframework.stereotype.Repository;

@Repository
public interface DayOfWeekDao extends GenericDao<DayOfWeek, Integer> {
}

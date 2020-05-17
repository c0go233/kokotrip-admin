package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.entity.common.DayOfWeek;
import com.kokotripadmin.exception.day_of_week.DayOfWeekNotFoundException;

import java.util.Map;

public interface DayOfWeekEntityService {

    DayOfWeek findEntityById(Integer dayOfWeekId) throws DayOfWeekNotFoundException;
}

package com.kokotripadmin.service.implementations;


import com.kokotripadmin.dao.interfaces.common.DayOfWeekDao;
import com.kokotripadmin.entity.common.DayOfWeek;
import com.kokotripadmin.exception.day_of_week.DayOfWeekNotFoundException;
import com.kokotripadmin.service.entityinterfaces.DayOfWeekEntityService;
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
public class DayOfWeekServiceImpl implements DayOfWeekEntityService {

    private final DayOfWeekDao dayOfWeekDao;

    @Autowired
    public DayOfWeekServiceImpl(DayOfWeekDao dayOfWeekDao) {
        this.dayOfWeekDao = dayOfWeekDao;
    }

    public DayOfWeek findEntityById(Integer dayOfWeekId) throws DayOfWeekNotFoundException {
        DayOfWeek dayOfWeek = dayOfWeekDao.findById(dayOfWeekId).orElseThrow(() -> new DayOfWeekNotFoundException());
        return dayOfWeek;
    }

}

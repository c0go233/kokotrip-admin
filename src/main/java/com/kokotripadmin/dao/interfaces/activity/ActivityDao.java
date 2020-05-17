package com.kokotripadmin.dao.interfaces.activity;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.activity.Activity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDao extends GenericDao<Activity, Integer>, JpaSpecificationExecutor<Activity> {
}

package com.kokotripadmin.dao.interfaces.activity;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.activity.ActivityInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityInfoDao extends GenericDao<ActivityInfo, Integer>, JpaSpecificationExecutor<ActivityInfo> {
}

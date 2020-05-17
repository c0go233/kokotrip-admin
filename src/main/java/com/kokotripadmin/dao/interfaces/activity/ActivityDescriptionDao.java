package com.kokotripadmin.dao.interfaces.activity;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.activity.ActivityDescription;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDescriptionDao extends GenericDao<ActivityDescription, Integer>, JpaSpecificationExecutor<ActivityDescription> {
}

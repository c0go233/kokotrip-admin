package com.kokotripadmin.dao.interfaces;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.activity.ActivityTicketDescriptionInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityTicketDescriptionInfoDao extends GenericDao<ActivityTicketDescriptionInfo, Integer>,
                                                          JpaSpecificationExecutor<ActivityTicketDescriptionInfo> {
}

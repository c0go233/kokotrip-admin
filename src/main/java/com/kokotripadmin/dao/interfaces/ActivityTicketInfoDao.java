package com.kokotripadmin.dao.interfaces;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.activity.ActivityTicket;
import com.kokotripadmin.entity.activity.ActivityTicketInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityTicketInfoDao extends GenericDao<ActivityTicketInfo, Integer>, JpaSpecificationExecutor<ActivityTicketInfo> {
}

package com.kokotripadmin.dao.interfaces;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.activity.ActivityTicket;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityTicketDao extends GenericDao<ActivityTicket, Integer>, JpaSpecificationExecutor<ActivityTicket> {
}

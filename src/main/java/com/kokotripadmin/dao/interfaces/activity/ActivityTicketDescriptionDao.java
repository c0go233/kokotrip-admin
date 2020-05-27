package com.kokotripadmin.dao.interfaces.activity;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.activity.ActivityTicketDescription;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityTicketDescriptionDao extends GenericDao<ActivityTicketDescription, Integer>,
                                                      JpaSpecificationExecutor<ActivityTicketDescription> {
}

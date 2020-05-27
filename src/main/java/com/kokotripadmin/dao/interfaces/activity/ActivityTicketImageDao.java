package com.kokotripadmin.dao.interfaces.activity;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.activity.ActivityTicketImage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityTicketImageDao extends GenericDao<ActivityTicketImage, Integer>, JpaSpecificationExecutor<ActivityTicketImage> {
}

package com.kokotripadmin.dao.interfaces.activity;


import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.activity.ActivityTicketDescriptionImage;
import com.kokotripadmin.entity.activity.ActivityTicketImage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityTicketDescriptionImageDao extends GenericDao<ActivityTicketDescriptionImage, Integer>,
                                                           JpaSpecificationExecutor<ActivityTicketDescriptionImage> {
}

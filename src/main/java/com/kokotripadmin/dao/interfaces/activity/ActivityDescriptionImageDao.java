package com.kokotripadmin.dao.interfaces.activity;


import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.activity.ActivityDescriptionImage;
import com.kokotripadmin.entity.activity.ActivityImage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDescriptionImageDao extends GenericDao<ActivityDescriptionImage, Integer>,
                                                     JpaSpecificationExecutor<ActivityDescriptionImage> {
}

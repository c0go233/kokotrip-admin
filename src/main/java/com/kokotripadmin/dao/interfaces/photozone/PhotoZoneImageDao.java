package com.kokotripadmin.dao.interfaces.photozone;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.photozone.PhotoZoneImage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface PhotoZoneImageDao extends GenericDao<PhotoZoneImage, Integer>, JpaSpecificationExecutor<PhotoZoneImage> {
}

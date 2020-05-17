package com.kokotripadmin.dao.interfaces;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.photozone.PhotoZone;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoZoneDao extends GenericDao<PhotoZone, Integer>, JpaSpecificationExecutor<PhotoZone> {
}

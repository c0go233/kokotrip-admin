package com.kokotripadmin.dao.interfaces.photozone;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.photozone.PhotoZoneInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoZoneInfoDao extends GenericDao<PhotoZoneInfo, Integer>, JpaSpecificationExecutor<PhotoZoneInfo> {
}

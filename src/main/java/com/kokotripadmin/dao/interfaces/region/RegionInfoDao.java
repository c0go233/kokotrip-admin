package com.kokotripadmin.dao.interfaces.region;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.region.RegionInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionInfoDao extends GenericDao<RegionInfo, Integer>, JpaSpecificationExecutor<RegionInfo> {
}

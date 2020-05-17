package com.kokotripadmin.dao.interfaces.region;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.region.Region;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionDao extends GenericDao<Region, Integer>, JpaSpecificationExecutor<Region> {
}

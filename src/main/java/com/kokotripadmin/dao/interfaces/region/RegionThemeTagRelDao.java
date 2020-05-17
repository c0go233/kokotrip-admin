package com.kokotripadmin.dao.interfaces.region;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.region.RegionThemeTagRel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionThemeTagRelDao extends GenericDao<RegionThemeTagRel, Integer>, JpaSpecificationExecutor<RegionThemeTagRel> {
}

package com.kokotripadmin.dao.interfaces.region;


import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.region.RegionThemeRel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionThemeRelDao extends GenericDao<RegionThemeRel, Integer>, JpaSpecificationExecutor<RegionThemeRel> {
}

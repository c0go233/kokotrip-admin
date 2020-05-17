package com.kokotripadmin.dao.interfaces.city;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.city.CityInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CityInfoDao extends GenericDao<CityInfo, Integer>, JpaSpecificationExecutor<CityInfo> {
}

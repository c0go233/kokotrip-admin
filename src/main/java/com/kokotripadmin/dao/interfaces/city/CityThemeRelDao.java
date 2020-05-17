package com.kokotripadmin.dao.interfaces.city;


import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.city.CityThemeRel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CityThemeRelDao extends GenericDao<CityThemeRel, Integer>, JpaSpecificationExecutor<CityThemeRel> {
}

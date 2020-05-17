package com.kokotripadmin.dao.interfaces.city;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.city.City;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDao extends GenericDao<City, Integer>, JpaSpecificationExecutor<City> {
}

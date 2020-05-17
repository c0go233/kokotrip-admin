package com.kokotripadmin.dao.interfaces.tourspot;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tourspot.TourSpotDescription;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TourSpotDescriptionDao extends GenericDao<TourSpotDescription, Integer>,
                                                JpaSpecificationExecutor<TourSpotDescription> {
}

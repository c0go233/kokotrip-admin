package com.kokotripadmin.dao.interfaces.tourspot;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.tourspot.TourSpotDescriptionImage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TourSpotDescriptionImageDao extends GenericDao<TourSpotDescriptionImage, Integer>,
                                                     JpaSpecificationExecutor<TourSpotDescriptionImage> {
}

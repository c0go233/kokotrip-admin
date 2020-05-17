package com.kokotripadmin.datatablesdao;

import com.kokotripadmin.entity.tourspot.TourSpot;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourSpotDataTablesDao extends DataTablesRepository<TourSpot, Integer> {
}

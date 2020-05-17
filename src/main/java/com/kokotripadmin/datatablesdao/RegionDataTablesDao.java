package com.kokotripadmin.datatablesdao;

import com.kokotripadmin.entity.region.Region;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionDataTablesDao extends DataTablesRepository<Region, Integer> {
}

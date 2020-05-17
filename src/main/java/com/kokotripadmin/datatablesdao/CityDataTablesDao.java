package com.kokotripadmin.datatablesdao;

import com.kokotripadmin.entity.city.City;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDataTablesDao extends DataTablesRepository<City, Integer> {
}

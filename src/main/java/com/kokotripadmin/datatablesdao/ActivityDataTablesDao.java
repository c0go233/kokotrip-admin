package com.kokotripadmin.datatablesdao;

import com.kokotripadmin.entity.activity.Activity;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDataTablesDao extends DataTablesRepository<Activity, Integer> {
}

package com.kokotripadmin.datatablesdao;

import com.kokotripadmin.entity.State;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDataTablesDao extends DataTablesRepository<State, Integer> {
}

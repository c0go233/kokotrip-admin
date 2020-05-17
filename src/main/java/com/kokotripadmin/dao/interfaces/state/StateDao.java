package com.kokotripadmin.dao.interfaces.state;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.State;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface StateDao extends GenericDao<State, Integer>, JpaSpecificationExecutor<State> {
}

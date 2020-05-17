package com.kokotripadmin.service.entityinterfaces;

import com.kokotripadmin.dto.state.StateDto;
import com.kokotripadmin.entity.State;
import com.kokotripadmin.exception.state.StateNotFoundException;

public interface StateEntityService {
    State findEntityById(Integer stateId) throws StateNotFoundException;
}

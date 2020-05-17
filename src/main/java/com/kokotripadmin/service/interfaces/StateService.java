package com.kokotripadmin.service.interfaces;

import com.kokotripadmin.dto.state.StateDto;
import com.kokotripadmin.exception.state.StateNameDuplicateException;
import com.kokotripadmin.exception.state.StateNotFoundException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import java.util.LinkedHashMap;

public interface StateService {
    Integer save(StateDto stateDto) throws StateNameDuplicateException, StateNotFoundException;
    StateDto findById(Integer stateId) throws StateNotFoundException;
    DataTablesOutput<StateDto> findAllByPagination(DataTablesInput dataTablesInput);
    StateDto findByIdInDetail(Integer stateId) throws StateNotFoundException;
    LinkedHashMap<Integer, String> findAllAsLinkedHashMap();
    void delete(Integer stateId) throws StateNotFoundException;
    String findNameById(Integer stateId) throws StateNotFoundException;
}

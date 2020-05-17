package com.kokotripadmin.service.implementations;


import com.kokotripadmin.dao.interfaces.state.StateDao;
import com.kokotripadmin.datatablesdao.StateDataTablesDao;
import com.kokotripadmin.dto.state.StateDto;
import com.kokotripadmin.entity.State;
import com.kokotripadmin.entity.State_;
import com.kokotripadmin.exception.state.StateNameDuplicateException;
import com.kokotripadmin.exception.state.StateNotFoundException;
import com.kokotripadmin.service.entityinterfaces.StateEntityService;
import com.kokotripadmin.service.interfaces.StateService;
import com.kokotripadmin.spec.StateSpec;
import com.kokotripadmin.util.Convert;
import org.apache.taglibs.standard.tag.common.xml.ParseSupport;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StateServiceImpl implements StateService, StateEntityService {

    private final ModelMapper        modelMapper;
    private final StateDataTablesDao stateDataTablesDao;
    private final Convert            convert;
    private final StateDao           stateDao;


    @Autowired
    public StateServiceImpl(StateDao stateDao,
                            ModelMapper modelMapper,
                            StateDataTablesDao stateDataTablesDao,
                            Convert convert) {

        this.stateDao = stateDao;
        this.modelMapper = modelMapper;
        this.stateDataTablesDao = stateDataTablesDao;
        this.convert = convert;
    }


    @Override
    public State findEntityById(Integer stateId) throws StateNotFoundException {
        State state = stateDao.findById(stateId).orElseThrow(() -> new StateNotFoundException());
        return state;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public StateDto findByIdInDetail(Integer stateId) throws StateNotFoundException {
        State state = findEntityById(stateId);
        return convert.stateToDtoInDetail(state);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public StateDto findById(Integer stateId) throws StateNotFoundException {
        State state = findEntityById(stateId);
        return convert.stateToDto(state);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public DataTablesOutput<StateDto> findAllByPagination(DataTablesInput dataTablesInput) {
        return stateDataTablesDao.findAll(dataTablesInput, convert.stateToDto());
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public String findNameById(Integer stateId) throws StateNotFoundException {
        if (stateId == null) return  null;
        State state = findEntityById(stateId);
        return state.getName();
    }




    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public LinkedHashMap<Integer, String> findAllAsLinkedHashMap() {
        List<State> stateList = stateDao.findAll(StateSpec.findByEnabled(true));
        LinkedHashMap<Integer, String> stateLinkedHashMap = stateList.stream()
                                                                     .collect(Collectors.toMap(State::getId,
                                                                                               State::getName,
                                                                                               (oKey, nKey) -> oKey,
                                                                                               LinkedHashMap::new));
        return stateLinkedHashMap;
    }

    @Override
    @Transactional
    public void delete(Integer stateId) throws StateNotFoundException {
        State state = findEntityById(stateId);
        stateDao.delete(state);
    }


    private boolean existsByName(String stateName) {
        long count = stateDao.count(StateSpec.findByName(stateName));
        return count > 0;
    }

    @Override
    @Transactional
    public Integer save(StateDto stateDto) throws StateNameDuplicateException, StateNotFoundException {

        State state;
        String newStateName = stateDto.getName();
        if (stateDto.getId() == null) {

            if (existsByName(newStateName)) throw new StateNameDuplicateException(newStateName);
            state = modelMapper.map(stateDto, State.class);
        } else {

            if (!stateDto.getName().equals(newStateName) && existsByName(newStateName))
                throw new StateNameDuplicateException(newStateName);

            state = findEntityById(stateDto.getId());
            state.clone(stateDto);
            state.setUpdatedAt(new Date());
        }
        stateDao.save(state);
        return state.getId();
    }


}

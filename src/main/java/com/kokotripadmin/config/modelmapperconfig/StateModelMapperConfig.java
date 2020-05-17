package com.kokotripadmin.config.modelmapperconfig;

import com.kokotripadmin.dto.region.RegionDto;
import com.kokotripadmin.dto.state.StateDto;
import com.kokotripadmin.entity.region.Region;
import com.kokotripadmin.viewmodel.state.StateVm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class StateModelMapperConfig {


    public static void stateDtoToVm(ModelMapper modelMapper) {
        TypeMap<StateDto, StateVm> typeMap = modelMapper.createTypeMap(StateDto.class, StateVm.class);
        typeMap.addMapping(StateDto::getCityDtoList, StateVm::setCityVmList);
    }
}

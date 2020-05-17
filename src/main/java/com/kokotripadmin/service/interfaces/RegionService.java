package com.kokotripadmin.service.interfaces;

import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.region.RegionDto;
import com.kokotripadmin.dto.region.RegionInfoDto;
import com.kokotripadmin.exception.city.CityInfoNotFoundException;
import com.kokotripadmin.exception.city.CityNotFoundException;
import com.kokotripadmin.exception.region.*;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface RegionService {

    RegionDto findById(Integer regionId) throws RegionNotFoundException;
    List<RegionDto> findAllByEnabledAndCityEnabled(boolean enabled, boolean cityEnabled);
    DataTablesOutput<RegionDto> findAllByPagination(DataTablesInput dataTablesInput);
    RegionDto findByIdInDetail(Integer regionId) throws RegionNotFoundException;
    void deleteInfo(Integer regionInfoId) throws RegionInfoNotEditableException, RegionInfoNotFoundException;

    Integer save(RegionDto regionDto)
    throws CityNotFoundException, RegionNameDuplicateException, CityInfoNotFoundException, RegionNotFoundException,
           SupportLanguageNotFoundException, RegionInfoAlreadyExistsException;

    RegionInfoDto saveInfo(RegionInfoDto regionInfoDto) throws RegionNotFoundException,
            RegionInfoAlreadyExistsException, CityInfoNotFoundException, SupportLanguageNotFoundException,
            RegionInfoNotFoundException, RegionNotEditableException;

    void delete(Integer regionId) throws RegionNotFoundException;
}

package com.kokotripadmin.service.interfaces;

import com.kokotripadmin.dto.city.CityInfoDto;
import com.kokotripadmin.dto.city.CityDto;
import com.kokotripadmin.exception.city.*;
import com.kokotripadmin.exception.state.StateNotFoundException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.LinkedHashMap;

public interface CityService {
    CityDto findById(Integer cityId) throws CityNotFoundException;
    CityDto findByIdInDetail(Integer cityId) throws CityNotFoundException;
    Integer save(CityDto cityDto)
    throws StateNotFoundException, CityNameDuplicateException, CityNotFoundException, CityInfoAlreadyExistsException,
           SupportLanguageNotFoundException;
    void delete(Integer cityId) throws CityNotFoundException;
    void deleteInfo(Integer cityInfoId) throws CityInfoNotFoundException, CityInfoNotDeletableException;

    CityInfoDto saveInfo(CityInfoDto cityInfoDto)
    throws CityInfoAlreadyExistsException, SupportLanguageNotFoundException, CityNotFoundException,
           CityInfoNotFoundException, CityInfoNotEditableException;

    LinkedHashMap<Integer, String> findAllAsLinkedHashMap();
    DataTablesOutput<CityDto> findAllByPagination(DataTablesInput input);
    void deleteImage(Integer imageId);
}

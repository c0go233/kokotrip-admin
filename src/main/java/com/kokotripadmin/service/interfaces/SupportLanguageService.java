package com.kokotripadmin.service.interfaces;

import com.kokotripadmin.dto.common.SupportLanguageDto;
import com.kokotripadmin.exception.support_language.SupportLanguageNameDuplicateException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

import java.util.LinkedHashMap;
import java.util.List;


public interface SupportLanguageService {
    LinkedHashMap<Integer, String> findAllAsLinkedHashMap();
    List<SupportLanguageDto> findAll();
    SupportLanguageDto findById(Integer supportLanguageId) throws SupportLanguageNotFoundException;

    void save(SupportLanguageDto supportLanguageDto)
    throws SupportLanguageNotFoundException, SupportLanguageNameDuplicateException;

    void delete(Integer supportLanguageId) throws SupportLanguageNotFoundException;
}

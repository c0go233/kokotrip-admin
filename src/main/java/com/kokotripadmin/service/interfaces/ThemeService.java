package com.kokotripadmin.service.interfaces;

import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.theme.ThemeDto;
import com.kokotripadmin.dto.theme.ThemeInfoDto;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.TagNameDuplicateException;
import com.kokotripadmin.exception.theme.*;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.LinkedHashMap;

public interface ThemeService {

    ThemeDto findById(Integer themeId) throws ThemeNotFoundException;
    DataTablesOutput<ThemeDto> findAllByPagination(DataTablesInput dataTablesInput);
    ThemeDto findByIdInDetail(Integer themeId) throws ThemeNotFoundException;
    LinkedHashMap<Integer, String> findAllAsLinkedHashMap();
    String findNameById(Integer themeId) throws ThemeNotFoundException;

    Integer save(ThemeDto themeDto)
    throws ThemeNameDuplicateException, TagNameDuplicateException,
            SupportLanguageNotFoundException, ThemeNotFoundException, ThemeInfoAlreadyExistsException;

    ThemeInfoDto saveInfo(ThemeInfoDto themeInfoDto) throws SupportLanguageNotFoundException,
            ThemeNotFoundException, ThemeInfoAlreadyExistsException, ThemeInfoNotEditableException,
            ThemeInfoNotFoundException;

    void deleteInfo(Integer themeInfoId) throws ThemeInfoNotFoundException, ThemeInfoNotDeletableException;

    void delete(Integer themeId) throws ThemeNotFoundException;
}

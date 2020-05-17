package com.kokotripadmin.service.interfaces;

import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.tag.TagDto;
import com.kokotripadmin.dto.tag.TagInfoDto;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.*;
import com.kokotripadmin.exception.theme.ThemeNotFoundException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.LinkedHashMap;
import java.util.List;

public interface TagService {

    LinkedHashMap<Integer, String> findAllAsLinkedHashMap();
    TagDto findByIdInDetail(Integer tagId) throws TagNotFoundException;
    TagDto findById(Integer tagId) throws TagNotFoundException;

    Integer save(TagDto tagDto) throws ThemeNotFoundException, TagNotFoundException, SupportLanguageNotFoundException,
                                       TagInfoAlreadyExistsException, TagNameDuplicateException,
                                       TagInfoNotFoundException;
    TagInfoDto saveInfo(TagInfoDto tagInfoDto) throws SupportLanguageNotFoundException,
                                                                  TagNotFoundException, TagInfoAlreadyExistsException,
                                                                  TagInfoNotEditableException, TagInfoNotFoundException;

    DataTablesOutput<TagDto> findAllByPagination(DataTablesInput dataTablesInput);

    void deleteInfo(Integer tagInfoId) throws TagInfoNotFoundException, TagInfoNotDeletableException;

    void delete(Integer tagId) throws TagNotFoundException;
}

package com.kokotripadmin.service.interfaces;

import com.kokotripadmin.dto.city.CityImageDto;
import com.kokotripadmin.dto.city.CityInfoDto;
import com.kokotripadmin.dto.city.CityDto;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.city.*;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.state.StateNotFoundException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

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
    void deleteImage(Integer imageId) throws CityImageNotFoundException, RepImageNotDeletableException;
    String findNameById(Integer cityId) throws CityNotFoundException;

    Integer saveImage(CityImageDto cityImageDto)
    throws CityNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException;
    String findImageDirectoryById(Integer cityId) throws CityNotFoundException;

    void updateRepImage(Integer imageId) throws CityImageNotFoundException;

    void updateImageOrder(List<Integer> imageIdList);
}

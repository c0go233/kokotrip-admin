package com.kokotripadmin.service.implementations;

import com.kokotripadmin.dao.interfaces.common.SupportLanguageDao;
import com.kokotripadmin.dto.common.SupportLanguageDto;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.exception.support_language.SupportLanguageNameDuplicateException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.service.entityinterfaces.SupportLanguageEntityService;
import com.kokotripadmin.service.interfaces.SupportLanguageService;
import com.kokotripadmin.spec.SupportLanguageSpec;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupportLanguageServiceImpl implements SupportLanguageService, SupportLanguageEntityService {

    private final SupportLanguageDao supportLanguageDao;
    private final ModelMapper modelMapper;

    @Autowired
    public SupportLanguageServiceImpl(SupportLanguageDao supportLanguageDao, ModelMapper modelMapper) {
        this.supportLanguageDao = supportLanguageDao;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public SupportLanguageDto findById(Integer supportLanguageId) throws SupportLanguageNotFoundException {

        SupportLanguage supportLanguage = findEntityById(supportLanguageId);
        return modelMapper.map(supportLanguage, SupportLanguageDto.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public SupportLanguage findEntityById(Integer id) throws SupportLanguageNotFoundException {

        return supportLanguageDao.findById(id).orElseThrow(() -> new SupportLanguageNotFoundException());
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public LinkedHashMap<Integer, String> findAllAsLinkedHashMap() {

//        List<SupportLanguage> supportLanguageList = supportLanguageDao.findAll(SupportLanguageSpec.findByEnabled(true));
        List<SupportLanguage> supportLanguageList = supportLanguageDao.findAllByEnabled(true);
        return supportLanguageList.stream().collect(Collectors.toMap(SupportLanguage::getId,
                                                                     SupportLanguage::getName,
                                                                     (oldKey, newKey) -> oldKey,
                                                                     LinkedHashMap::new));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SupportLanguageDto> findAll() {
        List<SupportLanguage> supportLanguageList = supportLanguageDao.findAll();
        return modelMapper.map(supportLanguageList, new TypeToken<List<SupportLanguageDto>>() {}.getType());
    }


    private boolean existsByName(String supportLanguageName) {
        long count = supportLanguageDao.count(SupportLanguageSpec.findByName(supportLanguageName));
        return count > 0;
    }

    private boolean existsByDisplayName(String supportLanguageDisplayName) {
        long count = supportLanguageDao.count(SupportLanguageSpec.findByDisplayName(supportLanguageDisplayName));
        return count > 0;
    }


    @Override
    @Transactional
    public void save(SupportLanguageDto supportLanguageDto)
    throws SupportLanguageNotFoundException, SupportLanguageNameDuplicateException {

        SupportLanguage supportLanguage;
        if (supportLanguageDto.getId() == null) supportLanguage = create(supportLanguageDto);
        else {
            supportLanguage = update(supportLanguageDto);
            supportLanguage.setUpdatedAt(new Date());
        }
        supportLanguageDao.save(supportLanguage);
    }

    private SupportLanguage create(SupportLanguageDto supportLanguageDto) throws SupportLanguageNameDuplicateException {

        if (existsByName(supportLanguageDto.getName()))
            throw new SupportLanguageNameDuplicateException(supportLanguageDto.getName());
        else if (existsByDisplayName(supportLanguageDto.getDisplayName()))
            throw new SupportLanguageNameDuplicateException(supportLanguageDto.getDisplayName());

        SupportLanguage supportLanguage = modelMapper.map(supportLanguageDto, SupportLanguage.class);
        return supportLanguage;
    }

    private SupportLanguage update(SupportLanguageDto supportLanguageDto)
    throws SupportLanguageNotFoundException, SupportLanguageNameDuplicateException {

        SupportLanguage supportLanguage = findEntityById(supportLanguageDto.getId());
        if (!supportLanguage.getName().equals(supportLanguageDto.getName()) && existsByName(supportLanguageDto.getName()))
            throw new SupportLanguageNameDuplicateException(supportLanguageDto.getName());
        else if (!supportLanguage.getDisplayName().equals(supportLanguageDto.getDisplayName())
                && existsByDisplayName(supportLanguageDto.getDisplayName()))
            throw new SupportLanguageNameDuplicateException(supportLanguage.getDisplayName());

        supportLanguage.clone(supportLanguageDto);
        return supportLanguage;
    }

    @Override
    @Transactional
    public void delete(Integer supportLanguageId) throws SupportLanguageNotFoundException {
        SupportLanguage supportLanguage = findEntityById(supportLanguageId);
        supportLanguageDao.delete(supportLanguage);
    }


}

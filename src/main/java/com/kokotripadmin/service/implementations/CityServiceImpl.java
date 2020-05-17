package com.kokotripadmin.service.implementations;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.city.*;
import com.kokotripadmin.datatablesdao.CityDataTablesDao;
import com.kokotripadmin.dto.city.CityInfoDto;
import com.kokotripadmin.dto.city.CityDto;
import com.kokotripadmin.entity.State;
import com.kokotripadmin.entity.city.*;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tag.Theme;
import com.kokotripadmin.exception.city.*;
import com.kokotripadmin.exception.state.StateNotFoundException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.service.entityinterfaces.CityEntityService;
import com.kokotripadmin.service.entityinterfaces.StateEntityService;
import com.kokotripadmin.service.entityinterfaces.SupportLanguageEntityService;
import com.kokotripadmin.service.interfaces.CityService;
import com.kokotripadmin.spec.CitySpec;
import com.kokotripadmin.util.Convert;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CityServiceImpl implements CityService, CityEntityService {

    private final ModelMapper        modelMapper;
    private final Convert convert;

    private final CityDao            cityDao;
    private final CityInfoDao        cityInfoDao;
    private final CityThemeRelDao    cityThemeRelDao;
    private final CityThemeTagRelDao cityThemeTagRelDao;
    private final CityDataTablesDao  cityDataTablesDao;
    private final CityImageDao       cityImageDao;

    private final SupportLanguageEntityService supportLanguageEntityService;
    private final StateEntityService           stateEntityService;

    @Autowired
    public CityServiceImpl(Convert convert,
                           CityDao cityDao,
                           CityInfoDao cityInfoDao,
                           ModelMapper modelMapper,
                           CityThemeRelDao cityThemeRelDao,
                           CityThemeTagRelDao cityThemeTagRelDao,
                           CityDataTablesDao cityDataTablesDao,
                           CityImageDao cityImageDao,
                           SupportLanguageEntityService supportLanguageEntityService,
                           StateEntityService stateEntityService) {

        this.convert = convert;
        this.cityDao = cityDao;
        this.modelMapper = modelMapper;
        this.cityDataTablesDao = cityDataTablesDao;
        this.cityImageDao = cityImageDao;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.cityInfoDao = cityInfoDao;
        this.cityThemeRelDao = cityThemeRelDao;
        this.cityThemeTagRelDao = cityThemeTagRelDao;
        this.stateEntityService = stateEntityService;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public CityDto findById(Integer cityId) throws CityNotFoundException {

        City city = findEntityById(cityId);
        return modelMapper.map(city, CityDto.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public City findEntityById(Integer cityId) throws CityNotFoundException {

        City city = cityDao.findById(cityId).orElseThrow(() -> new CityNotFoundException());
        return city;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public CityDto findByIdInDetail(Integer cityId) throws CityNotFoundException {

        City city = findEntityById(cityId);
        List<CityThemeRel> cityThemeRelList = cityThemeRelDao.findAll(CitySpec.findThemeRelById(cityId, true));
        return convert.cityToDtoInDetail(city, cityThemeRelList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public DataTablesOutput<CityDto> findAllByPagination(DataTablesInput input) {
        return cityDataTablesDao.findAll(input, convert.cityToDto());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public LinkedHashMap<Integer, String> findAllAsLinkedHashMap() {

        List<City> cityList = cityDao.findAll(CitySpec.findByEnabled(true));
        return cityList.stream().collect(Collectors.toMap(City::getId, City::getName, (oKey, nKey) -> oKey, LinkedHashMap::new));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public boolean imageExistsByIdAndImageName(Integer cityId, String imageName) {
        long count = cityImageDao.count(CitySpec.findImageByIdAndImageName(cityId, imageName));
        return count > 0;
    }


    @Override
    public Integer saveImage(Integer cityId, String path, String fileName, String fileType) throws CityNotFoundException {
        City city = findEntityById(cityId);
        CityImage cityImage = new CityImage(city, path, fileName, fileType);
        cityImageDao.save(cityImage);
        return cityImage.getId();
    }



    @Override
    @Transactional
    public Integer save(CityDto cityDto)
    throws StateNotFoundException, CityNameDuplicateException, CityNotFoundException, CityInfoAlreadyExistsException,
           SupportLanguageNotFoundException {

        State state = stateEntityService.findEntityById(cityDto.getStateId());

        City city;
        if (cityDto.getId() == null) city = create(cityDto, state);
        else city = update(state, cityDto);

        cityDao.save(city);
        return city.getId();
    }

    @Override
    @Transactional
    public void delete(Integer cityId) throws CityNotFoundException {
        City city = findEntityById(cityId);
        cityDao.delete(city);
    }

    private boolean existsByName(String cityName) {
        long count = cityDao.count(CitySpec.findByName(cityName));
        return count > 0;
    }

    private City create(CityDto cityDto, State state)
    throws CityInfoAlreadyExistsException, SupportLanguageNotFoundException, CityNameDuplicateException,
           CityNotFoundException {

        String cityName = cityDto.getName();
        if (existsByName(cityName)) throw new CityNameDuplicateException(cityName);

        City city = modelMapper.map(cityDto, City.class);
        city.setState(state);

        CityInfoDto cityInfoDto = modelMapper.map(cityDto, CityInfoDto.class);
        cityInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());

        CityInfo cityInfo = createInfo(city, cityInfoDto);
        city.getCityInfoList().add(cityInfo);

        return city;
    }

    private City update(State state, CityDto cityDto)
    throws CityNotFoundException, CityNameDuplicateException {

        City city = findEntityById(cityDto.getId());

        String cityName = cityDto.getName();
        if (!city.getName().equals(cityName) && existsByName(cityName))
            throw new CityNameDuplicateException(cityName);

        city.clone(cityDto);
        city.setState(state);
        city.updateInfos();
        city.setUpdatedAt(new Date());

        return city;
    }

//  ===============================================CITY INFO=======================================================  //


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public CityInfo findInfoEntityByInfoId(Integer infoId) throws CityInfoNotFoundException {
        CityInfo cityInfo = cityInfoDao.findById(infoId).orElseThrow(() -> new CityInfoNotFoundException());
        return cityInfo;
    }

    @Override
    @Transactional
    public CityInfo findInfoEntityByIdAndSupportLanguageId(Integer id, Integer supportLanguageId)
    throws CityInfoNotFoundException {
        CityInfo cityInfo = cityInfoDao.findOne(CitySpec.findInfoByIdAndSupportLanguageId(id, supportLanguageId))
                                       .orElseThrow(() -> new CityInfoNotFoundException());
        return cityInfo;
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public CityInfoDto saveInfo(CityInfoDto cityInfoDto)
    throws CityInfoAlreadyExistsException, SupportLanguageNotFoundException, CityNotFoundException,
           CityInfoNotFoundException, CityInfoNotEditableException {

        CityInfo cityInfo;
        if (cityInfoDto.getId() == null) cityInfo = createInfo(null, cityInfoDto);
        else cityInfo = updateInfo(cityInfoDto);

        cityInfoDao.save(cityInfo);
        return modelMapper.map(cityInfo, CityInfoDto.class);
    }

    private boolean infoExistsByIdAndSupportLanguageId(Integer cityId, Integer supportLanguageId) {
        long count = cityInfoDao.count(CitySpec.findInfoByIdAndSupportLanguageId(cityId, supportLanguageId));
        return count > 0;
    }

    private CityInfo createInfo(City city, CityInfoDto cityInfoDto)
    throws CityInfoAlreadyExistsException, CityNotFoundException, SupportLanguageNotFoundException {

        Integer supportLanguageId = cityInfoDto.getSupportLanguageId();

        if (city == null) city = findEntityById(cityInfoDto.getCityId());

        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(city.getId(), supportLanguageId))
            throw new CityInfoAlreadyExistsException(city.getName(), supportLanguage.getName());

        CityInfo cityInfo = modelMapper.map(cityInfoDto, CityInfo.class);
        cityInfo.setForeignEntities(city, supportLanguage);
        cityInfo.denormalize(city);

        return cityInfo;
    }

    private CityInfo updateInfo(CityInfoDto cityInfoDto)
    throws CityInfoNotFoundException, CityInfoNotEditableException {

        CityInfo cityInfo = findInfoEntityByInfoId(cityInfoDto.getId());

        if (cityInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new CityInfoNotEditableException();

        cityInfo.clone(cityInfoDto);
        cityInfo.setUpdatedAt(new Date());

        return cityInfo;
    }

    @Override
    @Transactional
    public void deleteInfo(Integer cityInfoId) throws CityInfoNotFoundException, CityInfoNotDeletableException {
        CityInfo cityInfo = findInfoEntityByInfoId(cityInfoId);
        if (cityInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new CityInfoNotDeletableException();
        cityInfoDao.delete(cityInfo);
    }


//  ===========================================Theme_Rel===========================================================  //

    @Override
    @Transactional
    public void addThemeTagRel(City city, Tag tag, int term) {

        CityThemeRel cityThemeRel = addThemeRel(city, tag.getTheme(), term);
        CityThemeTagRel cityThemeTagRel = countThemeTagRel(cityThemeRel.getId(), tag.getId(), term);
        cityThemeTagRel.setForeignEntities(cityThemeRel, tag);
        cityThemeRel.getCityThemeTagRelList().add(cityThemeTagRel);
        cityThemeRelDao.save(cityThemeRel);
    }

    private CityThemeRel addThemeRel(City city, Theme theme, int term) {

        CityThemeRel cityThemeRel = countThemeRel(city.getId(), theme.getId(), term);
        cityThemeRel.setForeignEntities(city, theme);
        return cityThemeRel;
    }

    @Override
    @Transactional
    public void subtractThemeTagRel(City city, Tag tag, int term) {
        term = -1 * term;
        CityThemeRel cityThemeRel = countThemeRel(city.getId(), tag.getThemeId(), term);
        if (cityThemeRel.getId() == null) return;
        countThemeTagRel(cityThemeRel.getId(), tag.getId(), term);
        cityThemeRelDao.save(cityThemeRel);
    }

    public CityThemeRel countThemeRel(Integer cityId, Integer themeId, int term) {
        CityThemeRel cityThemeRel = cityThemeRelDao.findOne(CitySpec.findThemeRelByIdAndThemeId(cityId, themeId))
                                                   .orElseGet(() -> new CityThemeRel());
        cityThemeRel.addNumOfAllTag(term);
        return cityThemeRel;
    }

    public CityThemeTagRel countThemeTagRel(Integer themeRelId, Integer tagId, int term) {
        CityThemeTagRel cityThemeTagRel
                = cityThemeTagRelDao.findOne(CitySpec.findThemeTagRelByThemeRelIdAndTagId(themeRelId, tagId))
                                    .orElseGet(() -> new CityThemeTagRel());
        cityThemeTagRel.addNumOfTag(term);
        return cityThemeTagRel;
    }

    @Override
    public void updateThemeOfThemeTagRel(Theme newTheme, Tag tag) {
        List<CityThemeTagRel> cityThemeTagRelList
                = cityThemeTagRelDao.findAll(CitySpec.findThemeTagRelByTagId(tag.getId(), true, true));

        for (CityThemeTagRel cityThemeTagRel : cityThemeTagRelList) {
            CityThemeRel oldThemeRel = cityThemeTagRel.getCityThemeRel();
            int minusTerm = cityThemeTagRel.getNumOfTag() * -1;
            oldThemeRel.addNumOfAllTag(minusTerm);
            cityThemeRelDao.save(oldThemeRel);


            City city = cityThemeTagRel.getCityThemeRel().getCity();
            CityThemeRel newCityThemeRel = addThemeRel(city, newTheme, cityThemeTagRel.getNumOfTag());
            cityThemeTagRel.setCityThemeRel(newCityThemeRel);
            if (newCityThemeRel.getId() == null) city.getCityThemeRelList().add(newCityThemeRel);
            cityThemeRelDao.save(newCityThemeRel);

        }
    }


}

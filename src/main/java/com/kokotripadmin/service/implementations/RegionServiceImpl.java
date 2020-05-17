package com.kokotripadmin.service.implementations;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.region.RegionDao;
import com.kokotripadmin.dao.interfaces.region.RegionInfoDao;
import com.kokotripadmin.dao.interfaces.region.RegionThemeRelDao;
import com.kokotripadmin.dao.interfaces.region.RegionThemeTagRelDao;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotDao;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotInfoDao;
import com.kokotripadmin.datatablesdao.RegionDataTablesDao;
import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.region.RegionDto;
import com.kokotripadmin.dto.region.RegionInfoDto;
import com.kokotripadmin.entity.city.CityThemeRel;
import com.kokotripadmin.entity.city.CityThemeTagRel;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.city.CityInfo;
import com.kokotripadmin.entity.region.Region;
import com.kokotripadmin.entity.region.RegionInfo;
import com.kokotripadmin.entity.region.RegionThemeRel;
import com.kokotripadmin.entity.region.RegionThemeTagRel;
import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tag.Theme;
import com.kokotripadmin.entity.tourspot.TourSpot;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import com.kokotripadmin.exception.city.CityInfoNotDeletableException;
import com.kokotripadmin.exception.city.CityInfoNotFoundException;
import com.kokotripadmin.exception.city.CityNotFoundException;
import com.kokotripadmin.exception.region.*;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.service.entityinterfaces.CityEntityService;
import com.kokotripadmin.service.entityinterfaces.RegionEntityService;
import com.kokotripadmin.service.entityinterfaces.SupportLanguageEntityService;
import com.kokotripadmin.service.interfaces.RegionService;
import com.kokotripadmin.spec.CitySpec;
import com.kokotripadmin.spec.RegionSpec;
import com.kokotripadmin.spec.tourspot.TourSpotSpec;
import com.kokotripadmin.util.Convert;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.kokotripadmin.entity.region.RegionInfo_.cityInfo;


@Service
public class RegionServiceImpl implements RegionService, RegionEntityService {


    private final ModelMapper modelMapper;
    private final Convert convert;

    private final RegionDataTablesDao regionDataTablesDao;
    private final RegionDao regionDao;
    private final RegionInfoDao regionInfoDao;
    private final RegionThemeRelDao regionThemeRelDao;
    private final RegionThemeTagRelDao regionThemeTagRelDao;
    private final TourSpotDao tourSpotDao;
    private final TourSpotInfoDao tourSpotInfoDao;

    private final SupportLanguageEntityService supportLanguageEntityService;
    private final CityEntityService cityEntityService;



    @Autowired
    public RegionServiceImpl(RegionDao regionDao,
                             ModelMapper modelMapper,
                             RegionDataTablesDao regionDataTablesDao,
                             RegionInfoDao regionInfoDao,
                             RegionThemeRelDao regionThemeRelDao,
                             RegionThemeTagRelDao regionThemeTagRelDao,
                             TourSpotDao tourSpotDao,
                             TourSpotInfoDao tourSpotInfoDao,
                             SupportLanguageEntityService supportLanguageEntityService,
                             CityEntityService cityEntityService, Convert convert) {

        this.regionDao = regionDao;
        this.modelMapper = modelMapper;
        this.regionDataTablesDao = regionDataTablesDao;
        this.regionInfoDao = regionInfoDao;
        this.regionThemeRelDao = regionThemeRelDao;
        this.regionThemeTagRelDao = regionThemeTagRelDao;
        this.tourSpotDao = tourSpotDao;
        this.tourSpotInfoDao = tourSpotInfoDao;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.cityEntityService = cityEntityService;
        this.convert = convert;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public RegionDto findById(Integer regionId) throws RegionNotFoundException {

        Region region = findEntityById(regionId);
        return modelMapper.map(region, RegionDto.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public Region findEntityById(Integer regionId) throws RegionNotFoundException {
        return regionDao.findById(regionId).orElseThrow(RegionNotFoundException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public RegionDto findByIdInDetail(Integer regionId) throws RegionNotFoundException {

        Region region = findEntityById(regionId);
        List<RegionThemeRel> regionThemeRelList = regionThemeRelDao.findAll(RegionSpec.findThemeRelById(regionId, true));
        return convert.regionToDtoInDetail(region, regionThemeRelList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<RegionDto> findAllByEnabledAndCityEnabled(boolean regionEnabled, boolean cityEnabled) {

        List<Region> regionList = regionDao.findAll(RegionSpec.findAllByEnabledAndCityEnabled(regionEnabled, cityEnabled));
        return modelMapper.map(regionList, new TypeToken<List<RegionDto>>() {}.getType());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public Region findEntityById(Integer regionId, Region other) {

        if (regionId == null) return other;
        return regionDao.findById(regionId).orElse(other);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public DataTablesOutput<RegionDto> findAllByPagination(DataTablesInput dataTablesInput) {
        return regionDataTablesDao.findAll(dataTablesInput, convert.regionToDto());
    }


    @Override
    @Transactional
    public Integer save(RegionDto regionDto)
    throws CityNotFoundException, RegionNameDuplicateException, CityInfoNotFoundException, RegionNotFoundException,
            SupportLanguageNotFoundException, RegionInfoAlreadyExistsException {

        City city = cityEntityService.findEntityById(regionDto.getCityId());
        Region region;

        if (regionDto.getId() == null) region = create(city, regionDto);
        else region = update(city, regionDto);
        regionDao.save(region);

        return region.getId();
    }


    private boolean existsByName(String name) {
        long count = regionDao.count(RegionSpec.findByName(name));
        return count > 0;
    }

    private Region create(City city, RegionDto regionDto)
    throws RegionNameDuplicateException, RegionNotFoundException, RegionInfoAlreadyExistsException,
            CityInfoNotFoundException, SupportLanguageNotFoundException {

        String regionName = regionDto.getName();
        if (existsByName(regionName))
            throw new RegionNameDuplicateException(regionName);

        Region region = modelMapper.map(regionDto, Region.class);
        region.setCity(city);

        RegionInfoDto regionInfoDto = modelMapper.map(regionDto, RegionInfoDto.class);
        regionInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());

        RegionInfo regionInfo = createInfo(region, regionInfoDto);
        region.getRegionInfoList().add(regionInfo);

        return region;
    }


    private Region update(City city, RegionDto regionDto)
    throws RegionNameDuplicateException, CityInfoNotFoundException, RegionNotFoundException {

        Region region = findEntityById(regionDto.getId());

        String regionNameToUpdate = regionDto.getName();
        if (!region.getName().equals(regionNameToUpdate) && existsByName(regionNameToUpdate))
            throw new RegionNameDuplicateException(regionNameToUpdate);

        if (region.getCity() != city) {
            for (RegionThemeTagRel regionThemeTagRel : region.getRegionThemeTagRelList()) {
                cityEntityService.subtractThemeTagRel(region.getCity(), regionThemeTagRel.getTag(), regionThemeTagRel.getNumOfTag());
                cityEntityService.addThemeTagRel(city, regionThemeTagRel.getTag(), regionThemeTagRel.getNumOfTag());
            }
            updateCityOfTourSpotById(region.getId(), city);
        }

        region.clone(regionDto);
        region.setCity(city);
        updateInfos(region);
        region.setUpdatedAt(new Date());

        return region;
    }

    @Override
    @Transactional
    public void delete(Integer regionId) throws RegionNotFoundException {
        Region region = findEntityById(regionId);
        regionDao.delete(region);
    }

    private void updateCityOfTourSpotById(Integer regionId, City city) {

        List<TourSpot> tourSpotList = tourSpotDao.findAll(TourSpotSpec.findByRegionId(regionId, true));
        for (TourSpot tourSpot : tourSpotList) {
            tourSpot.setCity(city);
            for (TourSpotInfo tourSpotInfo : tourSpot.getTourSpotInfoList())
                tourSpotInfo.setCity(city);
        }
    }

//  =================================== REGION INFO ====================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public RegionInfo findInfoEntityByInfoId(Integer infoId) throws RegionInfoNotFoundException {
        RegionInfo regionInfo = regionInfoDao.findById(infoId).orElseThrow(() -> new RegionInfoNotFoundException());
        return regionInfo;
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public RegionInfoDto saveInfo(RegionInfoDto regionInfoDto)
    throws RegionNotFoundException, RegionInfoAlreadyExistsException, CityInfoNotFoundException,
           SupportLanguageNotFoundException, RegionInfoNotFoundException, RegionNotEditableException {

        RegionInfo regionInfo;
        if (regionInfoDto.getId() == null) regionInfo = createInfo(null, regionInfoDto);
        else regionInfo = updateInfo(regionInfoDto);
        regionInfoDao.save(regionInfo);
        return modelMapper.map(regionInfo, RegionInfoDto.class);
    }


    private boolean infoExistsByIdAndSupportLanguageId(Integer id, Integer supportLanguageId) {
        long count = regionInfoDao.count(RegionSpec.findInfoByIdAndSupportLanguageId(id, supportLanguageId));
        return count > 0;
    }

    private RegionInfo createInfo(Region region, RegionInfoDto regionInfoDto)
    throws RegionNotFoundException, SupportLanguageNotFoundException, RegionInfoAlreadyExistsException,
            CityInfoNotFoundException {

        if (region == null) region = findEntityById(regionInfoDto.getRegionId());

        Integer supportLanguageId = regionInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);


        if (infoExistsByIdAndSupportLanguageId(region.getId(), supportLanguageId))
            throw new RegionInfoAlreadyExistsException(region.getName(), supportLanguage.getName());

        CityInfo cityInfo = cityEntityService.findInfoEntityByIdAndSupportLanguageId(region.getCityId(), supportLanguageId);

        RegionInfo regionInfo = modelMapper.map(regionInfoDto, RegionInfo.class);
        regionInfo.setForeignEntities(cityInfo, region, supportLanguage);
        regionInfo.denormalize(region);

        return regionInfo;
    }

    private RegionInfo updateInfo(RegionInfoDto regionInfoDto)
    throws RegionInfoNotFoundException, RegionNotEditableException {

        RegionInfo regionInfo = findInfoEntityByInfoId(regionInfoDto.getId());

        if (regionInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new RegionNotEditableException();

        regionInfo.clone(regionInfoDto);
        regionInfo.setUpdatedAt(new Date());

        return regionInfo;
    }

    private void updateInfos(Region region) throws CityInfoNotFoundException {

        Integer cityId = region.getCityId();
        for (RegionInfo regionInfo : region.getRegionInfoList()) {
            Integer supportLanguageId = regionInfo.getSupportLanguageId();
            CityInfo cityInfo = cityEntityService.findInfoEntityByIdAndSupportLanguageId(cityId, supportLanguageId);
            regionInfo.setForeignEntities(cityInfo, region);

            if (supportLanguageId == SupportLanguageEnum.Korean.getId())
                regionInfo.clone(region);
        }
    }

    @Override
    @Transactional
    public void deleteInfo(Integer regionInfoId) throws RegionInfoNotEditableException, RegionInfoNotFoundException {
        RegionInfo regionInfo = findInfoEntityByInfoId(regionInfoId);
        if (regionInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new RegionInfoNotEditableException();
        regionInfoDao.delete(regionInfo);
    }


//  ================================= THEME REL ================================================  //

    @Override
    @Transactional
    public void addThemeTagRel(Region region, Tag tag, int term) {

        RegionThemeRel regionThemeRel = addThemeRel(region, tag.getTheme(), term);
        RegionThemeTagRel regionThemeTagRel = countThemeTagRel(regionThemeRel.getId(), tag.getId(), 1);
        regionThemeTagRel.setForeignEntities(regionThemeRel, tag, region);
        regionThemeRel.getRegionThemeTagRelList().add(regionThemeTagRel);
        regionThemeRelDao.save(regionThemeRel);
    }


    private RegionThemeRel addThemeRel(Region region, Theme theme, int term) {

        RegionThemeRel regionThemeRel = countThemeRel(region.getId(), theme.getId(), term);
        regionThemeRel.setForeignEntities(region, theme);
        return regionThemeRel;
    }

    private RegionThemeRel countThemeRel(Integer regionId, Integer themeId, int term) {
        RegionThemeRel regionThemeRel
                = regionThemeRelDao.findOne(RegionSpec.findThemeRelByIdAndThemeId(regionId, themeId))
                                   .orElseGet(() -> new RegionThemeRel());

        regionThemeRel.addNumOfAllTag(term);
        return regionThemeRel;
    }

    @Override
    @Transactional
    public void subtractThemeTagRel(Region region, Tag tag, int term) {
        term = -1 * term;
        RegionThemeRel regionThemeRel = countThemeRel(region.getId(), tag.getThemeId(), term);
        if (regionThemeRel.getId() == null) return;
        countThemeTagRel(regionThemeRel.getId(), tag.getId(), term);
        regionThemeRelDao.save(regionThemeRel);
    }


    private RegionThemeTagRel countThemeTagRel(Integer themeRelId, Integer tagId, int term) {
        RegionThemeTagRel regionThemeTagRel
                = regionThemeTagRelDao.findOne(RegionSpec.findThemeTagRelByThemeRelIdAndTagId(themeRelId, tagId))
                                      .orElseGet(() -> new RegionThemeTagRel());
        regionThemeTagRel.addNumOfTag(term);
        return regionThemeTagRel;
    }

    @Override
    public void updateThemeOfThemeTagRel(Theme newTheme, Tag tag) {
        List<RegionThemeTagRel> regionThemeTagRelList
                = regionThemeTagRelDao.findAll(RegionSpec.findThemeTagRelByTagId(tag.getId(), true, true));

        for (RegionThemeTagRel regionThemeTagRel : regionThemeTagRelList) {
            RegionThemeRel oldThemeRel = regionThemeTagRel.getRegionThemeRel();
            int minusTerm = regionThemeTagRel.getNumOfTag() * -1;
            oldThemeRel.addNumOfAllTag(minusTerm);
            regionThemeRelDao.save(oldThemeRel);


            Region region = regionThemeTagRel.getRegionThemeRel().getRegion();
            RegionThemeRel newRegionThemeRel = addThemeRel(region, newTheme, regionThemeTagRel.getNumOfTag());
            regionThemeTagRel.setRegionThemeRel(newRegionThemeRel);
            if (newRegionThemeRel.getId() == null) region.getRegionThemeRelList().add(newRegionThemeRel);
            regionThemeRelDao.save(newRegionThemeRel);

        }
    }


}

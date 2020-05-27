package com.kokotripadmin.service.implementations.tourspot;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotDao;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotImageDao;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotInfoDao;
import com.kokotripadmin.datatablesdao.TourSpotDataTablesDao;
import com.kokotripadmin.dto.common.LocatableAutoCompleteDto;
import com.kokotripadmin.dto.tourspot.TourSpotDto;
import com.kokotripadmin.dto.common.TradingHourDto;
import com.kokotripadmin.dto.tourspot.TourSpotImageDto;
import com.kokotripadmin.dto.tourspot.TourSpotInfoDto;
import com.kokotripadmin.entity.activity.Activity;
import com.kokotripadmin.entity.common.DayOfWeek;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.common.TradingHourType;
import com.kokotripadmin.entity.tag.TagInfo;
import com.kokotripadmin.entity.tourspot.TourSpot;
import com.kokotripadmin.entity.tourspot.TourSpotImage;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import com.kokotripadmin.entity.region.Region;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tourspot.TourSpotTradingHour;
import com.kokotripadmin.exception.city.CityNotFoundException;
import com.kokotripadmin.exception.day_of_week.DayOfWeekNotFoundException;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.region.RegionMismatchException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.TagInfoNotFoundException;
import com.kokotripadmin.exception.tag.TagNotFoundException;
import com.kokotripadmin.exception.tour_spot.*;
import com.kokotripadmin.exception.trading_hour_type.TradingHourTypeNotFoundException;
import com.kokotripadmin.service.entityinterfaces.*;
import com.kokotripadmin.service.interfaces.BucketService;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotService;
import com.kokotripadmin.spec.tourspot.TourSpotSpec;
import com.kokotripadmin.util.Convert;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TourSpotServiceImpl implements TourSpotService, TourSpotEntityService {

    private final ModelMapper            modelMapper;
    private final TourSpotDao tourSpotDao;
    private final TourSpotDataTablesDao  tourSpotDataTablesDao;
    private final TourSpotInfoDao tourSpotInfoDao;
    private final TourSpotImageDao tourSpotImageDao;

    private final CityEntityService cityEntityService;
    private final RegionEntityService regionEntityService;
    private final TagEntityService tagEntityService;
    private final SupportLanguageEntityService supportLanguageEntityService;
    private final TradingHourTypeEntityService tradingHourTypeEntityService;
    private final DayOfWeekEntityService dayOfWeekEntityService;
    private final BucketService bucketService;

    private final Convert convert;

    private final String TOUR_SPOT_IMAGE_DIRECTORY = "tour-spot/image";

    @Autowired
    public TourSpotServiceImpl(TourSpotDao tourSpotDao,
                               ModelMapper modelMapper,
                               TourSpotDataTablesDao tourSpotDataTablesDao,
                               TourSpotInfoDao tourSpotInfoDao,
                               TourSpotImageDao tourSpotImageDao,
                               CityEntityService cityEntityService,
                               RegionEntityService regionEntityService,
                               TagEntityService tagEntityService,
                               SupportLanguageEntityService supportLanguageEntityService,
                               TradingHourTypeEntityService tradingHourTypeEntityService,
                               DayOfWeekEntityService dayOfWeekEntityService,
                               BucketService bucketService, Convert convert) {

        this.tourSpotDao = tourSpotDao;
        this.modelMapper = modelMapper;
        this.tourSpotDataTablesDao = tourSpotDataTablesDao;
        this.tourSpotInfoDao = tourSpotInfoDao;
        this.tourSpotImageDao = tourSpotImageDao;


        this.cityEntityService = cityEntityService;
        this.regionEntityService = regionEntityService;
        this.tagEntityService = tagEntityService;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.tradingHourTypeEntityService = tradingHourTypeEntityService;
        this.dayOfWeekEntityService = dayOfWeekEntityService;
        this.bucketService = bucketService;
        this.convert = convert;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public String getNameById(Integer tourSpotId) throws TourSpotNotFoundException {
        TourSpot tourSpot = findEntityById(tourSpotId);
        return tourSpot.getName();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotDto findById(Integer tourSpotId) throws TourSpotNotFoundException {

        TourSpot tourSpot = findEntityById(tourSpotId);
        return modelMapper.map(tourSpot, TourSpotDto.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public DataTablesOutput<TourSpotDto> findAllByPagination(DataTablesInput dataTablesInput) {
        return tourSpotDataTablesDao.findAll(dataTablesInput, convert.tourSpotToDto());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpot findEntityById(Integer tourSpotId) throws TourSpotNotFoundException {
        return tourSpotDao.findById(tourSpotId).orElseThrow(TourSpotNotFoundException::new);
    }



    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotDto findByIdInDetail(Integer tourSpotId) throws TourSpotNotFoundException {
        TourSpot tourSpot = findEntityById(tourSpotId);
        List<TourSpotInfo> tourSpotInfoList = tourSpotInfoDao.findAll(TourSpotSpec.findInfoById(tourSpotId, true));
        return convert.tourSpotToDtoInDetail(tourSpot, tourSpotInfoList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<LocatableAutoCompleteDto> findAllAsLocatableAutoComplete(String search) {
        List<TourSpot> tourSpotList = tourSpotDao.findAll(TourSpotSpec.findByNameLike(search));
        return convert.tourSpotToLocatableAutoCompleteDto(tourSpotList);
    }

    @Override
    @Transactional
    public Integer save(TourSpotDto tourSpotDto)
    throws TourSpotNameDuplicateException, CityNotFoundException, SupportLanguageNotFoundException,
            TourSpotNotFoundException, TagNotFoundException, TagInfoNotFoundException,
            TourSpotInfoAlreadyExistsException, RegionMismatchException, DayOfWeekNotFoundException,
            TradingHourTypeNotFoundException, TourSpotInfoNotFoundException {

        City city = cityEntityService.findEntityById(tourSpotDto.getCityId());
        Region region = regionEntityService.findEntityById(tourSpotDto.getRegionId(), null);

        if (region != null && region.getCityId() != city.getId())
            throw new RegionMismatchException(region.getName(), city.getName());

        Tag tag = tagEntityService.findEntityById(tourSpotDto.getTagId());

        TourSpot tourSpot;
        if (tourSpotDto.getId() == null) tourSpot = create(tourSpotDto, city, region, tag);
        else tourSpot = update(tourSpotDto, city, region, tag);

        if (!tourSpotDto.isAlwaysOpen())
            updateTradingHourList(tourSpotDto.getTradingHourDtoList(), tourSpot.getTourSpotTradingHourList(), tourSpot);

        tourSpotDao.save(tourSpot);
        return tourSpot.getId();
    }

    private void updateTradingHourList(List<TradingHourDto> tradingHourDtoList,
                                       List<TourSpotTradingHour> tourSpotTradingHourList,
                                       TourSpot tourSpot)
    throws TradingHourTypeNotFoundException, DayOfWeekNotFoundException {

        tourSpotTradingHourList.clear();
        for (TradingHourDto tradingHourDto : tradingHourDtoList) {

            TourSpotTradingHour tourSpotTradingHour = modelMapper.map(tradingHourDto, TourSpotTradingHour.class);
            TradingHourType tradingHourType = tradingHourTypeEntityService.findEntityById(tradingHourDto.getTradingHourTypeId());
            DayOfWeek dayOfWeek = dayOfWeekEntityService.findEntityById(tradingHourDto.getDayOfWeekId());
            tourSpotTradingHour.setForeignEntities(tourSpot, dayOfWeek, tradingHourType);
            tourSpotTradingHourList.add(tourSpotTradingHour);
        }
    }

    private TourSpot create(TourSpotDto tourSpotDto, City city, Region region, Tag tag)
    throws SupportLanguageNotFoundException, TourSpotNameDuplicateException, TagInfoNotFoundException,
           TourSpotNotFoundException, TourSpotInfoAlreadyExistsException {

        if (existsByName(tourSpotDto.getName()))
            throw new TourSpotNameDuplicateException(tourSpotDto.getName());

        TourSpot tourSpot = modelMapper.map(tourSpotDto, TourSpot.class);
        tourSpot.setForeignEntities(city, region, tag);

        TourSpotInfoDto tourSpotInfoDto = modelMapper.map(tourSpotDto, TourSpotInfoDto.class);
        tourSpotInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());

        TourSpotInfo tourSpotInfo = createInfo(tourSpot, tourSpotInfoDto);
        tourSpot.getTourSpotInfoList().add(tourSpotInfo);
        tourSpotDao.save(tourSpot);

        cityEntityService.addThemeTagRel(city, tag, 1);
        if (region != null) regionEntityService.addThemeTagRel(region, tag, 1);

        return tourSpot;
    }

    private boolean existsByName(String tourSpotName) {
        long count = tourSpotDao.count(TourSpotSpec.findByName(tourSpotName));
        return count > 0;
    }

    private TourSpot update(TourSpotDto tourSpotDto, City city, Region region, Tag tag)
    throws TourSpotNotFoundException, TagInfoNotFoundException, TourSpotNameDuplicateException,
            TourSpotInfoNotFoundException {


        TourSpot tourSpot = findEntityById(tourSpotDto.getId());

        if (!tourSpot.getName().equals(tourSpotDto.getName()) && existsByName(tourSpotDto.getName()))
            throw new TourSpotNameDuplicateException(tourSpotDto.getName());

        tourSpot.clone(tourSpotDto);
        updateThemeTagRel(tourSpot, city, region, tag);
        updateInfos(tourSpot, tag.getId());

        tourSpot.setForeignEntities(city, region, tag);
        tourSpot.setUpdatedAt(new Date());

        return tourSpot;
    }

    @Override
    @Transactional
    public void delete(Integer tourSpotId) throws TourSpotNotFoundException {
        TourSpot tourSpot = findEntityById(tourSpotId);

        City city = tourSpot.getCity();
        Region region = tourSpot.getRegion();

        for (Activity activity : tourSpot.getActivityList()) {
            Tag tag = activity.getTag();
            cityEntityService.subtractThemeTagRel(city, tag, 1);
            if (region != null) regionEntityService.subtractThemeTagRel(region, tag, 1);
        }

        cityEntityService.subtractThemeTagRel(city, tourSpot.getTag(), 1);
        if (region != null) regionEntityService.subtractThemeTagRel(region, tourSpot.getTag(), 1);

        tourSpotDao.delete(tourSpot);
    }

    private void updateThemeTagRel(TourSpot tourSpot, City city,
                                   Region region, Tag tag) {

        Tag oldTag = tourSpot.getTag();

        City oldCity = tourSpot.getCity();
        if (oldCity != city) {
            cityEntityService.subtractThemeTagRel(oldCity, oldTag, 1);
            cityEntityService.addThemeTagRel(city, tag, 1);
        }

        Region oldRegion = tourSpot.getRegion();
        if (oldRegion != region) {
            if (oldRegion != null) regionEntityService.subtractThemeTagRel(oldRegion, oldTag, 1);
            if (region != null) regionEntityService.addThemeTagRel(region, tag, 1);
        }
    }



    //  =================================== IMAGE ====================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotImage findImageByImageId(Integer tourSpotImageId) throws TourSpotImageNotFoundException {
        return tourSpotImageDao.findById(tourSpotImageId).orElseThrow(TourSpotImageNotFoundException::new);
    }


    @Override
    @Transactional
    public Integer saveImage(TourSpotImageDto tourSpotImageDto)
    throws TourSpotNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException {
        TourSpot tourSpot = findEntityById(tourSpotImageDto.getTourSpotId());
        String bucketKey = TOUR_SPOT_IMAGE_DIRECTORY + "/" + tourSpot.getName() + "/" + tourSpotImageDto.getName();

        if (tourSpotImageDao.count(TourSpotSpec.findImageByIdAndImageBucketKey(tourSpot.getId(), bucketKey)) > 0)
            throw new ImageDuplicateException(tourSpotImageDto.getName());

        TourSpotImage tourSpotImage = modelMapper.map(tourSpotImageDto, TourSpotImage.class);
        tourSpotImage.setTourSpot(tourSpot);
        tourSpotImage.setBucketKey(bucketKey);
        tourSpotImageDao.save(tourSpotImage);

        bucketService.uploadImage(bucketKey, tourSpotImageDto.getName(), tourSpotImageDto.getMultipartFile());
        return tourSpotImage.getId();
    }

    @Override
    @Transactional
    public void updateRepImage(Integer imageId) throws TourSpotImageNotFoundException {
        TourSpotImage newRepImage = findImageByImageId(imageId);
        List<TourSpotImage> repImageList = tourSpotImageDao.findAll(TourSpotSpec.findImageByIdAndRepImage(newRepImage.getTourSpotId(),
                                                                                                          true));
        for (TourSpotImage repImage : repImageList) {
            if (newRepImage != repImage) repImage.setRepImage(false);
        }
        newRepImage.setRepImage(true);
    }

    @Override
    @Transactional
    public void updateImageOrder(List<Integer> imageIdList) {
        List<TourSpotImage> tourSpotImageList = tourSpotImageDao.findAll(TourSpotSpec.findImageByIds(imageIdList));
        HashMap<Integer, TourSpotImage> tourSpotImageHashMap = tourSpotImageList.stream()
                                                                          .collect(Collectors.toMap(TourSpotImage::getId,
                                                                                                    tourSpotImage -> tourSpotImage,
                                                                                                    (oKey, nKey) -> oKey,
                                                                                                    HashMap::new));
        int order = 0;
        for (Integer imageId : imageIdList) {
            TourSpotImage tourSpotImage = tourSpotImageHashMap.get(imageId);
            if (tourSpotImage != null) {
                tourSpotImage.setOrder(order);
                order++;
            }
        }
        tourSpotImageDao.saveAll(tourSpotImageList);
    }


    @Override
    @Transactional
    public void deleteImage(Integer tourSpotImageId)
    throws TourSpotImageNotFoundException, RepImageNotDeletableException {
        TourSpotImage tourSpotImage = findImageByImageId(tourSpotImageId);
        if (tourSpotImage.isRepImage())
            throw new RepImageNotDeletableException();
        bucketService.deleteImage(tourSpotImage.getBucketKey());
        tourSpotImageDao.delete(tourSpotImage);
    }




//  =============================================================================================================//
//  ===========================================TOUR_SPOT_INFO====================================================//
//  =============================================================================================================//



    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotInfo findInfoEntityByIdAndSupportLanguageId(Integer tourSpotId, Integer supportLanguageId)
    throws TourSpotInfoNotFoundException {
        return tourSpotInfoDao.findOne(TourSpotSpec.findInfoByIdAndSupportLanguageId(tourSpotId, supportLanguageId))
                              .orElseThrow(TourSpotInfoNotFoundException::new);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotInfo findInfoEntityByInfoId(Integer tourSpotInfoId) throws TourSpotInfoNotFoundException {
        return tourSpotInfoDao.findById(tourSpotInfoId).orElseThrow(TourSpotInfoNotFoundException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<TourSpotInfo> findAllInfoById(Integer tourSpotId) {
        return tourSpotInfoDao.findAll(TourSpotSpec.findInfoById(tourSpotId, false));
    }


    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public TourSpotInfoDto saveInfo(TourSpotInfoDto tourSpotInfoDto)
    throws TourSpotNotFoundException, SupportLanguageNotFoundException, TourSpotInfoAlreadyExistsException,
           TourSpotInfoNotFoundException, TagInfoNotFoundException, TourSpotInfoNotEditableException {

        TourSpotInfo tourSpotInfo;

        if (tourSpotInfoDto.getId() == null) tourSpotInfo = createInfo(null, tourSpotInfoDto);
        else tourSpotInfo = updateInfo(tourSpotInfoDto);
        tourSpotInfoDao.save(tourSpotInfo);
        return modelMapper.map(tourSpotInfo, TourSpotInfoDto.class);
    }



    private TourSpotInfo createInfo(TourSpot tourSpot, TourSpotInfoDto tourSpotInfoDto)
    throws SupportLanguageNotFoundException, TagInfoNotFoundException, TourSpotNotFoundException,
           TourSpotInfoAlreadyExistsException {

        if (tourSpot == null) tourSpot = findEntityById(tourSpotInfoDto.getTourSpotId());

        Integer supportLanguageId = tourSpotInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(tourSpot.getId(), tourSpotInfoDto.getSupportLanguageId()))
            throw new TourSpotInfoAlreadyExistsException(tourSpot.getName(), supportLanguage.getName());

        TagInfo tagInfo = tagEntityService.findInfoEntityByIdAndSupportLanguageId(tourSpot.getTagId(), supportLanguageId);
        TourSpotInfo tourSpotInfo = modelMapper.map(tourSpotInfoDto, TourSpotInfo.class);
        tourSpotInfo.setForeignEntities(tourSpot, supportLanguage, tagInfo);
        tourSpotInfo.denormalize(tourSpot, tagInfo.getName());


//        TODO: add dummy tour_spot_info to activity_info table

        return tourSpotInfo;
    }

    private boolean infoExistsByIdAndSupportLanguageId(Integer tourSpotId, Integer supportLanguageId) {
        long count = tourSpotInfoDao.count(TourSpotSpec.findInfoByIdAndSupportLanguageId(tourSpotId, supportLanguageId));
        return count > 0;
    }

    private TourSpotInfo updateInfo(TourSpotInfoDto tourSpotInfoDto)
    throws TourSpotInfoNotFoundException, TourSpotInfoNotEditableException {

        TourSpotInfo tourSpotInfo = findInfoEntityByInfoId(tourSpotInfoDto.getId());

        if (tourSpotInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new TourSpotInfoNotEditableException();

        tourSpotInfo.clone(tourSpotInfoDto);
        return tourSpotInfo;
    }

    @SuppressWarnings("Duplicates")
    private void updateInfos(TourSpot tourSpot, Integer tagId)
    throws TagInfoNotFoundException, TourSpotInfoNotFoundException {

        List<TourSpotInfo> tourSpotInfoList = tourSpot.getTourSpotInfoList();
        for (TourSpotInfo tourSpotInfo : tourSpotInfoList) tourSpotInfo.denormalize(tourSpot);

        if (!tourSpot.getTagId().equals(tagId)) {
            HashMap<Integer, TagInfo> tagInfoHashMap = tagEntityService.findAllInfoByIdAsHashMap(tagId);
            for (TourSpotInfo tourSpotInfo : tourSpotInfoList) {
                TagInfo tagInfo = tagInfoHashMap.get(tourSpotInfo.getSupportLanguageId());
                if (tagInfo == null) throw new TagInfoNotFoundException();
                tourSpotInfo.setTagInfo(tagInfo);
                tourSpotInfo.setTagName(tagInfo.getName());
            }
        }

        TourSpotInfo tourSpotInfoInKorean = tourSpotInfoList.stream()
                                                            .filter(t -> t.getSupportLanguageId()
                                                                          .equals(SupportLanguageEnum.Korean.getId()))
                                                            .findFirst()
                                                            .orElseThrow(TourSpotInfoNotFoundException::new);
        tourSpotInfoInKorean.clone(tourSpot);
    }

    @Override
    @Transactional
    public void deleteInfo(Integer tourSpotInfoId)
    throws TourSpotInfoNotFoundException, TourSpotInfoNotDeletableException {
        TourSpotInfo tourSpotInfo = findInfoEntityByInfoId(tourSpotInfoId);
        if (tourSpotInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new TourSpotInfoNotDeletableException();
        tourSpotInfoDao.delete(tourSpotInfo);
    }


}

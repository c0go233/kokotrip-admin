package com.kokotripadmin.service.implementations.activity;

import com.kokotripadmin.constant.BucketDirectoryConstant;
import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.activity.ActivityDao;
import com.kokotripadmin.dao.interfaces.activity.ActivityImageDao;
import com.kokotripadmin.dao.interfaces.activity.ActivityInfoDao;
import com.kokotripadmin.datatablesdao.ActivityDataTablesDao;
import com.kokotripadmin.dto.activity.ActivityDto;
import com.kokotripadmin.dto.activity.ActivityImageDto;
import com.kokotripadmin.dto.activity.ActivityInfoDto;
import com.kokotripadmin.dto.common.LocatableAutoCompleteDto;
import com.kokotripadmin.entity.activity.Activity;
import com.kokotripadmin.entity.activity.ActivityImage;
import com.kokotripadmin.entity.activity.ActivityInfo;
import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.region.Region;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tag.TagInfo;
import com.kokotripadmin.entity.tourspot.TourSpot;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import com.kokotripadmin.exception.activity.*;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.TagInfoNotFoundException;
import com.kokotripadmin.exception.tag.TagNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotInfoNotFoundException;
import com.kokotripadmin.exception.tour_spot.TourSpotNotFoundException;
import com.kokotripadmin.service.entityinterfaces.*;
import com.kokotripadmin.service.interfaces.BucketService;
import com.kokotripadmin.service.interfaces.activity.ActivityService;
import com.kokotripadmin.spec.ActivitySpec;
import com.kokotripadmin.util.Convert;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService, ActivityEntityService {


    private final ModelMapper modelMapper;
    private final Convert     convert;

    private final ActivityInfoDao       activityInfoDao;
    private final ActivityDao           activityDao;
    private final ActivityDataTablesDao activityDataTablesDao;
    private final ActivityImageDao activityImageDao;

    private final TourSpotEntityService        tourSpotEntityService;
    private final TagEntityService             tagEntityService;
    private final SupportLanguageEntityService supportLanguageEntityService;
    private final CityEntityService            cityEntityService;
    private final RegionEntityService          regionEntityService;
    private final BucketService bucketService;

    public ActivityServiceImpl(ModelMapper modelMapper,
                               Convert convert,
                               ActivityDao activityDao,
                               ActivityInfoDao activityInfoDao,
                               ActivityDataTablesDao activityDataTablesDao,
                               ActivityImageDao activityImageDao,
                               TourSpotEntityService tourSpotEntityService,
                               TagEntityService tagEntityService,
                               SupportLanguageEntityService supportLanguageEntityService,
                               CityEntityService cityEntityService,
                               RegionEntityService regionEntityService,
                               BucketService bucketService) {
        this.modelMapper = modelMapper;
        this.convert = convert;
        this.activityDao = activityDao;
        this.activityInfoDao = activityInfoDao;
        this.activityDataTablesDao = activityDataTablesDao;
        this.activityImageDao = activityImageDao;
        this.tourSpotEntityService = tourSpotEntityService;
        this.tagEntityService = tagEntityService;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.cityEntityService = cityEntityService;
        this.regionEntityService = regionEntityService;
        this.bucketService = bucketService;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public DataTablesOutput<ActivityDto> findAllByPagination(DataTablesInput dataTablesInput) {
        return activityDataTablesDao.findAll(dataTablesInput, convert.activityToDto());
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityDto findById(Integer activityId) throws ActivityNotFoundException {
        Activity activity = findEntityById(activityId);
        return modelMapper.map(activity, ActivityDto.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public Activity findEntityById(Integer activityId) throws ActivityNotFoundException {
        return activityDao.findById(activityId).orElseThrow(ActivityNotFoundException::new);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityDto findByIdInDetail(Integer activityId) throws ActivityNotFoundException {
        Activity activity = findEntityById(activityId);
        return convert.activityToDtoInDetail(activity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<LocatableAutoCompleteDto> findAllAsLocatableAutoComplete(String search) {
        List<Activity> activityList = activityDao.findAll(ActivitySpec.findByNameLike(search));
        return convert.activityToLocatableAutoCompleteDto(activityList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public String getNameById(Integer activityId) throws ActivityNotFoundException {
        Activity activity = findEntityById(activityId);
        return activity.getName();
    }

    @Override
    @Transactional
    public Integer save(ActivityDto activityDto)
    throws TagNotFoundException, TourSpotInfoNotFoundException, TourSpotNotFoundException,
           ActivityNotFoundException, ActivityInfoNotFoundException, SupportLanguageNotFoundException,
           TagInfoNotFoundException, ActivityInfoAlreadyExistsException, ActivityNameDuplicateException {

        Tag tag = tagEntityService.findEntityById(activityDto.getTagId());
        Activity activity;
        if (activityDto.getId() == null) activity = create(activityDto, tag);
        else activity = update(activityDto, tag);
        activityDao.save(activity);
        return activity.getId();
    }


    private boolean existsByTourSpotIdAndName(Integer tourSpotId, String activityName) {
        long count = activityDao.count(ActivitySpec.findByTourSpotIdAndName(tourSpotId, activityName));
        return count > 0;
    }


    private Activity create(ActivityDto activityDto, Tag tag)
    throws TourSpotNotFoundException, TourSpotInfoNotFoundException, ActivityInfoAlreadyExistsException,
           SupportLanguageNotFoundException, TagInfoNotFoundException, ActivityNotFoundException,
           ActivityNameDuplicateException {


        if (existsByTourSpotIdAndName(activityDto.getTourSpotId(), activityDto.getName()))
            throw new ActivityNameDuplicateException(activityDto.getName());

        TourSpot tourSpot = tourSpotEntityService.findEntityById(activityDto.getTourSpotId());
        Activity activity = modelMapper.map(activityDto, Activity.class);
        activity.setForeignEntities(tourSpot, tag);

        ActivityInfoDto activityInfoDto = modelMapper.map(activityDto, ActivityInfoDto.class);
        activityInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());

        ActivityInfo activityInfo = createInfo(activity, activityInfoDto);
        activity.getActivityInfoList().add(activityInfo);
        activityDao.save(activity);

        cityEntityService.addThemeTagRel(tourSpot.getCity(), tag, 1);
        if (tourSpot.getRegion() != null) regionEntityService.addThemeTagRel(tourSpot.getRegion(), tag, 1);

        return activity;
    }


    private Activity update(ActivityDto activityDto, Tag tag)
    throws ActivityNotFoundException, ActivityInfoNotFoundException, ActivityNameDuplicateException,
           TagInfoNotFoundException {


        Activity activity = findEntityById(activityDto.getId());

        if (!activity.getName().equals(activityDto.getName()) &&
            existsByTourSpotIdAndName(activityDto.getTourSpotId(), activityDto.getName()))
            throw new ActivityNameDuplicateException(activityDto.getName());


        boolean tagChanged = activity.getTag() != tag;

        if (tagChanged) {
            City city = activity.getTourSpot().getCity();
            cityEntityService.subtractThemeTagRel(city, activity.getTag(), 1);
            cityEntityService.addThemeTagRel(city, tag, 1);

            Region region = activity.getTourSpot().getRegion();
            if (region != null) {
                regionEntityService.subtractThemeTagRel(region, activity.getTag(), 1);
                regionEntityService.addThemeTagRel(region, tag, 1);
            }
        }

        activity.clone(activityDto);
        updateInfos(activity, tag.getId());
        if (tagChanged) activity.setTag(tag);
        activity.setUpdatedAt(new Date());

        return activity;
    }


    @SuppressWarnings("Duplicates")
    private void updateInfos(Activity activity, Integer tagId)
    throws TagInfoNotFoundException, ActivityInfoNotFoundException {
        List<ActivityInfo> activityInfoList = activity.getActivityInfoList();
        for (ActivityInfo activityInfo : activityInfoList)
            activityInfo.denormalize(activity);

        if (activity.getTagId() != tagId) {
            HashMap<Integer, TagInfo> tagInfoHashMap = tagEntityService.findAllInfoByIdAsHashMap(tagId);
            for (ActivityInfo activityInfo : activityInfoList) {
                TagInfo tagInfo = tagInfoHashMap.get(activityInfo.getSupportLanguageId());
                if (tagInfo == null) throw new TagInfoNotFoundException();
                activityInfo.setTagInfo(tagInfo);
                activityInfo.setTagName(tagInfo.getName());
            }
        }

        ActivityInfo activityInfoInKorean = activityInfoList.stream()
                                                            .filter(t -> t.getSupportLanguageId()
                                                                          .equals(SupportLanguageEnum.Korean.getId()))
                                                            .findFirst()
                                                            .orElseThrow(ActivityInfoNotFoundException::new);
        activityInfoInKorean.clone(activity);
    }


    @Override
    @Transactional
    public Integer delete(Integer activityId) throws ActivityNotFoundException {
        Activity activity = findEntityById(activityId);
        cityEntityService.subtractThemeTagRel(activity.getTourSpot().getCity(), activity.getTag(), 1);
        if (activity.getTourSpot().getRegion() != null)
            regionEntityService.subtractThemeTagRel(activity.getTourSpot().getRegion(), activity.getTag(), 1);
        Integer tourSpotId = activity.getTourSpotId();
        activityDao.delete(activity);
        return tourSpotId;
    }


//  =================================== IMAGE ====================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityImage findImageByImageId(Integer activityImageId) throws ActivityImageNotFoundException {
        return activityImageDao.findById(activityImageId).orElseThrow(ActivityImageNotFoundException::new);
    }


    @Override
    @Transactional
    public Integer saveImage(ActivityImageDto activityImageDto)
    throws ActivityNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException {
        Activity activity = findEntityById(activityImageDto.getActivityId());

        String bucketKey = BucketDirectoryConstant.TOUR_SPOT_IMAGE + "/" +
                           activity.getTourSpot().getName() + "/activity/" +
                           activity.getName() + "/" +
                           activityImageDto.getName();

        if (activityImageDao.count(ActivitySpec.findImageByIdAndImageBucketKey(activity.getId(), bucketKey)) > 0)
            throw new ImageDuplicateException(activityImageDto.getName());

        ActivityImage activityImage = modelMapper.map(activityImageDto, ActivityImage.class);
        activityImage.setActivity(activity);
        activityImage.setBucketKey(bucketKey);
        activityImageDao.save(activityImage);

        bucketService.uploadImage(bucketKey, activityImageDto.getName(), activityImageDto.getMultipartFile());
        return activityImage.getId();
    }

    @Override
    @Transactional
    public void updateRepImage(Integer imageId) throws ActivityImageNotFoundException {
        ActivityImage newRepImage = findImageByImageId(imageId);
        List<ActivityImage> repImageList = activityImageDao.findAll(ActivitySpec.findImageByIdAndRepImage(newRepImage.getActivityId(),
                                                                                                          true));
        for (ActivityImage repImage : repImageList) {
            if (newRepImage != repImage) repImage.setRepImage(false);
        }
        newRepImage.setRepImage(true);
    }

    @Override
    @Transactional
    public void updateImageOrder(List<Integer> imageIdList) {
        List<ActivityImage> activityImageList = activityImageDao.findAll(ActivitySpec.findImageByIds(imageIdList));
        HashMap<Integer, ActivityImage> activityImageHashMap = activityImageList.stream()
                                                                                .collect(Collectors.toMap(ActivityImage::getId,
                                                                                                          activityImage -> activityImage,
                                                                                                          (oKey, nKey) -> oKey,
                                                                                                          HashMap::new));
        int order = 0;
        for (Integer imageId : imageIdList) {
            ActivityImage activityImage = activityImageHashMap.get(imageId);
            if (activityImage != null) {
                activityImage.setOrder(order);
                order++;
            }
        }
        activityImageDao.saveAll(activityImageList);
    }


    @Override
    @Transactional
    public void deleteImage(Integer activityImageId)
    throws ActivityImageNotFoundException, RepImageNotDeletableException {
        ActivityImage activityImage = findImageByImageId(activityImageId);
        if (activityImage.isRepImage())
            throw new RepImageNotDeletableException();
        bucketService.deleteImage(activityImage.getBucketKey());
        activityImageDao.delete(activityImage);
    }





//  ============================================= INFO =========================================  //


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityInfo findInfoByInfoId(Integer activityInfoId) throws ActivityInfoNotFoundException {
        return activityInfoDao.findById(activityInfoId).orElseThrow(ActivityInfoNotFoundException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityInfo findInfoEntityByIdAndSupportLanguageId(Integer activityId, Integer supportLanguageId)
    throws ActivityInfoNotFoundException {
        return activityInfoDao.findOne(ActivitySpec.findInfoByIdAndSupportLanguageId(activityId, supportLanguageId))
                              .orElseThrow(ActivityInfoNotFoundException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<ActivityInfo> findAllInfoById(Integer activityId) {
        return activityInfoDao.findAll(ActivitySpec.findInfoById(activityId));
    }


    @Override
    @Transactional
    public ActivityInfoDto saveInfo(ActivityInfoDto activityInfoDto)
    throws ActivityInfoAlreadyExistsException, SupportLanguageNotFoundException, TourSpotInfoNotFoundException,
           TagInfoNotFoundException, ActivityNotFoundException, ActivityInfoNotFoundException,
           ActivityInfoNotEditableException {

        ActivityInfo activityInfo;
        if (activityInfoDto.getId() == null) activityInfo = createInfo(null, activityInfoDto);
        else activityInfo = updateInfo(activityInfoDto);
        activityInfoDao.save(activityInfo);
        return modelMapper.map(activityInfo, ActivityInfoDto.class);
    }

    private ActivityInfo createInfo(Activity activity, ActivityInfoDto activityInfoDto)
    throws SupportLanguageNotFoundException, TagInfoNotFoundException, ActivityNotFoundException,
           ActivityInfoAlreadyExistsException, TourSpotInfoNotFoundException {

        if (activity == null) activity = findEntityById(activityInfoDto.getActivityId());

        Integer supportLanguageId = activityInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(activity.getId(), activityInfoDto.getSupportLanguageId()))
            throw new ActivityInfoAlreadyExistsException(activity.getName(), supportLanguage.getName());

        TagInfo tagInfo =
                tagEntityService.findInfoEntityByIdAndSupportLanguageId(activity.getTagId(), supportLanguageId);
        TourSpotInfo tourSpotInfo =
                tourSpotEntityService.findInfoEntityByIdAndSupportLanguageId(activity.getTourSpotId(),
                                                                             supportLanguageId);

        ActivityInfo activityInfo = modelMapper.map(activityInfoDto, ActivityInfo.class);
        activityInfo.setForeignEntities(tourSpotInfo, activity, tagInfo, supportLanguage);
        activityInfo.denormalize(activity, tagInfo.getName());

        return activityInfo;
    }

    private ActivityInfo updateInfo(ActivityInfoDto activityInfoDto)
    throws ActivityInfoNotFoundException, ActivityInfoNotEditableException {

        ActivityInfo activityInfo = findInfoByInfoId(activityInfoDto.getId());

        if (activityInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new ActivityInfoNotEditableException();

        activityInfo.clone(activityInfoDto);
        return activityInfo;
    }

    private boolean infoExistsByIdAndSupportLanguageId(Integer activityId, Integer supportLanguageId) {
        long count =
                activityInfoDao.count(ActivitySpec.findInfoByIdAndSupportLanguageId(activityId, supportLanguageId));
        return count > 0;
    }

    @Override
    @Transactional
    public void deleteInfo(Integer activityInfoId)
    throws ActivityInfoNotFoundException, ActivityInfoNotDeletableException {
        ActivityInfo activityInfo = findInfoByInfoId(activityInfoId);

        if (activityInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new ActivityInfoNotDeletableException();

        activityInfoDao.delete(activityInfo);
    }


}

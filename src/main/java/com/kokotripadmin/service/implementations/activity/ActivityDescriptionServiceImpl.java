package com.kokotripadmin.service.implementations.activity;

import com.kokotripadmin.constant.BucketDirectoryConstant;
import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.activity.ActivityDescriptionDao;
import com.kokotripadmin.dao.interfaces.activity.ActivityDescriptionImageDao;
import com.kokotripadmin.dao.interfaces.activity.ActivityDescriptionInfoDao;
import com.kokotripadmin.dto.activity.ActivityDescriptionDto;
import com.kokotripadmin.dto.activity.ActivityDescriptionImageDto;
import com.kokotripadmin.dto.activity.ActivityDescriptionInfoDto;
import com.kokotripadmin.entity.activity.*;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.exception.activity.*;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.service.entityinterfaces.ActivityEntityService;
import com.kokotripadmin.service.entityinterfaces.SupportLanguageEntityService;
import com.kokotripadmin.service.interfaces.BucketService;
import com.kokotripadmin.service.interfaces.activity.ActivityDescriptionService;
import com.kokotripadmin.spec.ActivityDescriptionSpec;
import com.kokotripadmin.util.Convert;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityDescriptionServiceImpl implements ActivityDescriptionService {


    private final ModelMapper                modelMapper;
    private final Convert convert;
    private final ActivityDescriptionDao     activityDescriptionDao;
    private final ActivityDescriptionInfoDao activityDescriptionInfoDao;
    private final ActivityDescriptionImageDao activityDescriptionImageDao;

    private final ActivityEntityService        activityEntityService;
    private final SupportLanguageEntityService supportLanguageEntityService;
    private final BucketService bucketService;

    private final String ACTIVITY_DESCRIPTION_IMAGE_DIRECTORY = "activity/description/image";

    public ActivityDescriptionServiceImpl(ModelMapper modelMapper,
                                          Convert convert,
                                          ActivityDescriptionDao activityDescriptionDao,
                                          ActivityDescriptionInfoDao activityDescriptionInfoDao,
                                          ActivityDescriptionImageDao activityDescriptionImageDao,
                                          ActivityEntityService activityEntityService,
                                          SupportLanguageEntityService supportLanguageEntityService,
                                          BucketService bucketService) {
        this.modelMapper = modelMapper;
        this.convert = convert;
        this.activityDescriptionDao = activityDescriptionDao;
        this.activityDescriptionInfoDao = activityDescriptionInfoDao;
        this.activityDescriptionImageDao = activityDescriptionImageDao;
        this.activityEntityService = activityEntityService;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.bucketService = bucketService;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityDescriptionDto findById(Integer activityDescriptionId)
    throws ActivityDescriptionNotFoundException {
        ActivityDescription activityDescription = findEntityById(activityDescriptionId);
        return modelMapper.map(activityDescription, ActivityDescriptionDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityDescription findEntityById(Integer activityDescriptionId)
    throws ActivityDescriptionNotFoundException {
        return activityDescriptionDao.findById(activityDescriptionId)
                                     .orElseThrow(ActivityDescriptionNotFoundException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityDescriptionDto findByIdInDetail(Integer activityDescriptionId)
    throws ActivityDescriptionNotFoundException {
        ActivityDescription activityDescription = findEntityById(activityDescriptionId);
        return convert.activityDescriptionToDtoInDetail(activityDescription);
    }

    @Override
    @Transactional
    public Integer save(ActivityDescriptionDto activityDescriptionDto)
    throws ActivityNotFoundException, ActivityInfoNotFoundException, ActivityDescriptionNotFoundException,
           ActivityDescriptionAlreadyExistsException, SupportLanguageNotFoundException,
           ActivityDescriptionInfoAlreadyExistsException, ActivityDescriptionInfoNotFoundException {

        ActivityDescription activityDescription;
        if (activityDescriptionDto.getId() == null) activityDescription = create(activityDescriptionDto);
        else activityDescription = update(activityDescriptionDto);
        activityDescriptionDao.save(activityDescription);
        return activityDescription.getId();
    }

    private boolean existsByActivityIdAndName(Integer activityId, String activityDescriptionName) {
        long count = activityDescriptionDao.count(ActivityDescriptionSpec.findByActivityIdAndName(activityId,
                                                                                                  activityDescriptionName));
        return count > 0;
    }

    private ActivityDescription create(ActivityDescriptionDto activityDescriptionDto)
    throws ActivityNotFoundException, SupportLanguageNotFoundException, ActivityDescriptionInfoAlreadyExistsException,
           ActivityInfoNotFoundException, ActivityDescriptionAlreadyExistsException,
           ActivityDescriptionNotFoundException {

        Activity activity = activityEntityService.findEntityById(activityDescriptionDto.getActivityId());

        if (existsByActivityIdAndName(activity.getId(), activityDescriptionDto.getName()))
            throw new ActivityDescriptionAlreadyExistsException(activityDescriptionDto.getName());

        ActivityDescription activityDescription = modelMapper.map(activityDescriptionDto, ActivityDescription.class);
        activityDescription.setActivity(activity);

        ActivityDescriptionInfoDto activityDescriptionInfoDto = modelMapper.map(activityDescriptionDto,
                                                                                ActivityDescriptionInfoDto.class);

        activityDescriptionInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());
        ActivityDescriptionInfo activityDescriptionInfo = createInfo(activityDescription, activityDescriptionInfoDto);

        activityDescription.getActivityDescriptionInfoList().add(activityDescriptionInfo);

        return activityDescription;
    }


    private ActivityDescription update(ActivityDescriptionDto activityDescriptionDto)
    throws ActivityDescriptionNotFoundException, ActivityDescriptionAlreadyExistsException,
           ActivityDescriptionInfoNotFoundException {

        ActivityDescription activityDescription = findEntityById(activityDescriptionDto.getId());

        if (!activityDescription.getName().equals(activityDescriptionDto.getName())
            && existsByActivityIdAndName(activityDescription.getActivityId(), activityDescriptionDto.getName()))
            throw new ActivityDescriptionAlreadyExistsException(activityDescriptionDto.getName());

        activityDescription.clone(activityDescriptionDto);
        updateDescriptionInfos(activityDescription);

        return activityDescription;
    }


    @Override
    @Transactional
    public Integer delete(Integer activityDescriptionId) throws ActivityDescriptionNotFoundException {
        ActivityDescription activityDescription = findEntityById(activityDescriptionId);
        Integer activityId = activityDescription.getActivityId();
        activityDescriptionDao.delete(activityDescription);
        return activityId;
    }


//  =================================== IMAGE ====================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityDescriptionImage findImageByImageId(Integer activityDescriptionImageId)
    throws ActivityDescriptionImageNotFoundException {
        return activityDescriptionImageDao.findById(activityDescriptionImageId)
                                          .orElseThrow(ActivityDescriptionImageNotFoundException::new);
    }

    @Override
    @Transactional
    public Integer saveImage(ActivityDescriptionImageDto activityDescriptionImageDto)
    throws ActivityDescriptionNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException {
        ActivityDescription activityDescription =
                findEntityById(activityDescriptionImageDto.getActivityDescriptionId());



        String bucketKey = BucketDirectoryConstant.TOUR_SPOT_IMAGE + "/" +
                           activityDescription.getActivity().getTourSpot().getName() + "/activity/" +
                           activityDescription.getActivity().getName() + "/description/" +
                           activityDescriptionImageDto.getName();

        if (activityDescriptionImageDao.count(ActivityDescriptionSpec.findImageByIdAndImageBucketKey(activityDescription.getId(),
                                                                                                     bucketKey)) > 0)
            throw new ImageDuplicateException(activityDescriptionImageDto.getName());

        ActivityDescriptionImage activityDescriptionImage = modelMapper.map(activityDescriptionImageDto,
                                                                            ActivityDescriptionImage.class);
        activityDescriptionImage.setActivityDescription(activityDescription);
        activityDescriptionImage.setBucketKey(bucketKey);
        activityDescriptionImageDao.save(activityDescriptionImage);
        bucketService.uploadImage(bucketKey,
                                  activityDescriptionImageDto.getName(),
                                  activityDescriptionImageDto.getMultipartFile());
        return activityDescriptionImage.getId();
    }

    @Override
    @Transactional
    public void updateImageOrder(List<Integer> imageIdList) {
        List<ActivityDescriptionImage> activityDescriptionImageList =
                activityDescriptionImageDao.findAll(ActivityDescriptionSpec.findImageByIds(imageIdList));
        HashMap<Integer, ActivityDescriptionImage> activityDescriptionImageHashMap
                = activityDescriptionImageList.stream()
                                              .collect(Collectors.toMap(ActivityDescriptionImage::getId,
                                                                        activityDescriptionImage -> activityDescriptionImage,
                                                                        (oKey, nKey) -> oKey,
                                                                        HashMap::new));
        int order = 0;
        for (Integer imageId : imageIdList) {
            ActivityDescriptionImage activityDescriptionImage = activityDescriptionImageHashMap.get(imageId);
            if (activityDescriptionImage != null) {
                activityDescriptionImage.setOrder(order);
                order++;
            }
        }
        activityDescriptionImageDao.saveAll(activityDescriptionImageList);
    }

    @Override
    @Transactional
    public void deleteImage(Integer activityDescriptionImageId)
    throws ActivityDescriptionImageNotFoundException {
        ActivityDescriptionImage activityDescriptionImage = findImageByImageId(activityDescriptionImageId);
        bucketService.deleteImage(activityDescriptionImage.getBucketKey());
        activityDescriptionImageDao.delete(activityDescriptionImage);
    }


//  ========================================= INFO ================================================  //


    @Override
    @Transactional
    public void deleteInfo(Integer activityDescriptionInfoId)
    throws ActivityDescriptionInfoNotFoundException, ActivityDescriptionInfoNotDeletableException {
        ActivityDescriptionInfo activityDescriptionInfo = findInfoEntityByInfoId(activityDescriptionInfoId);
        if (activityDescriptionInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new ActivityDescriptionInfoNotDeletableException();
        activityDescriptionInfoDao.delete(activityDescriptionInfo);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityDescriptionInfo findInfoEntityByInfoId(Integer activityDescriptionInfoId)
    throws ActivityDescriptionInfoNotFoundException {
        return activityDescriptionInfoDao.findById(activityDescriptionInfoId)
                                         .orElseThrow(ActivityDescriptionInfoNotFoundException::new);
    }

    private boolean infoExistsByIdAndSupportLanguageId(Integer activityDescriptionId, Integer supportLanguageId) {
        long count = activityDescriptionInfoDao
                .count(ActivityDescriptionSpec.findInfoByIdAndSupportLanguageId(activityDescriptionId,
                                                                                supportLanguageId));
        return count > 0;
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public ActivityDescriptionInfoDto saveInfo(ActivityDescriptionInfoDto activityDescriptionInfoDto)
    throws SupportLanguageNotFoundException, ActivityInfoNotFoundException,
           ActivityDescriptionInfoAlreadyExistsException, ActivityDescriptionNotFoundException,
           ActivityDescriptionInfoNotEditableException, ActivityDescriptionInfoNotFoundException {

        ActivityDescriptionInfo activityDescriptionInfo;
        if (activityDescriptionInfoDto.getId() == null)
            activityDescriptionInfo = createInfo(null, activityDescriptionInfoDto);
        else activityDescriptionInfo = updateInfo(activityDescriptionInfoDto);

        activityDescriptionInfoDao.save(activityDescriptionInfo);
        return modelMapper.map(activityDescriptionInfo, ActivityDescriptionInfoDto.class);
    }

    private ActivityDescriptionInfo createInfo(ActivityDescription activityDescription,
                                               ActivityDescriptionInfoDto activityDescriptionInfoDto)
    throws SupportLanguageNotFoundException, ActivityDescriptionInfoAlreadyExistsException,
           ActivityInfoNotFoundException, ActivityDescriptionNotFoundException {

        if (activityDescription == null)
            activityDescription = findEntityById(activityDescriptionInfoDto.getActivityDescriptionId());

        Integer supportLanguageId = activityDescriptionInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(activityDescription.getId(), supportLanguageId))
            throw new ActivityDescriptionInfoAlreadyExistsException(activityDescription.getName(),
                                                                    supportLanguage.getName());

        ActivityInfo activityInfo =
                activityEntityService.findInfoEntityByIdAndSupportLanguageId(activityDescription.getActivityId(),
                                                                             supportLanguageId);

        ActivityDescriptionInfo activityDescriptionInfo = modelMapper.map(activityDescriptionInfoDto,
                                                                          ActivityDescriptionInfo.class);

        activityDescriptionInfo.setForeignEntities(activityInfo, supportLanguage, activityDescription);
        return activityDescriptionInfo;
    }

    private ActivityDescriptionInfo updateInfo(ActivityDescriptionInfoDto activityDescriptionInfoDto)
    throws ActivityDescriptionInfoNotFoundException, ActivityDescriptionInfoNotEditableException {

        ActivityDescriptionInfo activityDescriptionInfo = findInfoEntityByInfoId(activityDescriptionInfoDto.getId());

        if (activityDescriptionInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new ActivityDescriptionInfoNotEditableException();

        activityDescriptionInfo.clone(activityDescriptionInfoDto);

        return activityDescriptionInfo;
    }

    private void updateDescriptionInfos(ActivityDescription activityDescription)
    throws ActivityDescriptionInfoNotFoundException {

        List<ActivityDescriptionInfo> activityDescriptionInfoList =
                activityDescription.getActivityDescriptionInfoList();

        for (ActivityDescriptionInfo activityDescriptionInfo : activityDescriptionInfoList)
            activityDescriptionInfo.denormalize(activityDescription);

        ActivityDescriptionInfo activityDescriptionInfoInKorean =
                activityDescriptionInfoList.stream()
                                           .filter(t -> t.getSupportLanguageId()
                                                         .equals(SupportLanguageEnum.Korean.getId()))
                                           .findFirst()
                                           .orElseThrow(ActivityDescriptionInfoNotFoundException::new);
        activityDescriptionInfoInKorean.clone(activityDescription);

    }

}

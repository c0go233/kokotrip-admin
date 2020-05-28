package com.kokotripadmin.service.implementations;

import com.kokotripadmin.constant.BucketDirectoryConstant;
import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.photozone.PhotoZoneDao;
import com.kokotripadmin.dao.interfaces.photozone.PhotoZoneImageDao;
import com.kokotripadmin.dao.interfaces.photozone.PhotoZoneInfoDao;
import com.kokotripadmin.dto.photozone.PhotoZoneDto;
import com.kokotripadmin.dto.photozone.PhotoZoneImageDto;
import com.kokotripadmin.dto.photozone.PhotoZoneInfoDto;
import com.kokotripadmin.entity.activity.Activity;
import com.kokotripadmin.entity.activity.ActivityInfo;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.photozone.PhotoZone;
import com.kokotripadmin.entity.photozone.PhotoZoneImage;
import com.kokotripadmin.entity.photozone.PhotoZoneInfo;
import com.kokotripadmin.entity.tourspot.TourSpot;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import com.kokotripadmin.exception.activity.ActivityInfoNotFoundException;
import com.kokotripadmin.exception.activity.ActivityNotFoundException;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.photozone.*;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.TagInfoNotFoundException;
import com.kokotripadmin.exception.tour_spot.*;
import com.kokotripadmin.service.entityinterfaces.ActivityEntityService;
import com.kokotripadmin.service.entityinterfaces.SupportLanguageEntityService;
import com.kokotripadmin.service.entityinterfaces.TourSpotEntityService;
import com.kokotripadmin.service.interfaces.BucketService;
import com.kokotripadmin.service.interfaces.PhotoZoneService;
import com.kokotripadmin.spec.PhotoZoneSpec;
import com.kokotripadmin.util.Convert;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PhotoZoneServiceImpl implements PhotoZoneService {

    private final ModelMapper modelMapper;
    private final Convert convert;

    private final PhotoZoneDao photoZoneDao;
    private final PhotoZoneInfoDao photoZoneInfoDao;
    private final PhotoZoneImageDao photoZoneImageDao;

    private final TourSpotEntityService tourSpotEntityService;
    private final ActivityEntityService activityEntityService;
    private final SupportLanguageEntityService supportLanguageEntityService;
    private final BucketService bucketService;

    @Autowired
    public PhotoZoneServiceImpl(ModelMapper modelMapper, Convert convert,
                                PhotoZoneDao photoZoneDao,
                                PhotoZoneInfoDao photoZoneInfoDao,
                                PhotoZoneImageDao photoZoneImageDao,
                                TourSpotEntityService tourSpotEntityService,
                                ActivityEntityService activityEntityService,
                                SupportLanguageEntityService supportLanguageEntityService,
                                BucketService bucketService) {
        this.modelMapper = modelMapper;
        this.convert = convert;
        this.photoZoneDao = photoZoneDao;
        this.photoZoneInfoDao = photoZoneInfoDao;
        this.photoZoneImageDao = photoZoneImageDao;
        this.tourSpotEntityService = tourSpotEntityService;
        this.activityEntityService = activityEntityService;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.bucketService = bucketService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public PhotoZoneDto findById(Integer photoZoneId) throws PhotoZoneNotFoundException {
        PhotoZone photoZone = findEntityById(photoZoneId);
        return modelMapper.map(photoZone, PhotoZoneDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public PhotoZone findEntityById(Integer photoZoneId) throws PhotoZoneNotFoundException {
        return photoZoneDao.findById(photoZoneId).orElseThrow(PhotoZoneNotFoundException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public PhotoZoneDto findByIdInDetail(Integer photoZoneId) throws PhotoZoneNotFoundException {
        PhotoZone photoZone = photoZoneDao.findOne(PhotoZoneSpec.findById(photoZoneId, true))
                                          .orElseThrow(PhotoZoneNotFoundException::new);
        return convert.photoZoneToDtoInDetail(photoZone);
    }


    @Override
    @Transactional
    public Integer save(PhotoZoneDto photoZoneDto)
    throws TourSpotNotFoundException, PhotoZoneInfoAlreadyExistsException, TourSpotInfoNotFoundException,
           SupportLanguageNotFoundException, PhotoZoneNotFoundException, PhotoZoneDuplicateException,
           ActivityNotFoundException, ActivityInfoNotFoundException, PhotoZoneInfoNotFoundException,
           PhotoZoneTourSpotDuplicateException {

        TourSpot tourSpot = photoZoneDto.getTourSpotId() == null ? null :
                tourSpotEntityService.findEntityById(photoZoneDto.getTourSpotId());

        Activity activity = photoZoneDto.getActivityId() == null ? null :
                activityEntityService.findEntityById(photoZoneDto.getActivityId());

        PhotoZone photoZone;
        if (photoZoneDto.getId() == null) photoZone = create(photoZoneDto, tourSpot, activity);
        else photoZone = update(photoZoneDto, tourSpot, activity);

        photoZoneDao.save(photoZone);
        return photoZone.getId();
    }

    private boolean existsByName(Integer parentTourSpotId, String photoZoneName) {
        long count = photoZoneDao.count(PhotoZoneSpec.findByParentTourSpotIdAndName(parentTourSpotId, photoZoneName));
        return count > 0;
    }

    private boolean existsByTourSpotId(Integer parentTourSpotId, Integer tourSpotId) {
        long count = photoZoneDao.count(PhotoZoneSpec.findByParentTourSpotIdAndTourSpotId(parentTourSpotId, tourSpotId));
        return count > 0;
    }

    private boolean existsByActivityId(Integer parentTourSpotId, Integer activityId) {
        long count = photoZoneDao.count(PhotoZoneSpec.findByParentTourSpotIdAndActivityId(parentTourSpotId, activityId));
        return count > 0;
    }

    private boolean exists(Integer parentTourSpotId, Integer tourSpotId, Integer activityId, String photoZoneName) {
        if (existsByName(parentTourSpotId, photoZoneName)) return true;
        else if (existsByTourSpotId(parentTourSpotId, tourSpotId)) return true;
        else if (existsByActivityId(parentTourSpotId, activityId)) return true;
        else return false;
    }

    private PhotoZone create(PhotoZoneDto photoZoneDto, TourSpot tourSpot, Activity activity)
    throws PhotoZoneDuplicateException, TourSpotNotFoundException, PhotoZoneInfoAlreadyExistsException,
            SupportLanguageNotFoundException, TourSpotInfoNotFoundException, PhotoZoneNotFoundException,
            ActivityInfoNotFoundException, PhotoZoneTourSpotDuplicateException {

        if (photoZoneDto.getParentTourSpotId().equals(photoZoneDto.getTourSpotId()))
            throw new PhotoZoneTourSpotDuplicateException();

        if (exists(photoZoneDto.getParentTourSpotId(), photoZoneDto.getTourSpotId(),
                   photoZoneDto.getActivityId(), photoZoneDto.getName()))
            throw new PhotoZoneDuplicateException();

        TourSpot parentTourSpot = tourSpotEntityService.findEntityById(photoZoneDto.getParentTourSpotId());

        PhotoZone photoZone = modelMapper.map(photoZoneDto, PhotoZone.class);
        photoZone.setForeignEntities(parentTourSpot, tourSpot, activity);
        photoZone.denormalize(tourSpot, activity);

        PhotoZoneInfoDto photoZoneInfoDto = modelMapper.map(photoZoneDto, PhotoZoneInfoDto.class);
        photoZoneInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());

        PhotoZoneInfo photoZoneInfo = createInfo(photoZone, photoZoneInfoDto);
        photoZone.getPhotoZoneInfoList().add(photoZoneInfo);
        photoZoneDao.save(photoZone);
        return photoZone;
    }

    private PhotoZone update(PhotoZoneDto photoZoneDto, TourSpot tourSpot, Activity activity)
    throws PhotoZoneNotFoundException, PhotoZoneDuplicateException,
            PhotoZoneInfoNotFoundException, PhotoZoneTourSpotDuplicateException {

        PhotoZone photoZone = findEntityById(photoZoneDto.getId());

        if (photoZoneDto.getParentTourSpotId().equals(photoZoneDto.getTourSpotId()))
            throw new PhotoZoneTourSpotDuplicateException();

        if (!photoZone.getName().equals(photoZoneDto.getName())
            && existsByName(photoZone.getId(), photoZoneDto.getName()))
            throw new PhotoZoneDuplicateException();

        if (photoZone.getTourSpotId() != null && !photoZone.getTourSpotId().equals(photoZoneDto.getTourSpotId())
            && existsByTourSpotId(photoZone.getId(), photoZoneDto.getTourSpotId()))
            throw new PhotoZoneDuplicateException();

        if (photoZone.getActivityId() != null && photoZone.getActivityId() != photoZoneDto.getActivityId()
            && existsByActivityId(photoZone.getId(), photoZoneDto.getActivityId()))
            throw new PhotoZoneDuplicateException();

        photoZone.setForeignEntities(tourSpot, activity);
        photoZone.clone(photoZoneDto);
        photoZone.denormalize(tourSpot, activity);
        updateInfos(photoZone, tourSpot, activity);

        return photoZone;
    }

    @Override
    @Transactional
    public Integer delete(Integer photoZoneId) throws PhotoZoneNotFoundException {
        PhotoZone photoZone = findEntityById(photoZoneId);
        Integer tourSpotId = photoZone.getParentTourSpotId();
        photoZoneDao.delete(photoZone);
        return tourSpotId;
    }


//  =============================================== IMAGE ================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public PhotoZoneImage findImageByImageId(Integer photoZoneImageId) throws PhotoZoneImageNotFoundException {
        return photoZoneImageDao.findById(photoZoneImageId).orElseThrow(PhotoZoneImageNotFoundException::new);
    }

    @Override
    @Transactional
    public Integer saveImage(PhotoZoneImageDto photoZoneImageDto)
    throws PhotoZoneNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException {
        PhotoZone photoZone = findEntityById(photoZoneImageDto.getPhotoZoneId());

        String bucketKey = BucketDirectoryConstant.TOUR_SPOT_IMAGE + "/" +
                           photoZone.getTourSpot().getName() + "/photo-zone/" +
                           photoZone.getName() + "/" +
                           photoZoneImageDto.getName();

        if (photoZoneImageDao.count(PhotoZoneSpec.findImageByIdAndImageBucketKey(photoZone.getId(), bucketKey)) > 0)
            throw new ImageDuplicateException(photoZoneImageDto.getName());

        PhotoZoneImage photoZoneImage = modelMapper.map(photoZoneImageDto, PhotoZoneImage.class);
        photoZoneImage.setPhotoZone(photoZone);
        photoZoneImage.setBucketKey(bucketKey);
        photoZoneImageDao.save(photoZoneImage);
        bucketService.uploadImage(bucketKey, photoZoneImageDto.getName(), photoZoneImageDto.getMultipartFile());
        return photoZoneImage.getId();
    }

    @Override
    @Transactional
    public void updateRepImage(Integer imageId) throws PhotoZoneImageNotFoundException {
        PhotoZoneImage newRepImage = findImageByImageId(imageId);
        List<PhotoZoneImage> repImageList = photoZoneImageDao.findAll(PhotoZoneSpec.findImageByIdAndRepImage(newRepImage.getPhotoZoneId(), true));
        for (PhotoZoneImage repImage : repImageList) {
            if (newRepImage != repImage) repImage.setRepImage(false);
        }
        newRepImage.setRepImage(true);
    }

    @Override
    @Transactional
    public void updateImageOrder(List<Integer> imageIdList) {
        List<PhotoZoneImage> photoZoneImageList = photoZoneImageDao.findAll(PhotoZoneSpec.findImageByIds(imageIdList));
        HashMap<Integer, PhotoZoneImage> photoZoneImageHashMap = photoZoneImageList.stream()
                                                                                   .collect(Collectors.toMap(PhotoZoneImage::getId,
                                                                                              photoZoneImage -> photoZoneImage,
                                                                                              (oKey, nKey) -> oKey,
                                                                                              HashMap::new));
        int order = 0;
        for (Integer imageId : imageIdList) {
            PhotoZoneImage photoZoneImage = photoZoneImageHashMap.get(imageId);
            if (photoZoneImage != null) {
                photoZoneImage.setOrder(order);
                order++;
            }
        }
        photoZoneImageDao.saveAll(photoZoneImageList);
    }


    @Override
    @Transactional
    public void deleteImage(Integer photoZoneImageId)
    throws PhotoZoneImageNotFoundException, RepImageNotDeletableException {
        PhotoZoneImage photoZoneImage = findImageByImageId(photoZoneImageId);
        if (photoZoneImage.isRepImage())
            throw new RepImageNotDeletableException();
        bucketService.deleteImage(photoZoneImage.getBucketKey());
        photoZoneImageDao.delete(photoZoneImage);
    }




//  =============================================== INFO =================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public PhotoZoneInfo findInfoEntityByInfoId(Integer photoZoneInfoId) throws PhotoZoneInfoNotFoundException {
        return photoZoneInfoDao.findById(photoZoneInfoId).orElseThrow(PhotoZoneInfoNotFoundException::new);
    }

    private boolean infoExistsByIdAndSupportLanguageId(Integer photoZoneId, Integer supportLanguageId) {
        long count = photoZoneInfoDao.count(PhotoZoneSpec.findInfoByIdAndSupportLanguageId(photoZoneId, supportLanguageId));
        return count > 0;
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public PhotoZoneInfoDto saveInfo(PhotoZoneInfoDto photoZoneInfoDto)
    throws PhotoZoneNotFoundException, SupportLanguageNotFoundException, PhotoZoneInfoAlreadyExistsException,
            PhotoZoneInfoNotFoundException, TagInfoNotFoundException, PhotoZoneInfoNotEditableException,
            TourSpotInfoNotFoundException, ActivityInfoNotFoundException {



        PhotoZoneInfo photoZoneInfo;
        if (photoZoneInfoDto.getId() == null) photoZoneInfo = createInfo(null, photoZoneInfoDto);
        else photoZoneInfo = updateInfo(photoZoneInfoDto);
        photoZoneInfoDao.save(photoZoneInfo);
        return modelMapper.map(photoZoneInfo, PhotoZoneInfoDto.class);
    }

    private PhotoZoneInfo createInfo(PhotoZone photoZone, PhotoZoneInfoDto photoZoneInfoDto)
    throws SupportLanguageNotFoundException, PhotoZoneInfoAlreadyExistsException, TourSpotInfoNotFoundException,
            PhotoZoneNotFoundException, ActivityInfoNotFoundException {

        if (photoZone == null) photoZone = findEntityById(photoZoneInfoDto.getPhotoZoneId());

        Integer supportLanguageId = photoZoneInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        TourSpotInfo tourSpotInfo = photoZone.getTourSpotId() == null ? null :
                tourSpotEntityService.findInfoEntityByIdAndSupportLanguageId(photoZone.getTourSpotId(), supportLanguageId);

        ActivityInfo activityInfo = photoZone.getActivityId() == null ? null :
                activityEntityService.findInfoEntityByIdAndSupportLanguageId(photoZone.getActivityId(), supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(photoZone.getId(), photoZoneInfoDto.getSupportLanguageId()))
            throw new PhotoZoneInfoAlreadyExistsException(photoZone.getName(), supportLanguage.getName());

        TourSpotInfo parentTourSpotInfo = tourSpotEntityService.findInfoEntityByIdAndSupportLanguageId(photoZone.getParentTourSpotId(),
                                                                                                       supportLanguageId);
        PhotoZoneInfo photoZoneInfo = modelMapper.map(photoZoneInfoDto, PhotoZoneInfo.class);
        photoZoneInfo.setForeignEntities(photoZone, parentTourSpotInfo, tourSpotInfo, activityInfo, supportLanguage);
        photoZoneInfo.denormalize(photoZone);
        
        return photoZoneInfo;
    }

    private PhotoZoneInfo updateInfo(PhotoZoneInfoDto photoZoneInfoDto)
    throws PhotoZoneInfoNotFoundException, PhotoZoneInfoNotEditableException {

        PhotoZoneInfo photoZoneInfo = findInfoEntityByInfoId(photoZoneInfoDto.getId());

        if (photoZoneInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new PhotoZoneInfoNotEditableException();

        photoZoneInfo.clone(photoZoneInfoDto);
        return photoZoneInfo;
    }

    private void updateInfos(PhotoZone photoZone, TourSpot tourSpot, Activity activity)
    throws PhotoZoneInfoNotFoundException {

        List<PhotoZoneInfo> photoZoneInfoList = photoZone.getPhotoZoneInfoList();
        for (PhotoZoneInfo photoZoneInfo : photoZoneInfoList)
            photoZoneInfo.denormalize(photoZone);

        PhotoZoneInfo photoZoneInfoInKorean = photoZoneInfoList.stream()
                                                               .filter(t -> t.getSupportLanguageId()
                                                                             .equals(SupportLanguageEnum.Korean.getId()))
                                                               .findFirst()
                                                               .orElseThrow(PhotoZoneInfoNotFoundException::new);
        photoZoneInfoInKorean.clone(photoZone);

        if (tourSpot != null) {
            List<TourSpotInfo> tourSpotInfoList = tourSpotEntityService.findAllInfoById(tourSpot.getId());
            Map<Integer, TourSpotInfo> tourSpotInfoMap = tourSpotInfoList.stream()
                                                                         .collect(Collectors.toMap(TourSpotInfo::getSupportLanguageId,
                                                                                                   tourSpotInfo -> tourSpotInfo));

            for (PhotoZoneInfo photoZoneInfo : photoZoneInfoList) {
                photoZoneInfo.setTourSpotInfo(tourSpotInfoMap.get(photoZoneInfo.getSupportLanguageId()));
                photoZoneInfo.setActivityInfo(null);
            }
        } else if (activity != null) {
            List<ActivityInfo> activityInfoList = activityEntityService.findAllInfoById(activity.getId());
            Map<Integer, ActivityInfo> activityInfoMap = activityInfoList.stream()
                                                                         .collect(Collectors.toMap(ActivityInfo::getSupportLanguageId,
                                                                                                   activityInfo -> activityInfo));

            for (PhotoZoneInfo photoZoneInfo : photoZoneInfoList) {
                photoZoneInfo.setActivityInfo(activityInfoMap.get(photoZoneInfo.getSupportLanguageId()));
                photoZoneInfo.setTourSpotInfo(null);
            }
        }

    }


    @Override
    @Transactional
    public void deleteInfo(Integer photoZoneInfoId)
    throws PhotoZoneInfoNotFoundException, PhotoZoneInfoNotDeletableException {
        PhotoZoneInfo photoZoneInfo = findInfoEntityByInfoId(photoZoneInfoId);
        if (photoZoneInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new PhotoZoneInfoNotDeletableException();
        photoZoneInfoDao.delete(photoZoneInfo);
    }


}

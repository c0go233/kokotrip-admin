package com.kokotripadmin.service.implementations.tourspot;

import com.kokotripadmin.constant.BucketDirectoryConstant;
import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotDescriptionDao;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotDescriptionImageDao;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotDescriptionInfoDao;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionDto;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionImageDto;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionInfoDto;
import com.kokotripadmin.entity.tourspot.*;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tour_spot.*;
import com.kokotripadmin.service.entityinterfaces.SupportLanguageEntityService;
import com.kokotripadmin.service.entityinterfaces.TourSpotEntityService;
import com.kokotripadmin.service.interfaces.BucketService;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotDescriptionService;
import com.kokotripadmin.spec.tourspot.TourSpotDescriptionSpec;
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
public class TourSpotDescriptionServiceImpl implements TourSpotDescriptionService {

    private final ModelMapper                 modelMapper;
    private final Convert                     convert;
    private final TourSpotDescriptionDao      tourSpotDescriptionDao;
    private final TourSpotDescriptionInfoDao  tourSpotDescriptionInfoDao;
    private final TourSpotDescriptionImageDao tourSpotDescriptionImageDao;

    private final TourSpotEntityService        tourSpotEntityService;
    private final SupportLanguageEntityService supportLanguageEntityService;
    private final BucketService                bucketService;


    public TourSpotDescriptionServiceImpl(ModelMapper modelMapper,
                                          Convert convert,
                                          TourSpotDescriptionDao tourSpotDescriptionDao,
                                          TourSpotDescriptionInfoDao tourSpotDescriptionInfoDao,
                                          TourSpotDescriptionImageDao tourSpotDescriptionImageDao,
                                          TourSpotEntityService tourSpotEntityService,
                                          SupportLanguageEntityService supportLanguageEntityService,
                                          BucketService bucketService) {
        this.modelMapper = modelMapper;
        this.convert = convert;
        this.tourSpotDescriptionDao = tourSpotDescriptionDao;
        this.tourSpotDescriptionInfoDao = tourSpotDescriptionInfoDao;
        this.tourSpotDescriptionImageDao = tourSpotDescriptionImageDao;
        this.tourSpotEntityService = tourSpotEntityService;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.bucketService = bucketService;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotDescriptionDto findById(Integer tourSpotDescriptionId)
    throws TourSpotDescriptionNotFoundException {

        TourSpotDescription tourSpotDescription = findEntityById(tourSpotDescriptionId);
        return modelMapper.map(tourSpotDescription, TourSpotDescriptionDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotDescription findEntityById(Integer tourSpotDescriptionId)
    throws TourSpotDescriptionNotFoundException {

        return tourSpotDescriptionDao.findById(tourSpotDescriptionId)
                                     .orElseThrow(TourSpotDescriptionNotFoundException::new);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotDescriptionDto findByIdInDetail(Integer tourSpotDescriptionId)
    throws TourSpotDescriptionNotFoundException {
        TourSpotDescription tourSpotDescription = findEntityById(tourSpotDescriptionId);
        return convert.tourSpotDescriptionToDtoInDetail(tourSpotDescription);
    }


    @Override
    @Transactional
    public Integer save(TourSpotDescriptionDto tourSpotDescriptionDto)
    throws TourSpotNotFoundException, TourSpotInfoNotFoundException, TourSpotDescriptionNotFoundException,
           TourSpotDescriptionAlreadyExistsException, SupportLanguageNotFoundException,
           TourSpotDescriptionInfoAlreadyExistsException, TourSpotDescriptionInfoNotFoundException {

        TourSpotDescription tourSpotDescription;
        if (tourSpotDescriptionDto.getId() == null) tourSpotDescription = create(tourSpotDescriptionDto);
        else tourSpotDescription = update(tourSpotDescriptionDto);
        tourSpotDescriptionDao.save(tourSpotDescription);
        return tourSpotDescription.getId();
    }

    private boolean existsByTourSpotIdAndName(Integer tourSpotId, String tourSpotDescriptionName) {
        long count = tourSpotDescriptionDao
                .count(TourSpotDescriptionSpec.findByTourSpotIdAndName(tourSpotId, tourSpotDescriptionName));
        return count > 0;
    }

    private TourSpotDescription create(TourSpotDescriptionDto tourSpotDescriptionDto)
    throws TourSpotNotFoundException, SupportLanguageNotFoundException, TourSpotDescriptionInfoAlreadyExistsException,
           TourSpotInfoNotFoundException, TourSpotDescriptionAlreadyExistsException,
           TourSpotDescriptionNotFoundException {

        TourSpot tourSpot = tourSpotEntityService.findEntityById(tourSpotDescriptionDto.getTourSpotId());

        if (existsByTourSpotIdAndName(tourSpot.getId(), tourSpotDescriptionDto.getName()))
            throw new TourSpotDescriptionAlreadyExistsException(tourSpotDescriptionDto.getName());

        TourSpotDescription tourSpotDescription = modelMapper.map(tourSpotDescriptionDto, TourSpotDescription.class);
        tourSpotDescription.setTourSpot(tourSpot);

        TourSpotDescriptionInfoDto tourSpotDescriptionInfoDto =
                modelMapper.map(tourSpotDescriptionDto, TourSpotDescriptionInfoDto.class);
        tourSpotDescriptionInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());
        TourSpotDescriptionInfo tourSpotDescriptionInfo = createInfo(tourSpotDescription, tourSpotDescriptionInfoDto);

        tourSpotDescription.getTourSpotDescriptionInfoList().add(tourSpotDescriptionInfo);

        return tourSpotDescription;
    }

    private TourSpotDescription update(TourSpotDescriptionDto tourSpotDescriptionDto)
    throws TourSpotDescriptionNotFoundException, TourSpotDescriptionAlreadyExistsException,
           TourSpotDescriptionInfoNotFoundException {

        TourSpotDescription tourSpotDescription = findEntityById(tourSpotDescriptionDto.getId());

        if (!tourSpotDescription.getName().equals(tourSpotDescriptionDto.getName())
            && existsByTourSpotIdAndName(tourSpotDescription.getTourSpotId(), tourSpotDescriptionDto.getName()))
            throw new TourSpotDescriptionAlreadyExistsException(tourSpotDescriptionDto.getName());

        tourSpotDescription.clone(tourSpotDescriptionDto);
        updateDescriptionInfos(tourSpotDescription);

        return tourSpotDescription;
    }


    //  =================================== IMAGE ====================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotDescriptionImage findImageByImageId(Integer tourSpotDescriptionImageId)
    throws TourSpotDescriptionImageNotFoundException {
        return tourSpotDescriptionImageDao.findById(tourSpotDescriptionImageId)
                                          .orElseThrow(TourSpotDescriptionImageNotFoundException::new);
    }

    @Override
    @Transactional
    public Integer saveImage(TourSpotDescriptionImageDto tourSpotDescriptionImageDto)
    throws TourSpotDescriptionNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException {
        TourSpotDescription tourSpotDescription =
                findEntityById(tourSpotDescriptionImageDto.getTourSpotDescriptionId());

        String bucketKey = BucketDirectoryConstant.TOUR_SPOT_IMAGE + "/" +
                           tourSpotDescription.getTourSpot().getName() +
                           "/description/" +
                           tourSpotDescriptionImageDto.getName();

        if (tourSpotDescriptionImageDao.count(TourSpotDescriptionSpec
                                                      .findImageByIdAndImageBucketKey(tourSpotDescription.getId(),
                                                                                      bucketKey)) > 0)
            throw new ImageDuplicateException(tourSpotDescriptionImageDto.getName());

        TourSpotDescriptionImage tourSpotDescriptionImage = modelMapper.map(tourSpotDescriptionImageDto,
                                                                            TourSpotDescriptionImage.class);
        tourSpotDescriptionImage.setTourSpotDescription(tourSpotDescription);
        tourSpotDescriptionImage.setBucketKey(bucketKey);
        tourSpotDescriptionImageDao.save(tourSpotDescriptionImage);
        bucketService.uploadImage(bucketKey,
                                  tourSpotDescriptionImageDto.getName(),
                                  tourSpotDescriptionImageDto.getMultipartFile());
        return tourSpotDescriptionImage.getId();
    }

    @Override
    @Transactional
    public void updateImageOrder(List<Integer> imageIdList) {
        List<TourSpotDescriptionImage> tourSpotDescriptionImageList =
                tourSpotDescriptionImageDao.findAll(TourSpotDescriptionSpec.findImageByIds(imageIdList));
        HashMap<Integer, TourSpotDescriptionImage> tourSpotDescriptionImageHashMap
                = tourSpotDescriptionImageList.stream()
                                              .collect(Collectors.toMap(TourSpotDescriptionImage::getId,
                                                                        tourSpotDescriptionImage -> tourSpotDescriptionImage,
                                                                        (oKey, nKey) -> oKey,
                                                                        HashMap::new));
        int order = 0;
        for (Integer imageId : imageIdList) {
            TourSpotDescriptionImage tourSpotDescriptionImage = tourSpotDescriptionImageHashMap.get(imageId);
            if (tourSpotDescriptionImage != null) {
                tourSpotDescriptionImage.setOrder(order);
                order++;
            }
        }
        tourSpotDescriptionImageDao.saveAll(tourSpotDescriptionImageList);
    }

    @Override
    @Transactional
    public void deleteImage(Integer tourSpotDescriptionImageId)
    throws TourSpotDescriptionImageNotFoundException {
        TourSpotDescriptionImage tourSpotDescriptionImage = findImageByImageId(tourSpotDescriptionImageId);
        bucketService.deleteImage(tourSpotDescriptionImage.getBucketKey());
        tourSpotDescriptionImageDao.delete(tourSpotDescriptionImage);
    }



    //  ============================================================================================================= //
    //  ===========================================TOUR_SPOT_DESCRIPTION_INFO======================================== //
    //  ============================================================================================================= //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotDescriptionInfo findInfoEntityByInfoId(Integer tourSpotDescriptionInfoId)
    throws TourSpotDescriptionInfoNotFoundException {

        return tourSpotDescriptionInfoDao.findById(tourSpotDescriptionInfoId)
                                         .orElseThrow(TourSpotDescriptionInfoNotFoundException::new);
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public TourSpotDescriptionInfoDto saveInfo(TourSpotDescriptionInfoDto tourSpotDescriptionInfoDto)
    throws SupportLanguageNotFoundException, TourSpotInfoNotFoundException,
           TourSpotDescriptionInfoAlreadyExistsException, TourSpotDescriptionNotFoundException,
           TourSpotDescriptionInfoNotEditableException, TourSpotDescriptionInfoNotFoundException {

        TourSpotDescriptionInfo tourSpotDescriptionInfo;
        if (tourSpotDescriptionInfoDto.getId() == null)
            tourSpotDescriptionInfo = createInfo(null, tourSpotDescriptionInfoDto);
        else tourSpotDescriptionInfo = updateInfo(tourSpotDescriptionInfoDto);

        tourSpotDescriptionInfoDao.save(tourSpotDescriptionInfo);
        return modelMapper.map(tourSpotDescriptionInfo, TourSpotDescriptionInfoDto.class);
    }

    @Override
    @Transactional
    public Integer delete(Integer tourSpotDescriptionId) throws TourSpotDescriptionNotFoundException {
        TourSpotDescription tourSpotDescription = findEntityById(tourSpotDescriptionId);
        Integer tourSpotId = tourSpotDescription.getTourSpotId();
        tourSpotDescriptionDao.delete(tourSpotDescription);
        return tourSpotId;
    }

    @Override
    @Transactional
    public void deleteInfo(Integer tourSpotDescriptionInfoId)
    throws TourSpotDescriptionInfoNotFoundException, TourSpotDescriptionInfoNotDeletableException {
        TourSpotDescriptionInfo tourSpotDescriptionInfo = findInfoEntityByInfoId(tourSpotDescriptionInfoId);
        if (tourSpotDescriptionInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new TourSpotDescriptionInfoNotDeletableException();
        tourSpotDescriptionInfoDao.delete(tourSpotDescriptionInfo);
    }

    private TourSpotDescriptionInfo updateInfo(TourSpotDescriptionInfoDto tourSpotDescriptionInfoDto)
    throws TourSpotDescriptionInfoNotFoundException, TourSpotDescriptionInfoNotEditableException {

        TourSpotDescriptionInfo tourSpotDescriptionInfo = findInfoEntityByInfoId(tourSpotDescriptionInfoDto.getId());

        if (tourSpotDescriptionInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new TourSpotDescriptionInfoNotEditableException();

        tourSpotDescriptionInfo.clone(tourSpotDescriptionInfoDto);

        return tourSpotDescriptionInfo;
    }


    private TourSpotDescriptionInfo createInfo(TourSpotDescription tourSpotDescription,
                                               TourSpotDescriptionInfoDto tourSpotDescriptionInfoDto)
    throws SupportLanguageNotFoundException, TourSpotDescriptionInfoAlreadyExistsException,
           TourSpotInfoNotFoundException, TourSpotDescriptionNotFoundException {

        if (tourSpotDescription == null)
            tourSpotDescription = findEntityById(tourSpotDescriptionInfoDto.getTourSpotDescriptionId());

        Integer supportLanguageId = tourSpotDescriptionInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(tourSpotDescription.getId(), supportLanguageId))
            throw new TourSpotDescriptionInfoAlreadyExistsException(tourSpotDescription.getName(),
                                                                    supportLanguage.getName());

        TourSpotInfo tourSpotInfo =
                tourSpotEntityService
                        .findInfoEntityByIdAndSupportLanguageId(tourSpotDescription.getTourSpotId(), supportLanguageId);

        TourSpotDescriptionInfo tourSpotDescriptionInfo = modelMapper
                .map(tourSpotDescriptionInfoDto, TourSpotDescriptionInfo.class);
        tourSpotDescriptionInfo.setForeignEntities(tourSpotDescription, tourSpotInfo, supportLanguage);
        return tourSpotDescriptionInfo;

    }


    private void updateDescriptionInfos(TourSpotDescription tourSpotDescription)
    throws TourSpotDescriptionInfoNotFoundException {

        List<TourSpotDescriptionInfo> tourSpotDescriptionInfoList = tourSpotDescription
                .getTourSpotDescriptionInfoList();
        for (TourSpotDescriptionInfo tourSpotDescriptionInfo : tourSpotDescriptionInfoList)
            tourSpotDescriptionInfo.denormalize(tourSpotDescription);

        TourSpotDescriptionInfo tourSpotDescriptionInfoInKorean =
                tourSpotDescriptionInfoList.stream()
                                           .filter(t -> t.getSupportLanguageId()
                                                         .equals(SupportLanguageEnum.Korean.getId()))
                                           .findFirst()
                                           .orElseThrow(TourSpotDescriptionInfoNotFoundException::new);
        tourSpotDescriptionInfoInKorean.clone(tourSpotDescription);

    }

    private boolean infoExistsByIdAndSupportLanguageId(Integer tourSpotDescriptionId, Integer supportLanguageId) {
        long count = tourSpotDescriptionInfoDao
                .count(TourSpotDescriptionSpec.findInfoByIdAndSupportLanguageId(tourSpotDescriptionId,
                                                                                supportLanguageId));
        return count > 0;
    }
}

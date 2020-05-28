package com.kokotripadmin.service.implementations.tourspot;

import com.kokotripadmin.constant.BucketDirectoryConstant;
import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotTicketDescriptionImageDao;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotTicketDescriptionInfoDao;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotTicketDescriptionDao;
import com.kokotripadmin.dto.tourspot.TourSpotTicketDescriptionDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketDescriptionImageDto;
import com.kokotripadmin.dto.tourspot.TourSpotTicketDescriptionInfoDto;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.tourspot.ticket.*;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tour_spot.ticket.*;
import com.kokotripadmin.service.entityinterfaces.SupportLanguageEntityService;
import com.kokotripadmin.service.entityinterfaces.TourSpotTicketEntityService;
import com.kokotripadmin.service.interfaces.BucketService;
import com.kokotripadmin.service.interfaces.tourspot.TourSpotTicketDescriptionService;
import com.kokotripadmin.spec.tourspot.TourSpotTicketDescriptionSpec;
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
public class TourSpotTicketDescriptionServiceImpl implements TourSpotTicketDescriptionService {

    private final ModelMapper                       modelMapper;
    private final Convert                           convert;
    private final TourSpotTicketDescriptionDao      tourSpotTicketDescriptionDao;
    private final TourSpotTicketDescriptionInfoDao  tourSpotTicketDescriptionInfoDao;
    private final TourSpotTicketDescriptionImageDao tourSpotTicketDescriptionImageDao;

    private final TourSpotTicketEntityService  tourSpotTicketEntityService;
    private final SupportLanguageEntityService supportLanguageEntityService;
    private final BucketService                bucketService;

    private final String TOUR_SPOT_TICKET_DESCRIPTION_IMAGE_DIRECTORY = "tour-spot/ticket/description/image";


    public TourSpotTicketDescriptionServiceImpl(ModelMapper modelMapper,
                                                Convert convert,
                                                TourSpotTicketDescriptionDao tourSpotTicketDescriptionDao,
                                                TourSpotTicketDescriptionInfoDao tourSpotTicketDescriptionInfoDao,
                                                TourSpotTicketDescriptionImageDao tourSpotTicketDescriptionImageDao,
                                                TourSpotTicketEntityService tourSpotTicketEntityService,
                                                SupportLanguageEntityService supportLanguageEntityService,
                                                BucketService bucketService) {
        this.modelMapper = modelMapper;
        this.convert = convert;
        this.tourSpotTicketDescriptionDao = tourSpotTicketDescriptionDao;
        this.tourSpotTicketDescriptionInfoDao = tourSpotTicketDescriptionInfoDao;
        this.tourSpotTicketDescriptionImageDao = tourSpotTicketDescriptionImageDao;
        this.tourSpotTicketEntityService = tourSpotTicketEntityService;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.bucketService = bucketService;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotTicketDescriptionDto findById(Integer tourSpotTicketDescriptionId)
    throws TourSpotTicketDescriptionNotFoundException {

        TourSpotTicketDescription tourSpotTicketDescription = findEntityById(tourSpotTicketDescriptionId);
        return modelMapper.map(tourSpotTicketDescription, TourSpotTicketDescriptionDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotTicketDescription findEntityById(Integer tourSpotTicketDescriptionId)
    throws TourSpotTicketDescriptionNotFoundException {
        return tourSpotTicketDescriptionDao.findById(tourSpotTicketDescriptionId)
                                           .orElseThrow(TourSpotTicketDescriptionNotFoundException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotTicketDescriptionDto findByIdInDetail(Integer tourSpotTicketDescriptionId)
    throws TourSpotTicketDescriptionNotFoundException {
        TourSpotTicketDescription tourSpotTicketDescription = findEntityById(tourSpotTicketDescriptionId);
        return convert.tourSpotTicketDescriptionToDtoInDetail(tourSpotTicketDescription);
    }


    @Override
    @Transactional
    public Integer save(TourSpotTicketDescriptionDto tourSpotTicketDescriptionDto)
    throws TourSpotTicketDescriptionNotFoundException,
           TourSpotTicketDescriptionAlreadyExistsException, SupportLanguageNotFoundException,
           TourSpotTicketDescriptionInfoAlreadyExistsException, TourSpotTicketDescriptionInfoNotFoundException,
           TourSpotTicketNotFoundException, TourSpotTicketInfoNotFoundException {

        TourSpotTicketDescription tourSpotTicketDescription;
        if (tourSpotTicketDescriptionDto.getId() == null)
            tourSpotTicketDescription = create(tourSpotTicketDescriptionDto);
        else tourSpotTicketDescription = update(tourSpotTicketDescriptionDto);
        tourSpotTicketDescriptionDao.save(tourSpotTicketDescription);
        return tourSpotTicketDescription.getId();
    }

    private boolean existsByTourSpotTicketIdAndName(Integer tourSpotTicketId, String name) {
        long count = tourSpotTicketDescriptionDao
                .count(TourSpotTicketDescriptionSpec.findByTourSpotTicketIdAndName(tourSpotTicketId, name));
        return count > 0;
    }

    private TourSpotTicketDescription create(TourSpotTicketDescriptionDto tourSpotTicketDescriptionDto)
    throws SupportLanguageNotFoundException, TourSpotTicketDescriptionInfoAlreadyExistsException,
           TourSpotTicketDescriptionAlreadyExistsException, TourSpotTicketDescriptionNotFoundException,
           TourSpotTicketNotFoundException, TourSpotTicketInfoNotFoundException {

        TourSpotTicket tourSpotTicket =
                tourSpotTicketEntityService.findEntityById(tourSpotTicketDescriptionDto.getTourSpotTicketId());

        if (existsByTourSpotTicketIdAndName(tourSpotTicket.getId(), tourSpotTicketDescriptionDto.getName()))
            throw new TourSpotTicketDescriptionAlreadyExistsException(tourSpotTicketDescriptionDto.getName());

        TourSpotTicketDescription tourSpotTicketDescription =
                modelMapper.map(tourSpotTicketDescriptionDto, TourSpotTicketDescription.class);
        tourSpotTicketDescription.setTourSpotTicket(tourSpotTicket);

        TourSpotTicketDescriptionInfoDto tourSpotTicketDescriptionInfoDto =
                modelMapper.map(tourSpotTicketDescriptionDto, TourSpotTicketDescriptionInfoDto.class);
        tourSpotTicketDescriptionInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());
        TourSpotTicketDescriptionInfo tourSpotTicketDescriptionInfo =
                createInfo(tourSpotTicketDescription, tourSpotTicketDescriptionInfoDto);

        tourSpotTicketDescription.getTourSpotTicketDescriptionInfoList().add(tourSpotTicketDescriptionInfo);

        return tourSpotTicketDescription;
    }

    private TourSpotTicketDescription update(TourSpotTicketDescriptionDto tourSpotTicketDescriptionDto)
    throws TourSpotTicketDescriptionNotFoundException, TourSpotTicketDescriptionAlreadyExistsException,
           TourSpotTicketDescriptionInfoNotFoundException {

        TourSpotTicketDescription tourSpotTicketDescription = findEntityById(tourSpotTicketDescriptionDto.getId());

        if (!tourSpotTicketDescription.getName().equals(tourSpotTicketDescriptionDto.getName())
            && existsByTourSpotTicketIdAndName(tourSpotTicketDescription.getTourSpotTicketId(),
                                               tourSpotTicketDescriptionDto.getName()))
            throw new TourSpotTicketDescriptionAlreadyExistsException(tourSpotTicketDescriptionDto.getName());

        tourSpotTicketDescription.clone(tourSpotTicketDescriptionDto);
        updateDescriptionInfos(tourSpotTicketDescription);

        return tourSpotTicketDescription;
    }

    @Override
    @Transactional
    public Integer delete(Integer tourSpotTicketDescriptionId) throws TourSpotTicketDescriptionNotFoundException {
        TourSpotTicketDescription tourSpotTicketDescription = findEntityById(tourSpotTicketDescriptionId);
        Integer tourSpotTicketId = tourSpotTicketDescription.getTourSpotTicketId();
        tourSpotTicketDescriptionDao.delete(tourSpotTicketDescription);
        return tourSpotTicketId;
    }


//  =================================== IMAGE ====================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotTicketDescriptionImage findImageByImageId(Integer tourSpotTicketDescriptionImageId)
    throws TourSpotTicketDescriptionImageNotFoundException {
        return tourSpotTicketDescriptionImageDao.findById(tourSpotTicketDescriptionImageId)
                                                .orElseThrow(TourSpotTicketDescriptionImageNotFoundException::new);
    }

    @Override
    @Transactional
    public Integer saveImage(TourSpotTicketDescriptionImageDto tourSpotTicketDescriptionImageDto)
    throws TourSpotTicketDescriptionNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException {

        TourSpotTicketDescription tourSpotTicketDescription =
                findEntityById(tourSpotTicketDescriptionImageDto.getTourSpotTicketDescriptionId());

        String bucketKey = BucketDirectoryConstant.TOUR_SPOT_IMAGE + "/" +
                           tourSpotTicketDescription.getTourSpotTicket().getTourSpot().getName() + "/ticket" +
                           tourSpotTicketDescription.getTourSpotTicket().getName() + "/description/" +
                           tourSpotTicketDescriptionImageDto.getName();

        if (tourSpotTicketDescriptionImageDao.count(TourSpotTicketDescriptionSpec
                                                            .findImageByIdAndImageBucketKey(tourSpotTicketDescription.getId(),
                                                                                            bucketKey)) > 0)
            throw new ImageDuplicateException(tourSpotTicketDescriptionImageDto.getName());

        TourSpotTicketDescriptionImage tourSpotTicketDescriptionImage =
                modelMapper.map(tourSpotTicketDescriptionImageDto,
                                TourSpotTicketDescriptionImage.class);

        tourSpotTicketDescriptionImage.setTourSpotTicketDescription(tourSpotTicketDescription);
        tourSpotTicketDescriptionImage.setBucketKey(bucketKey);
        tourSpotTicketDescriptionImageDao.save(tourSpotTicketDescriptionImage);
        bucketService.uploadImage(bucketKey,
                                  tourSpotTicketDescriptionImageDto.getName(),
                                  tourSpotTicketDescriptionImageDto.getMultipartFile());
        return tourSpotTicketDescriptionImage.getId();
    }

    @Override
    @Transactional
    public void updateImageOrder(List<Integer> imageIdList) {
        List<TourSpotTicketDescriptionImage> tourSpotTicketDescriptionImageList =
                tourSpotTicketDescriptionImageDao.findAll(TourSpotTicketDescriptionSpec.findImageByIds(imageIdList));
        HashMap<Integer, TourSpotTicketDescriptionImage> tourSpotTicketDescriptionImageHashMap
                = tourSpotTicketDescriptionImageList.stream()
                                                    .collect(Collectors.toMap(TourSpotTicketDescriptionImage::getId,
                                                                              tourSpotTicketDescriptionImage -> tourSpotTicketDescriptionImage,
                                                                              (oKey, nKey) -> oKey,
                                                                              HashMap::new));
        int order = 0;
        for (Integer imageId : imageIdList) {
            TourSpotTicketDescriptionImage tourSpotTicketDescriptionImage =
                    tourSpotTicketDescriptionImageHashMap.get(imageId);
            if (tourSpotTicketDescriptionImage != null) {
                tourSpotTicketDescriptionImage.setOrder(order);
                order++;
            }
        }
        tourSpotTicketDescriptionImageDao.saveAll(tourSpotTicketDescriptionImageList);
    }

    @Override
    @Transactional
    public void deleteImage(Integer tourSpotTicketDescriptionImageId)
    throws TourSpotTicketDescriptionImageNotFoundException {
        TourSpotTicketDescriptionImage tourSpotTicketDescriptionImage =
                findImageByImageId(tourSpotTicketDescriptionImageId);
        bucketService.deleteImage(tourSpotTicketDescriptionImage.getBucketKey());
        tourSpotTicketDescriptionImageDao.delete(tourSpotTicketDescriptionImage);
    }


//  ============================================ INFO ====================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TourSpotTicketDescriptionInfo findInfoEntityByInfoId(Integer tourSpotTicketDescriptionInfoId)
    throws TourSpotTicketDescriptionInfoNotFoundException {

        return tourSpotTicketDescriptionInfoDao.findById(tourSpotTicketDescriptionInfoId)
                                               .orElseThrow(TourSpotTicketDescriptionInfoNotFoundException::new);
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public TourSpotTicketDescriptionInfoDto saveInfo(TourSpotTicketDescriptionInfoDto tourSpotTicketDescriptionInfoDto)
    throws SupportLanguageNotFoundException, TourSpotTicketDescriptionInfoAlreadyExistsException,
           TourSpotTicketDescriptionNotFoundException,
           TourSpotTicketDescriptionInfoNotEditableException, TourSpotTicketDescriptionInfoNotFoundException,
           TourSpotTicketInfoNotFoundException {

        TourSpotTicketDescriptionInfo tourSpotTicketDescriptionInfo;
        if (tourSpotTicketDescriptionInfoDto.getId() == null)
            tourSpotTicketDescriptionInfo = createInfo(null, tourSpotTicketDescriptionInfoDto);
        else tourSpotTicketDescriptionInfo = updateInfo(tourSpotTicketDescriptionInfoDto);

        tourSpotTicketDescriptionInfoDao.save(tourSpotTicketDescriptionInfo);
        return modelMapper.map(tourSpotTicketDescriptionInfo, TourSpotTicketDescriptionInfoDto.class);
    }


    @Override
    @Transactional
    public void deleteInfo(Integer tourSpotTicketDescriptionInfoId)
    throws TourSpotTicketDescriptionInfoNotFoundException, TourSpotTicketDescriptionInfoNotDeletableException {
        TourSpotTicketDescriptionInfo tourSpotTicketDescriptionInfo =
                findInfoEntityByInfoId(tourSpotTicketDescriptionInfoId);
        if (tourSpotTicketDescriptionInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new TourSpotTicketDescriptionInfoNotDeletableException();
        tourSpotTicketDescriptionInfoDao.delete(tourSpotTicketDescriptionInfo);
    }

    private TourSpotTicketDescriptionInfo updateInfo(TourSpotTicketDescriptionInfoDto tourSpotTicketDescriptionInfoDto)
    throws TourSpotTicketDescriptionInfoNotFoundException, TourSpotTicketDescriptionInfoNotEditableException {

        TourSpotTicketDescriptionInfo tourSpotTicketDescriptionInfo =
                findInfoEntityByInfoId(tourSpotTicketDescriptionInfoDto.getId());

        if (tourSpotTicketDescriptionInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new TourSpotTicketDescriptionInfoNotEditableException();

        tourSpotTicketDescriptionInfo.clone(tourSpotTicketDescriptionInfoDto);

        return tourSpotTicketDescriptionInfo;
    }


    private TourSpotTicketDescriptionInfo createInfo(TourSpotTicketDescription tourSpotTicketDescription,
                                                     TourSpotTicketDescriptionInfoDto tourSpotTicketDescriptionInfoDto)
    throws SupportLanguageNotFoundException, TourSpotTicketDescriptionInfoAlreadyExistsException,
           TourSpotTicketDescriptionNotFoundException, TourSpotTicketInfoNotFoundException {

        if (tourSpotTicketDescription == null)
            tourSpotTicketDescription =
                    findEntityById(tourSpotTicketDescriptionInfoDto.getTourSpotTicketDescriptionId());

        Integer supportLanguageId = tourSpotTicketDescriptionInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(tourSpotTicketDescription.getId(), supportLanguageId))
            throw new TourSpotTicketDescriptionInfoAlreadyExistsException(tourSpotTicketDescription.getName(),
                                                                          supportLanguage.getName());

        TourSpotTicketInfo tourSpotTicketInfo = tourSpotTicketEntityService
                .findInfoEntityByIdAndSupportLanguageId(tourSpotTicketDescription.getTourSpotTicketId(),
                                                        supportLanguageId);

        TourSpotTicketDescriptionInfo tourSpotTicketDescriptionInfo = modelMapper
                .map(tourSpotTicketDescriptionInfoDto, TourSpotTicketDescriptionInfo.class);
        tourSpotTicketDescriptionInfo
                .setForeignEntities(tourSpotTicketInfo, tourSpotTicketDescription, supportLanguage);
        return tourSpotTicketDescriptionInfo;

    }


    private void updateDescriptionInfos(TourSpotTicketDescription tourSpotTicketDescription)
    throws TourSpotTicketDescriptionInfoNotFoundException {

        List<TourSpotTicketDescriptionInfo> tourSpotTicketDescriptionInfoList = tourSpotTicketDescription
                .getTourSpotTicketDescriptionInfoList();
        for (TourSpotTicketDescriptionInfo tourSpotTicketDescriptionInfo : tourSpotTicketDescriptionInfoList)
            tourSpotTicketDescriptionInfo.denormalize(tourSpotTicketDescription);

        TourSpotTicketDescriptionInfo tourSpotTicketDescriptionInfoInKorean =
                tourSpotTicketDescriptionInfoList.stream()
                                                 .filter(t -> t.getSupportLanguageId()
                                                               .equals(SupportLanguageEnum.Korean.getId()))
                                                 .findFirst()
                                                 .orElseThrow(TourSpotTicketDescriptionInfoNotFoundException::new);
        tourSpotTicketDescriptionInfoInKorean.clone(tourSpotTicketDescription);

    }

    private boolean infoExistsByIdAndSupportLanguageId(Integer tourSpotTicketDescriptionId, Integer supportLanguageId) {
        long count = tourSpotTicketDescriptionInfoDao
                .count(TourSpotTicketDescriptionSpec.findInfoByIdAndSupportLanguageId(tourSpotTicketDescriptionId,
                                                                                      supportLanguageId));
        return count > 0;
    }


}

package com.kokotripadmin.service.implementations.activity;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.activity.ActivityTicketDao;
import com.kokotripadmin.dao.interfaces.activity.ActivityTicketImageDao;
import com.kokotripadmin.dao.interfaces.activity.ActivityTicketInfoDao;
import com.kokotripadmin.dto.activity.ActivityTicketDto;
import com.kokotripadmin.dto.activity.ActivityTicketImageDto;
import com.kokotripadmin.dto.activity.ActivityTicketInfoDto;
import com.kokotripadmin.dto.ticket.TicketPriceDto;
import com.kokotripadmin.entity.activity.*;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.ticket.TicketType;
import com.kokotripadmin.exception.activity.ActivityInfoNotFoundException;
import com.kokotripadmin.exception.activity.ActivityNotFoundException;
import com.kokotripadmin.exception.activity.ticket.*;
import com.kokotripadmin.exception.image.FileIsNotImageException;
import com.kokotripadmin.exception.image.ImageDuplicateException;
import com.kokotripadmin.exception.image.RepImageNotDeletableException;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.ticket.TicketTypeNotFoundException;
import com.kokotripadmin.service.entityinterfaces.ActivityEntityService;
import com.kokotripadmin.service.entityinterfaces.ActivityTicketEntityService;
import com.kokotripadmin.service.entityinterfaces.SupportLanguageEntityService;
import com.kokotripadmin.service.entityinterfaces.TicketTypeEntityService;
import com.kokotripadmin.service.interfaces.BucketService;
import com.kokotripadmin.service.interfaces.activity.ActivityTicketService;
import com.kokotripadmin.spec.ActivityTicketSpec;
import com.kokotripadmin.util.Convert;
import org.modelmapper.ModelMapper;
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
public class ActivityTicketServiceImpl implements ActivityTicketService, ActivityTicketEntityService {



    private final ActivityTicketDao     activityTicketDao;
    private final ActivityTicketInfoDao activityTicketInfoDao;
    private final ActivityTicketImageDao activityTicketImageDao;

    private final ActivityEntityService        activityEntityService;
    private final TicketTypeEntityService      ticketTypeEntityService;
    private final SupportLanguageEntityService supportLanguageEntityService;
    private final BucketService bucketService;

    private final ModelMapper modelMapper;
    private final Convert     convert;

    private final String ACTIVITY_TICKET_IMAGE_DIRECTORY = "activity/ticket/image";

    public ActivityTicketServiceImpl(ActivityTicketDao activityTicketDao,
                                     ActivityTicketInfoDao activityTicketInfoDao,
                                     ActivityTicketImageDao activityTicketImageDao,
                                     ActivityEntityService activityEntityService,
                                     TicketTypeEntityService ticketTypeEntityService,
                                     SupportLanguageEntityService supportLanguageEntityService,
                                     BucketService bucketService,
                                     ModelMapper modelMapper, Convert convert) {
        this.activityTicketDao = activityTicketDao;
        this.activityTicketInfoDao = activityTicketInfoDao;
        this.activityTicketImageDao = activityTicketImageDao;
        this.activityEntityService = activityEntityService;
        this.ticketTypeEntityService = ticketTypeEntityService;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.bucketService = bucketService;
        this.modelMapper = modelMapper;
        this.convert = convert;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityTicket findEntityById(Integer activityTicketId) throws ActivityTicketNotFoundException {
        return activityTicketDao.findById(activityTicketId).orElseThrow(ActivityTicketNotFoundException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityTicketInfo findInfoEntityByIdAndSupportLanguageId(Integer activityTicketId,
                                                                     Integer supportLanguageId)
    throws ActivityTicketInfoNotFoundException {
        return activityTicketInfoDao.findOne(ActivityTicketSpec.findInfoByIdAndSupportLanguageId(activityTicketId, supportLanguageId))
                                    .orElseThrow(ActivityTicketInfoNotFoundException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityTicketDto findById(Integer activityTicketId) throws ActivityTicketNotFoundException {
        ActivityTicket activityTicket = findEntityById(activityTicketId);
        return modelMapper.map(activityTicket, ActivityTicketDto.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityTicketDto findByIdInDetail(Integer activityTicketId) throws ActivityTicketNotFoundException {
        ActivityTicket activityTicket = findEntityById(activityTicketId);
        return convert.activityTicketToDtoInDetail(activityTicket);
    }

    @Override
    @Transactional
    public Integer save(ActivityTicketDto activityTicketDto)
    throws ActivityTicketNotFoundException, SupportLanguageNotFoundException,
           ActivityNotFoundException, ActivityTicketNameDuplicateException, ActivityInfoNotFoundException,
           TicketTypeNotFoundException, ActivityTicketInfoAlreadyExistsException, ActivityTicketInfoNotFoundException {

        ActivityTicket activityTicket;
        if (activityTicketDto.getId() == null) activityTicket = create(activityTicketDto);
        else activityTicket = update(activityTicketDto);
        activityTicketDao.save(activityTicket);
        return activityTicket.getId();
    }

    private boolean existsByActivityIdAndName(Integer activityId, String activityTicketName) {
        long count = activityTicketDao.count(ActivityTicketSpec.findByActivityIdAndName(activityId, activityTicketName));
        return count > 0;
    }

    private ActivityTicket create(ActivityTicketDto activityTicketDto)
    throws ActivityInfoNotFoundException, TicketTypeNotFoundException, ActivityNotFoundException,
           ActivityTicketNameDuplicateException, ActivityTicketInfoAlreadyExistsException,
           ActivityTicketNotFoundException,
           SupportLanguageNotFoundException {


        Integer activityId = activityTicketDto.getActivityId();

        if (existsByActivityIdAndName(activityId, activityTicketDto.getName()))
            throw new ActivityTicketNameDuplicateException(activityTicketDto.getName());

        Activity activity = activityEntityService.findEntityById(activityId);

        ActivityTicket activityTicket = modelMapper.map(activityTicketDto, ActivityTicket.class);
        activityTicket.setActivity(activity);

        setTicketPriceList(activityTicket, activityTicketDto.getTicketPriceDtoList());

        ActivityTicketInfoDto activityTicketInfoDto = modelMapper.map(activityTicketDto, ActivityTicketInfoDto.class);
        activityTicketInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());

        ActivityTicketInfo activityTicketInfo = createInfo(activityTicket, activityTicketInfoDto);
        activityTicket.getActivityTicketInfoList().add(activityTicketInfo);

        return activityTicket;
    }

    private void setTicketPriceList(ActivityTicket activityTicket, List<TicketPriceDto> ticketPriceDtoList)
    throws TicketTypeNotFoundException {
        for (TicketPriceDto ticketPriceDto : ticketPriceDtoList) {

            TicketType ticketType = ticketTypeEntityService.findEntityById(ticketPriceDto.getTicketTypeId());

            ActivityTicketPrice activityTicketPrice = modelMapper.map(ticketPriceDto, ActivityTicketPrice.class);
            activityTicketPrice.setActivityTicket(activityTicket);
            activityTicketPrice.setTicketType(ticketType);

            if (activityTicketPrice.isRepPrice())
                activityTicket.setRepPrice(activityTicketPrice.getPrice());
            activityTicket.getActivityTicketPriceList().add(activityTicketPrice);
        }
    }

    private ActivityTicket update(ActivityTicketDto activityTicketDto)
    throws ActivityTicketNotFoundException, ActivityTicketInfoNotFoundException, ActivityTicketNameDuplicateException,
           TicketTypeNotFoundException {

        ActivityTicket activityTicket = findEntityById(activityTicketDto.getId());

        if (!activityTicket.getName().equals(activityTicketDto.getName())
            && existsByActivityIdAndName(activityTicket.getActivityId(), activityTicketDto.getName()))
            throw new ActivityTicketNameDuplicateException(activityTicketDto.getName());

        activityTicket.clone(activityTicketDto);
        activityTicket.getActivityTicketPriceList().clear();
        setTicketPriceList(activityTicket, activityTicketDto.getTicketPriceDtoList());

        updateTicketInfos(activityTicket);
        activityTicket.setUpdatedAt(new Date());

        return activityTicket;
    }

    @Override
    @Transactional
    public Integer delete(Integer activityTicketId) throws ActivityTicketNotFoundException {
        ActivityTicket activityTicket = findEntityById(activityTicketId);
        Integer activityId = activityTicket.getActivityId();
        activityTicketDao.delete(activityTicket);
        return activityId;
    }

//  =================================== IMAGE ====================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityTicketImage findImageByImageId(Integer activityTicketImageId) throws ActivityTicketImageNotFoundException {
        return activityTicketImageDao.findById(activityTicketImageId).orElseThrow(ActivityTicketImageNotFoundException::new);
    }


    @Override
    @Transactional
    public Integer saveImage(ActivityTicketImageDto activityTicketImageDto)
    throws ActivityTicketNotFoundException, ImageDuplicateException, IOException, FileIsNotImageException {
        ActivityTicket activityTicket = findEntityById(activityTicketImageDto.getActivityTicketId());
        String bucketKey = ACTIVITY_TICKET_IMAGE_DIRECTORY + "/" + activityTicket.getName() + "/" + activityTicketImageDto.getName();

        if (activityTicketImageDao.count(ActivityTicketSpec.findImageByIdAndImageBucketKey(activityTicket.getId(), bucketKey)) > 0)
            throw new ImageDuplicateException(activityTicketImageDto.getName());

        ActivityTicketImage activityTicketImage = modelMapper.map(activityTicketImageDto, ActivityTicketImage.class);
        activityTicketImage.setActivityTicket(activityTicket);
        activityTicketImage.setBucketKey(bucketKey);
        activityTicketImageDao.save(activityTicketImage);

        bucketService.uploadImage(bucketKey, activityTicketImageDto.getName(), activityTicketImageDto.getMultipartFile());
        return activityTicketImage.getId();
    }

    @Override
    @Transactional
    public void updateRepImage(Integer imageId) throws ActivityTicketImageNotFoundException {
        ActivityTicketImage newRepImage = findImageByImageId(imageId);
        List<ActivityTicketImage> repImageList = activityTicketImageDao.findAll(ActivityTicketSpec.findImageByIdAndRepImage(newRepImage.getActivityTicketId(),
                                                                                                          true));
        for (ActivityTicketImage repImage : repImageList) {
            if (newRepImage != repImage) repImage.setRepImage(false);
        }
        newRepImage.setRepImage(true);
    }

    @Override
    @Transactional
    public void updateImageOrder(List<Integer> imageIdList) {
        List<ActivityTicketImage> activityTicketImageList = activityTicketImageDao.findAll(ActivityTicketSpec.findImageByIds(imageIdList));
        HashMap<Integer, ActivityTicketImage> activityTicketImageHashMap = activityTicketImageList.stream()
                                                                                                  .collect(Collectors
                                                                                                                   .toMap(ActivityTicketImage::getId,
                                                                                                          activityTicketImage -> activityTicketImage,
                                                                                                                          (oKey, nKey) -> oKey,
                                                                                                                          HashMap::new));
        int order = 0;
        for (Integer imageId : imageIdList) {
            ActivityTicketImage activityTicketImage = activityTicketImageHashMap.get(imageId);
            if (activityTicketImage != null) {
                activityTicketImage.setOrder(order);
                order++;
            }
        }
        activityTicketImageDao.saveAll(activityTicketImageList);
    }


    @Override
    @Transactional
    public void deleteImage(Integer activityTicketImageId)
    throws ActivityTicketImageNotFoundException, RepImageNotDeletableException {
        ActivityTicketImage activityTicketImage = findImageByImageId(activityTicketImageId);
        if (activityTicketImage.isRepImage())
            throw new RepImageNotDeletableException();
        bucketService.deleteImage(activityTicketImage.getBucketKey());
        activityTicketImageDao.delete(activityTicketImage);
    }





//  ========================================== INFO ===========================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityTicketInfo findInfoEntityByInfoId(Integer activityTicketInfoId)
    throws ActivityTicketInfoNotFoundException {
        return activityTicketInfoDao.findById(activityTicketInfoId).orElseThrow(ActivityTicketInfoNotFoundException::new);
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public ActivityTicketInfoDto saveInfo(ActivityTicketInfoDto activityTicketInfoDto)
    throws ActivityTicketNotFoundException, ActivityInfoNotFoundException, SupportLanguageNotFoundException,
           ActivityTicketInfoAlreadyExistsException, ActivityTicketInfoNotEditableException,
           ActivityTicketInfoNotFoundException {

        ActivityTicketInfo activityTicketInfo;

        if (activityTicketInfoDto.getId() == null) activityTicketInfo = createInfo(null, activityTicketInfoDto);
        else activityTicketInfo = updateInfo(activityTicketInfoDto);
        activityTicketInfoDao.save(activityTicketInfo);
        return modelMapper.map(activityTicketInfo, ActivityTicketInfoDto.class);
    }

    private boolean infoExistsByIdAndSupportLanguageId(Integer activityTicketId, Integer supportLanguageId) {
        long count = activityTicketInfoDao.count(ActivityTicketSpec.findInfoByIdAndSupportLanguageId(activityTicketId,
                                                                                                     supportLanguageId));
        return count > 0;
    }

    private ActivityTicketInfo createInfo(ActivityTicket activityTicket, ActivityTicketInfoDto activityTicketInfoDto)
    throws ActivityTicketNotFoundException, SupportLanguageNotFoundException, ActivityTicketInfoAlreadyExistsException,
           ActivityInfoNotFoundException {

        if (activityTicket == null)
            activityTicket = findEntityById(activityTicketInfoDto.getActivityTicketId());

        Integer supportLanguageId = activityTicketInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(activityTicket.getId(), supportLanguageId))
            throw new ActivityTicketInfoAlreadyExistsException(activityTicket.getName(), supportLanguage.getName());

        ActivityInfo activityInfo =
                activityEntityService.findInfoEntityByIdAndSupportLanguageId(activityTicket.getActivityId(),
                                                                             supportLanguageId);

        ActivityTicketInfo activityTicketInfo = modelMapper.map(activityTicketInfoDto, ActivityTicketInfo.class);
        activityTicketInfo.setForeignEntities(activityInfo, activityTicket, supportLanguage);
        activityTicketInfo.denormalize(activityTicket);

        return activityTicketInfo;
    }


    private ActivityTicketInfo updateInfo(ActivityTicketInfoDto activityTicketInfoDto)
    throws ActivityTicketInfoNotFoundException, ActivityTicketInfoNotEditableException {

        ActivityTicketInfo activityTicketInfo = findInfoEntityByInfoId(activityTicketInfoDto.getId());

        if (activityTicketInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new ActivityTicketInfoNotEditableException();

        activityTicketInfo.clone(activityTicketInfoDto);
        activityTicketInfo.setUpdatedAt(new Date());

        return activityTicketInfo;
    }

    private void updateTicketInfos(ActivityTicket activityTicket) throws ActivityTicketInfoNotFoundException {

        List<ActivityTicketInfo> activityTicketInfoList = activityTicket.getActivityTicketInfoList();
        for (ActivityTicketInfo activityTicketInfo : activityTicketInfoList)
            activityTicketInfo.denormalize(activityTicket);

        ActivityTicketInfo activityTicketInfoInKorean =
                activityTicketInfoList.stream().filter(t -> t.getSupportLanguageId()
                                                             .equals(SupportLanguageEnum.Korean.getId()))
                                      .findFirst()
                                      .orElseThrow(ActivityTicketInfoNotFoundException::new);

        activityTicketInfoInKorean.clone(activityTicket);
    }

    @Override
    @Transactional
    public void deleteInfo(Integer activityTicketInfoId)
    throws ActivityTicketInfoNotFoundException, ActivityTicketInfoNotDeletableException {
        ActivityTicketInfo activityTicketInfo = findInfoEntityByInfoId(activityTicketInfoId);
        if (activityTicketInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new ActivityTicketInfoNotDeletableException();
        activityTicketInfoDao.delete(activityTicketInfo);
    }

}

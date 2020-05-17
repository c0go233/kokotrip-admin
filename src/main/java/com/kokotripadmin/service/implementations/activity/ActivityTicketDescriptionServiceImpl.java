package com.kokotripadmin.service.implementations.activity;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.ActivityTicketDescriptionDao;
import com.kokotripadmin.dao.interfaces.ActivityTicketDescriptionInfoDao;
import com.kokotripadmin.dto.activity.ActivityTicketDescriptionDto;
import com.kokotripadmin.dto.activity.ActivityTicketDescriptionInfoDto;
import com.kokotripadmin.entity.activity.ActivityTicket;
import com.kokotripadmin.entity.activity.ActivityTicketDescription;
import com.kokotripadmin.entity.activity.ActivityTicketDescriptionInfo;
import com.kokotripadmin.entity.activity.ActivityTicketInfo;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.exception.activity.ticket.*;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.service.entityinterfaces.ActivityTicketEntityService;
import com.kokotripadmin.service.entityinterfaces.SupportLanguageEntityService;
import com.kokotripadmin.service.interfaces.activity.ActivityTicketDescriptionService;
import com.kokotripadmin.spec.ActivityTicketDescriptionSpec;
import com.kokotripadmin.util.Convert;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ActivityTicketDescriptionServiceImpl implements ActivityTicketDescriptionService {

    private final ModelMapper                      modelMapper;
    private final Convert                          convert;
    private final ActivityTicketDescriptionDao     activityTicketDescriptionDao;
    private final ActivityTicketDescriptionInfoDao activityTicketDescriptionInfoDao;

    private final ActivityTicketEntityService  activityTicketEntityService;
    private final SupportLanguageEntityService supportLanguageEntityService;

    public ActivityTicketDescriptionServiceImpl(ModelMapper modelMapper, Convert convert,
                                                ActivityTicketDescriptionDao activityTicketDescriptionDao,
                                                ActivityTicketDescriptionInfoDao activityTicketDescriptionInfoDao,
                                                ActivityTicketEntityService activityTicketEntityService,
                                                SupportLanguageEntityService supportLanguageEntityService) {
        this.modelMapper = modelMapper;
        this.convert = convert;
        this.activityTicketDescriptionDao = activityTicketDescriptionDao;
        this.activityTicketDescriptionInfoDao = activityTicketDescriptionInfoDao;
        this.activityTicketEntityService = activityTicketEntityService;
        this.supportLanguageEntityService = supportLanguageEntityService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityTicketDescriptionDto findById(Integer activityTicketDescriptionId)
    throws ActivityTicketDescriptionNotFoundException {

        ActivityTicketDescription activityTicketDescription = findEntityById(activityTicketDescriptionId);
        return modelMapper.map(activityTicketDescription, ActivityTicketDescriptionDto.class);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityTicketDescription findEntityById(Integer activityTicketDescriptionId)
    throws ActivityTicketDescriptionNotFoundException {
        return activityTicketDescriptionDao.findById(activityTicketDescriptionId)
                                           .orElseThrow(ActivityTicketDescriptionNotFoundException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityTicketDescriptionDto findByIdInDetail(Integer activityTicketDescriptionId)
    throws ActivityTicketDescriptionNotFoundException {
        ActivityTicketDescription activityTicketDescription = findEntityById(activityTicketDescriptionId);
        return convert.activityTicketDescriptionToDtoInDetail(activityTicketDescription);
    }

    @Override
    @Transactional
    public Integer save(ActivityTicketDescriptionDto activityTicketDescriptionDto)
    throws ActivityTicketDescriptionNotFoundException,
           ActivityTicketDescriptionAlreadyExistsException, SupportLanguageNotFoundException,
           ActivityTicketDescriptionInfoAlreadyExistsException, ActivityTicketDescriptionInfoNotFoundException,
           ActivityTicketNotFoundException, ActivityTicketInfoNotFoundException {

        ActivityTicketDescription activityTicketDescription;
        if (activityTicketDescriptionDto.getId() == null) activityTicketDescription = create(activityTicketDescriptionDto);
        else activityTicketDescription = update(activityTicketDescriptionDto);
        activityTicketDescriptionDao.save(activityTicketDescription);
        return activityTicketDescription.getId();
    }

    private boolean existsByActivityTicketIdAndName(Integer activityTicketId, String name) {
        long count = activityTicketDescriptionDao.count(ActivityTicketDescriptionSpec
                                                                .findByActivityTicketIdAndName(activityTicketId, name));
        return count > 0;
    }

    private ActivityTicketDescription create(ActivityTicketDescriptionDto activityTicketDescriptionDto)
    throws SupportLanguageNotFoundException, ActivityTicketDescriptionInfoAlreadyExistsException,
           ActivityTicketDescriptionAlreadyExistsException, ActivityTicketDescriptionNotFoundException,
           ActivityTicketNotFoundException, ActivityTicketInfoNotFoundException {

        ActivityTicket activityTicket = activityTicketEntityService.findEntityById(activityTicketDescriptionDto.getActivityTicketId());

        if (existsByActivityTicketIdAndName(activityTicket.getId(), activityTicketDescriptionDto.getName()))
            throw new ActivityTicketDescriptionAlreadyExistsException(activityTicketDescriptionDto.getName());

        ActivityTicketDescription activityTicketDescription = modelMapper.map(activityTicketDescriptionDto, ActivityTicketDescription.class);
        activityTicketDescription.setActivityTicket(activityTicket);

        ActivityTicketDescriptionInfoDto activityTicketDescriptionInfoDto =
                modelMapper.map(activityTicketDescriptionDto, ActivityTicketDescriptionInfoDto.class);
        activityTicketDescriptionInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());
        ActivityTicketDescriptionInfo activityTicketDescriptionInfo = createInfo(activityTicketDescription,
                                                                                 activityTicketDescriptionInfoDto);

        activityTicketDescription.getActivityTicketDescriptionInfoList().add(activityTicketDescriptionInfo);

        return activityTicketDescription;
    }

    private ActivityTicketDescription update(ActivityTicketDescriptionDto activityTicketDescriptionDto)
    throws ActivityTicketDescriptionNotFoundException, ActivityTicketDescriptionAlreadyExistsException,
           ActivityTicketDescriptionInfoNotFoundException {

        ActivityTicketDescription activityTicketDescription = findEntityById(activityTicketDescriptionDto.getId());

        if (!activityTicketDescription.getName().equals(activityTicketDescriptionDto.getName())
            && existsByActivityTicketIdAndName(activityTicketDescription.getActivityTicketId(), activityTicketDescriptionDto.getName()))
            throw new ActivityTicketDescriptionAlreadyExistsException(activityTicketDescriptionDto.getName());

        activityTicketDescription.clone(activityTicketDescriptionDto);
        updateDescriptionInfos(activityTicketDescription);

        return activityTicketDescription;
    }

    @Override
    @Transactional
    public Integer delete(Integer activityTicketDescriptionId) throws ActivityTicketDescriptionNotFoundException {
        ActivityTicketDescription activityTicketDescription = findEntityById(activityTicketDescriptionId);
        Integer activityTicketId = activityTicketDescription.getActivityTicketId();
        activityTicketDescriptionDao.delete(activityTicketDescription);
        return activityTicketId;
    }


//  ============================================ INFO ====================================================  //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ActivityTicketDescriptionInfo findInfoEntityByInfoId(Integer activityTicketDescriptionInfoId)
    throws ActivityTicketDescriptionInfoNotFoundException {
        return activityTicketDescriptionInfoDao.findById(activityTicketDescriptionInfoId)
                                               .orElseThrow(ActivityTicketDescriptionInfoNotFoundException::new);
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public ActivityTicketDescriptionInfoDto saveInfo(ActivityTicketDescriptionInfoDto activityTicketDescriptionInfoDto)
    throws SupportLanguageNotFoundException, ActivityTicketDescriptionInfoAlreadyExistsException, ActivityTicketDescriptionNotFoundException,
           ActivityTicketDescriptionInfoNotEditableException, ActivityTicketDescriptionInfoNotFoundException,
           ActivityTicketInfoNotFoundException {

        ActivityTicketDescriptionInfo activityTicketDescriptionInfo;
        if (activityTicketDescriptionInfoDto.getId() == null)
            activityTicketDescriptionInfo = createInfo(null, activityTicketDescriptionInfoDto);
        else activityTicketDescriptionInfo = updateInfo(activityTicketDescriptionInfoDto);

        activityTicketDescriptionInfoDao.save(activityTicketDescriptionInfo);
        return modelMapper.map(activityTicketDescriptionInfo, ActivityTicketDescriptionInfoDto.class);
    }

    private ActivityTicketDescriptionInfo updateInfo(ActivityTicketDescriptionInfoDto activityTicketDescriptionInfoDto)
    throws ActivityTicketDescriptionInfoNotFoundException, ActivityTicketDescriptionInfoNotEditableException {

        ActivityTicketDescriptionInfo activityTicketDescriptionInfo = findInfoEntityByInfoId(activityTicketDescriptionInfoDto.getId());

        if (activityTicketDescriptionInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new ActivityTicketDescriptionInfoNotEditableException();

        activityTicketDescriptionInfo.clone(activityTicketDescriptionInfoDto);

        return activityTicketDescriptionInfo;
    }

    private ActivityTicketDescriptionInfo createInfo(ActivityTicketDescription activityTicketDescription,
                                                     ActivityTicketDescriptionInfoDto activityTicketDescriptionInfoDto)
    throws SupportLanguageNotFoundException, ActivityTicketDescriptionInfoAlreadyExistsException,
           ActivityTicketDescriptionNotFoundException, ActivityTicketInfoNotFoundException {

        if (activityTicketDescription == null)
            activityTicketDescription = findEntityById(activityTicketDescriptionInfoDto.getActivityTicketDescriptionId());

        Integer supportLanguageId = activityTicketDescriptionInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(activityTicketDescription.getId(), supportLanguageId))
            throw new ActivityTicketDescriptionInfoAlreadyExistsException(activityTicketDescription.getName(),
                                                                          supportLanguage.getName());

        ActivityTicketInfo activityTicketInfo =
                activityTicketEntityService.findInfoEntityByIdAndSupportLanguageId(activityTicketDescription.getActivityTicketId(),
                                                                                   supportLanguageId);

        ActivityTicketDescriptionInfo activityTicketDescriptionInfo = modelMapper.map(activityTicketDescriptionInfoDto,
                                                                                      ActivityTicketDescriptionInfo.class);
        activityTicketDescriptionInfo.setForeignEntities(activityTicketInfo, activityTicketDescription, supportLanguage);
        return activityTicketDescriptionInfo;

    }


    private void updateDescriptionInfos(ActivityTicketDescription activityTicketDescription)
    throws ActivityTicketDescriptionInfoNotFoundException {

        List<ActivityTicketDescriptionInfo> activityTicketDescriptionInfoList =
                activityTicketDescription.getActivityTicketDescriptionInfoList();

        for (ActivityTicketDescriptionInfo activityTicketDescriptionInfo : activityTicketDescriptionInfoList)
            activityTicketDescriptionInfo.denormalize(activityTicketDescription);

        ActivityTicketDescriptionInfo activityTicketDescriptionInfoInKorean =
                activityTicketDescriptionInfoList.stream()
                                                 .filter(t -> t.getSupportLanguageId()
                                                               .equals(SupportLanguageEnum.Korean.getId()))
                                                 .findFirst()
                                                 .orElseThrow(ActivityTicketDescriptionInfoNotFoundException::new);
        activityTicketDescriptionInfoInKorean.clone(activityTicketDescription);

    }

    private boolean infoExistsByIdAndSupportLanguageId(Integer activityTicketDescriptionId, Integer supportLanguageId) {
        long count = activityTicketDescriptionInfoDao
                .count(ActivityTicketDescriptionSpec.findInfoByIdAndSupportLanguageId(activityTicketDescriptionId,
                                                                                      supportLanguageId));
        return count > 0;
    }


    @Override
    @Transactional
    public void deleteInfo(Integer activityTicketDescriptionInfoId)
    throws ActivityTicketDescriptionInfoNotFoundException, ActivityTicketDescriptionInfoNotDeletableException {
        ActivityTicketDescriptionInfo activityTicketDescriptionInfo = findInfoEntityByInfoId(activityTicketDescriptionInfoId);
        if (activityTicketDescriptionInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new ActivityTicketDescriptionInfoNotDeletableException();
        activityTicketDescriptionInfoDao.delete(activityTicketDescriptionInfo);
    }

}

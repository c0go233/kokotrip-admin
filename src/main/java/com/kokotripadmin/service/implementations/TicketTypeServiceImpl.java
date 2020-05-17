package com.kokotripadmin.service.implementations;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.ticket.TicketTypeDao;
import com.kokotripadmin.dao.interfaces.ticket.TicketTypeInfoDao;
import com.kokotripadmin.dto.ticket.TicketTypeDto;
import com.kokotripadmin.dto.ticket.TicketTypeInfoDto;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.ticket.TicketType;
import com.kokotripadmin.entity.ticket.TicketTypeInfo;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.ticket.*;
import com.kokotripadmin.service.entityinterfaces.SupportLanguageEntityService;
import com.kokotripadmin.service.entityinterfaces.TicketTypeEntityService;
import com.kokotripadmin.service.interfaces.TicketTypeService;
import com.kokotripadmin.spec.TicketTypeSpec;
import com.kokotripadmin.util.Convert;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketTypeServiceImpl implements TicketTypeService, TicketTypeEntityService {

    private final TicketTypeDao ticketTypeDao;
    private final TicketTypeInfoDao ticketTypeInfoDao;

    private final SupportLanguageEntityService supportLanguageEntityService;

    private final ModelMapper   modelMapper;
    private final Convert convert;

    @Autowired
    public TicketTypeServiceImpl(TicketTypeDao ticketTypeDao,
                                 TicketTypeInfoDao ticketTypeInfoDao,
                                 SupportLanguageEntityService supportLanguageEntityService,
                                 ModelMapper modelMapper, Convert convert) {
        this.ticketTypeDao = ticketTypeDao;
        this.ticketTypeInfoDao = ticketTypeInfoDao;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.modelMapper = modelMapper;
        this.convert = convert;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TicketType findEntityById(Integer ticketTypeId) throws TicketTypeNotFoundException {
        TicketType ticketType = ticketTypeDao.findById(ticketTypeId).orElseThrow(() -> new TicketTypeNotFoundException());
        return ticketType;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<TicketTypeDto> findAll() {
        List<TicketType> ticketTypeList =  ticketTypeDao.findAll();
        return modelMapper.map(ticketTypeList, new TypeToken<List<TicketTypeDto>>() {}.getType());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TicketTypeDto findByIdInDetail(Integer ticketTypeId) throws TicketTypeNotFoundException {
        TicketType ticketType = findEntityById(ticketTypeId);
        return convert.ticketTypeToDtoInDetail(ticketType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TicketTypeDto findById(Integer ticketTypeId) throws TicketTypeNotFoundException {
        TicketType ticketType = findEntityById(ticketTypeId);
        return modelMapper.map(ticketType, TicketTypeDto.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public LinkedHashMap<Integer, String> findAllAsLinkedHashMap() {
        List<TicketType> ticketTypeList = ticketTypeDao.findAllByEnabled(true);
        LinkedHashMap<Integer, String> ticketTypeLinkedHashMap
                = ticketTypeList.stream().collect(Collectors.toMap(TicketType::getId, TicketType::getName,
                                                                   (oKey, nKey) -> oKey, LinkedHashMap::new));
        return ticketTypeLinkedHashMap;
    }


    private boolean existsByName(String ticketTypeName) {
        long count = ticketTypeDao.count(TicketTypeSpec.findByName(ticketTypeName));
        return count > 0;
    }

    @Override
    @Transactional
    public Integer save(TicketTypeDto ticketTypeDto)
    throws TicketTypeNameDuplicateException, SupportLanguageNotFoundException,
           TicketTypeNotFoundException, TicketTypeInfoAlreadyExistsException {

        TicketType ticketType;
        if (ticketTypeDto.getId() == null) ticketType = create(ticketTypeDto);
        else ticketType = update(ticketTypeDto);
        ticketTypeDao.save(ticketType);

        return ticketType.getId();
    }

    private TicketType create(TicketTypeDto ticketTypeDto)
    throws TicketTypeNameDuplicateException, SupportLanguageNotFoundException,
           TicketTypeNotFoundException, TicketTypeInfoAlreadyExistsException {

        String ticketTypeName = ticketTypeDto.getName();
        if (existsByName(ticketTypeName)) throw new TicketTypeNameDuplicateException(ticketTypeName);

        TicketType ticketType = modelMapper.map(ticketTypeDto, TicketType.class);

        TicketTypeInfoDto ticketTypeInfoDto = modelMapper.map(ticketTypeDto, TicketTypeInfoDto.class);
        ticketTypeInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());

        TicketTypeInfo ticketTypeInfo = createInfo(ticketType, ticketTypeInfoDto);
        ticketType.getTicketTypeInfoList().add(ticketTypeInfo);

        return ticketType;
    }

    private TicketType update(TicketTypeDto ticketTypeDto) throws TicketTypeNotFoundException, TicketTypeNameDuplicateException {

        TicketType ticketType = findEntityById(ticketTypeDto.getId());

        String newTicketTypeName = ticketTypeDto.getName();
        if (!ticketType.getName().equals(newTicketTypeName) && existsByName(newTicketTypeName))
            throw new TicketTypeNameDuplicateException(newTicketTypeName);

        ticketType.clone(ticketTypeDto);
        ticketType.updateInfos();
        ticketType.setUpdatedAt(new Date());

        return ticketType;
    }

    @Override
    @Transactional
    public void delete(Integer ticketTypeId) throws TicketTypeNotFoundException {
        TicketType ticketType = findEntityById(ticketTypeId);
        ticketTypeDao.delete(ticketType);
    }



//  ====================================================== TICKET TYPE INFO ====================================================   //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TicketTypeInfo findInfoEntityByInfoId(Integer ticketTypeInfoId) throws TicketTypeInfoNotFoundException {
        TicketTypeInfo ticketTypeInfo = ticketTypeInfoDao.findById(ticketTypeInfoId).orElseThrow(() -> new TicketTypeInfoNotFoundException());
        return ticketTypeInfo;
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public TicketTypeInfoDto saveInfo(TicketTypeInfoDto ticketTypeInfoDto)
    throws SupportLanguageNotFoundException, TicketTypeNotFoundException, TicketTypeInfoAlreadyExistsException,
           TicketTypeInfoNotEditableException, TicketTypeInfoNotFoundException {

        TicketTypeInfo ticketTypeInfo;
        if (ticketTypeInfoDto.getId() == null) ticketTypeInfo = createInfo(null, ticketTypeInfoDto);
        else ticketTypeInfo = updateInfo(ticketTypeInfoDto);

        ticketTypeInfoDao.save(ticketTypeInfo);
        return modelMapper.map(ticketTypeInfo, TicketTypeInfoDto.class);
    }

    @Override
    @Transactional
    public void deleteInfo(Integer ticketTypeInfoId) throws TicketTypeInfoNotFoundException, TicketTypeInfoNotDeletableException {
        TicketTypeInfo ticketTypeInfo = findInfoEntityByInfoId(ticketTypeInfoId);
        if (ticketTypeInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new TicketTypeInfoNotDeletableException();
        ticketTypeInfoDao.delete(ticketTypeInfo);
    }


    private boolean infoExistsByIdAndSupportLanguageId(Integer ticketTypeId, Integer supportLanguageId) {
        long count = ticketTypeInfoDao.count(TicketTypeSpec.findInfoByIdAndSupportLanguageId(ticketTypeId, supportLanguageId));
        return count > 0;
    }

    private TicketTypeInfo createInfo(TicketType ticketType, TicketTypeInfoDto ticketTypeInfoDto)
    throws TicketTypeNotFoundException, SupportLanguageNotFoundException, TicketTypeInfoAlreadyExistsException {


        if (ticketType == null) ticketType = findEntityById(ticketTypeInfoDto.getTicketTypeId());

        Integer supportLanguageId = ticketTypeInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(ticketType.getId(), supportLanguageId))
            throw new TicketTypeInfoAlreadyExistsException(ticketType.getName(), supportLanguage.getName());

        TicketTypeInfo ticketTypeInfo = modelMapper.map(ticketTypeInfoDto, TicketTypeInfo.class);
        ticketTypeInfo.setForeignEntities(ticketType, supportLanguage);
        ticketTypeInfo.denormalize(ticketType);

        return ticketTypeInfo;
    }

    private TicketTypeInfo updateInfo(TicketTypeInfoDto ticketTypeInfoDto)
    throws TicketTypeInfoNotFoundException, TicketTypeInfoNotEditableException {

        TicketTypeInfo ticketTypeInfo = findInfoEntityByInfoId(ticketTypeInfoDto.getId());

        if (ticketTypeInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new TicketTypeInfoNotEditableException();

        ticketTypeInfo.clone(ticketTypeInfoDto);
        ticketTypeInfo.setUpdatedAt(new Date());

        return ticketTypeInfo;
    }


}

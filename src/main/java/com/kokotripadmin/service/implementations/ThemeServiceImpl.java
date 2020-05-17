package com.kokotripadmin.service.implementations;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.tag.ThemeDao;
import com.kokotripadmin.dao.interfaces.tag.ThemeInfoDao;
import com.kokotripadmin.datatablesdao.ThemeDataTablesDao;
import com.kokotripadmin.dto.theme.ThemeDto;
import com.kokotripadmin.dto.theme.ThemeInfoDto;
import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.tag.Theme;
import com.kokotripadmin.entity.tag.ThemeInfo;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.theme.*;
import com.kokotripadmin.service.entityinterfaces.SupportLanguageEntityService;
import com.kokotripadmin.service.entityinterfaces.ThemeEntityService;
import com.kokotripadmin.service.interfaces.ThemeService;
import com.kokotripadmin.spec.CitySpec;
import com.kokotripadmin.spec.ThemeSpec;
import com.kokotripadmin.util.Convert;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeServiceImpl implements ThemeService, ThemeEntityService {

    private final ModelMapper modelMapper;
    private final ThemeDao themeDao;
    private final ThemeInfoDao themeInfoDao;
    private final ThemeDataTablesDao themeDataTablesDao;

    private final Convert convert;



    private final SupportLanguageEntityService supportLanguageEntityService;

    @Autowired
    public ThemeServiceImpl(ModelMapper modelMapper,
                            ThemeDao themeDao,
                            ThemeInfoDao themeInfoDao,
                            ThemeDataTablesDao themeDataTablesDao,
                            Convert convert,
                            SupportLanguageEntityService supportLanguageEntityService) {
        this.modelMapper = modelMapper;
        this.themeDao = themeDao;
        this.themeInfoDao = themeInfoDao;
        this.convert = convert;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.themeDataTablesDao = themeDataTablesDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public Theme findEntityById(Integer themeId) throws ThemeNotFoundException {
        Theme theme = themeDao.findById(themeId).orElseThrow(() -> new ThemeNotFoundException());
        return theme;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ThemeDto findById(Integer themeId) throws ThemeNotFoundException {
        Theme theme = findEntityById(themeId);
        return convert.themeToDto(theme);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public DataTablesOutput<ThemeDto> findAllByPagination(DataTablesInput dataTablesInput) {
        return themeDataTablesDao.findAll(dataTablesInput, convert.themeToDto());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ThemeDto findByIdInDetail(Integer themeId) throws ThemeNotFoundException {
        Theme theme = findEntityById(themeId);
        return convert.themeToDtoInDetail(theme);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public LinkedHashMap<Integer, String> findAllAsLinkedHashMap() {

        List<Theme> themeList = themeDao.findAll(ThemeSpec.findByEnabled(true));
        LinkedHashMap<Integer, String> themeLinkedHashMap
                = themeList.stream().collect(Collectors.toMap(Theme::getId, Theme::getName,
                                                             (oKey, nKey) -> oKey, LinkedHashMap::new));
        return themeLinkedHashMap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public String findNameById(Integer themeId) throws ThemeNotFoundException {
        if (themeId == null) return null;
        return findEntityById(themeId).getName();
    }

    @Override
    @Transactional
    public Integer save(ThemeDto themeDto)
    throws ThemeNameDuplicateException, SupportLanguageNotFoundException,
            ThemeNotFoundException, ThemeInfoAlreadyExistsException {

        Theme theme;
        if (themeDto.getId() == null) theme = create(themeDto);
        else theme = update(themeDto);
        themeDao.save(theme);

        return theme.getId();
    }

    private boolean existsByName(String themeName) {
        long count = themeDao.count(ThemeSpec.findByName(themeName));
        return count > 0;
    }

    private Theme create(ThemeDto themeDto) throws ThemeNameDuplicateException, SupportLanguageNotFoundException,
            ThemeNotFoundException, ThemeInfoAlreadyExistsException {

        String themeName = themeDto.getName();
        if (existsByName(themeName)) throw new ThemeNameDuplicateException(themeName);

        Theme theme = modelMapper.map(themeDto, Theme.class);

        ThemeInfoDto themeInfoDto = modelMapper.map(themeDto, ThemeInfoDto.class);
        themeInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());

        ThemeInfo themeInfo = createInfo(theme, themeInfoDto);
        theme.getThemeInfoList().add(themeInfo);

        return theme;
    }

    private Theme update(ThemeDto themeDto) throws ThemeNotFoundException, ThemeNameDuplicateException {

        Theme theme = findEntityById(themeDto.getId());

        String newThemeName = themeDto.getName();
        if (!theme.getName().equals(newThemeName) && existsByName(newThemeName))
            throw new ThemeNameDuplicateException(newThemeName);

        theme.clone(themeDto);
        theme.updateInfos();
        theme.setUpdatedAt(new Date());

        return theme;
    }

    @Override
    @Transactional
    public void delete(Integer themeId) throws ThemeNotFoundException {
        Theme theme = findEntityById(themeId);
        themeDao.delete(theme);
    }



//  ====================================================== THEME INFO ====================================================   //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public ThemeInfo findInfoEntityByInfoId(Integer themeInfoId) throws ThemeInfoNotFoundException {
        ThemeInfo themeInfo = themeInfoDao.findById(themeInfoId).orElseThrow(() -> new ThemeInfoNotFoundException());
        return themeInfo;
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public ThemeInfoDto saveInfo(ThemeInfoDto themeInfoDto)
    throws SupportLanguageNotFoundException, ThemeNotFoundException, ThemeInfoAlreadyExistsException,
            ThemeInfoNotEditableException, ThemeInfoNotFoundException {

        ThemeInfo themeInfo;
        if (themeInfoDto.getId() == null) themeInfo = createInfo(null, themeInfoDto);
        else themeInfo = updateInfo(themeInfoDto);
        themeInfoDao.save(themeInfo);
        return modelMapper.map(themeInfo, ThemeInfoDto.class);
    }

    @Override
    @Transactional
    public void deleteInfo(Integer themeInfoId) throws ThemeInfoNotFoundException, ThemeInfoNotDeletableException {
        ThemeInfo themeInfo = findInfoEntityByInfoId(themeInfoId);
        if (themeInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new ThemeInfoNotDeletableException();
        themeInfoDao.delete(themeInfo);
    }

    private boolean infoExistsByIdAndSupportLanguageId(Integer themeId, Integer supportLanguageId) {
        long count = themeInfoDao.count(ThemeSpec.findInfoByIdAndSupportLanguageId(themeId, supportLanguageId));
        return count > 0;
    }

    private ThemeInfo createInfo(Theme theme, ThemeInfoDto themeInfoDto)
    throws ThemeNotFoundException, SupportLanguageNotFoundException, ThemeInfoAlreadyExistsException {


        if (theme == null) theme = findEntityById(themeInfoDto.getThemeId());

        Integer supportLanguageId = themeInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(theme.getId(), supportLanguageId))
            throw new ThemeInfoAlreadyExistsException(theme.getName(), supportLanguage.getName());

        ThemeInfo themeInfo = modelMapper.map(themeInfoDto, ThemeInfo.class);
        themeInfo.setForeignEntities(theme, supportLanguage);
        themeInfo.denormalize(theme);

        return themeInfo;
    }

    private ThemeInfo updateInfo(ThemeInfoDto themeInfoDto) throws ThemeInfoNotFoundException,
            ThemeInfoNotEditableException {

        ThemeInfo themeInfo = findInfoEntityByInfoId(themeInfoDto.getId());

        if (themeInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new ThemeInfoNotEditableException();

        themeInfo.clone(themeInfoDto);
        themeInfo.setUpdatedAt(new Date());
        return themeInfo;
    }




}

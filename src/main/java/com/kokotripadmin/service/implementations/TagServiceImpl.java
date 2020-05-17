package com.kokotripadmin.service.implementations;

import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dao.interfaces.tag.TagDao;
import com.kokotripadmin.dao.interfaces.tag.TagInfoDao;
import com.kokotripadmin.dao.interfaces.tourspot.TourSpotInfoDao;
import com.kokotripadmin.datatablesdao.TagDataTableDao;
import com.kokotripadmin.dto.tag.TagDto;
import com.kokotripadmin.dto.tag.TagInfoDto;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tag.TagInfo;
import com.kokotripadmin.entity.tag.Theme;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import com.kokotripadmin.exception.support_language.SupportLanguageNotFoundException;
import com.kokotripadmin.exception.tag.*;
import com.kokotripadmin.exception.theme.ThemeNotFoundException;
import com.kokotripadmin.service.entityinterfaces.*;
import com.kokotripadmin.service.interfaces.TagService;
import com.kokotripadmin.spec.TagSpec;
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

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService, TagEntityService {

    private final ModelMapper modelMapper;
    private final Convert convert;

    private final TagDao tagDao;
    private final TagInfoDao tagInfoDao;
    private final TagDataTableDao tagDataTableDao;
    private final TourSpotInfoDao tourSpotInfoDao;

    private final ThemeEntityService themeEntityService;
    private final SupportLanguageEntityService supportLanguageEntityService;
    private final CityEntityService cityEntityService;
    private final RegionEntityService regionEntityService;


    @Autowired
    public TagServiceImpl(TagDao tagDao,
                          TagInfoDao tagInfoDao,
                          ModelMapper modelMapper,
                          TourSpotInfoDao tourSpotInfoDao,
                          ThemeEntityService themeEntityService,
                          SupportLanguageEntityService supportLanguageEntityService,
                          CityEntityService cityEntityService, Convert convert,
                          TagDataTableDao tagDataTableDao,
                          RegionEntityService regionEntityService) {
        this.tagDao = tagDao;
        this.tagInfoDao = tagInfoDao;
        this.modelMapper = modelMapper;
        this.tourSpotInfoDao = tourSpotInfoDao;
        this.themeEntityService = themeEntityService;
        this.supportLanguageEntityService = supportLanguageEntityService;
        this.cityEntityService = cityEntityService;
        this.convert = convert;
        this.tagDataTableDao = tagDataTableDao;
        this.regionEntityService = regionEntityService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public Tag findEntityById(Integer tagId) throws TagNotFoundException {

        Tag tag = tagDao.findById(tagId).orElseThrow(() -> new TagNotFoundException());
        return tag;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TagDto findByIdInDetail(Integer tagId) throws TagNotFoundException {

        Tag tag = findEntityById(tagId);
        return convert.tagToDtoInDetail(tag);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TagDto findById(Integer tagId) throws TagNotFoundException {

        Tag tag = findEntityById(tagId);
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public LinkedHashMap<Integer, String> findAllAsLinkedHashMap() {

        List<Tag> tagList = tagDao.findAllByEnabledAndThemeEnabled(true, true);
        return tagList.stream().collect(Collectors.toMap(Tag::getId, Tag::getName, (oKey, nKey) -> oKey, LinkedHashMap::new));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public DataTablesOutput<TagDto> findAllByPagination(DataTablesInput dataTablesInput) {
        return tagDataTableDao.findAll(dataTablesInput, convert.tagToDto());
    }

    @Override
    @Transactional
    public void deleteInfo(Integer tagInfoId) throws TagInfoNotFoundException, TagInfoNotDeletableException {
        TagInfo tagInfo = findInfoEntityByInfoId(tagInfoId);
        if (tagInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new TagInfoNotDeletableException();
        tagInfoDao.delete(tagInfo);
    }

    @Override
    @Transactional
    public void delete(Integer tagId) throws TagNotFoundException {
        Tag tag = findEntityById(tagId);
        tagDao.delete(tag);
    }


    @Override
    @Transactional
    public Integer save(TagDto tagDto)
    throws ThemeNotFoundException, TagNotFoundException, SupportLanguageNotFoundException,
           TagInfoAlreadyExistsException, TagNameDuplicateException, TagInfoNotFoundException {

        Theme theme = themeEntityService.findEntityById(tagDto.getThemeId());

        Tag tag;
        if (tagDto.getId() == null) tag = create(theme, tagDto);
        else tag = update(theme, tagDto);
        tagDao.save(tag);

        return tag.getId();
    }

    private Tag update(Theme newTheme, TagDto tagDto)
    throws TagNotFoundException, TagNameDuplicateException, TagInfoNotFoundException {

        Tag tag = findEntityById(tagDto.getId());

        String newTagName = tagDto.getName();
        if (!tag.getName().equals(newTagName) && existsByName(newTagName))
            throw new TagNameDuplicateException(newTagName);

        if (tag.getTheme() != newTheme) {
            cityEntityService.updateThemeOfThemeTagRel(newTheme, tag);
            regionEntityService.updateThemeOfThemeTagRel(newTheme, tag);
        }

        tag.clone(tagDto);
        tag.setTheme(newTheme);
        updateInfos(tag);
        tag.setUpdatedAt(new Date());

        return tag;
    }

    private void updateInfos(Tag tag) throws TagInfoNotFoundException {

        List<TagInfo> tagInfoList = tag.getTagInfoList();
        for (TagInfo tagInfo : tagInfoList) tagInfo.denormalize(tag);
        TagInfo tagInfoInKorean = tagInfoList.stream()
                                             .filter(t -> t.getSupportLanguageId()
                                                           .equals(SupportLanguageEnum.Korean.getId()))
                                             .findFirst()
                                             .orElseThrow(TagInfoNotFoundException::new);

        if (!tagInfoInKorean.getName().equals(tag.getName())) {
            tagInfoInKorean.clone(tag);
            updateTagNameOfTourSpot(tagInfoInKorean);
            updateTagNameOfActivity(tagInfoInKorean);
        } else tagInfoInKorean.clone(tag);

        tagInfoInKorean.clone(tag);
    }

    private boolean existsByName(String tagName) {
        long count = tagDao.count(TagSpec.findByName(tagName));
        return count > 0;
    }

    private Tag create(Theme theme, TagDto tagDto)
    throws TagNameDuplicateException, SupportLanguageNotFoundException, TagNotFoundException,
           TagInfoAlreadyExistsException {

        String tagName = tagDto.getName();
        if (existsByName(tagName)) throw new TagNameDuplicateException(tagName);

        Tag tag = modelMapper.map(tagDto, Tag.class);
        tag.setTheme(theme);

        TagInfoDto tagInfoDto = modelMapper.map(tagDto, TagInfoDto.class);
        tagInfoDto.setSupportLanguageId(SupportLanguageEnum.Korean.getId());

        TagInfo tagInfo = createInfo(tag, tagInfoDto);
        tag.getTagInfoList().add(tagInfo);

        return tag;
    }


//  ==============================  TAG INFO  =============================================   //

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public TagInfo findInfoEntityByInfoId(Integer tagInfoId) throws TagInfoNotFoundException {
        TagInfo tagInfoEntity = tagInfoDao.findById(tagInfoId).orElseThrow(() -> new TagInfoNotFoundException());
        return tagInfoEntity;
    }

    @Override
    public TagInfo findInfoEntityByIdAndSupportLanguageId(Integer tagId, Integer supportLanguageId)
    throws TagInfoNotFoundException {
        TagInfo tagInfo = tagInfoDao.findOne(TagSpec.findInfoByIdAndSupportLanguageId(tagId, supportLanguageId))
                                    .orElseThrow(() -> new TagInfoNotFoundException());
        return tagInfo;
    }

    @Override
    public HashMap<Integer, TagInfo> findAllInfoByIdAsHashMap(Integer tagId) {
        List<TagInfo> tagInfoList = tagInfoDao.findAll(TagSpec.findInfoById(tagId));
        HashMap<Integer, TagInfo> tagInfoHashMap = new HashMap<>();
        for (TagInfo tagInfo : tagInfoList) {
            tagInfoHashMap.put(tagInfo.getSupportLanguageId(), tagInfo);
        }
        return tagInfoHashMap;
    }

    @Override
    @Transactional
    @SuppressWarnings("Duplicates")
    public TagInfoDto saveInfo(TagInfoDto tagInfoDto)
    throws SupportLanguageNotFoundException, TagNotFoundException, TagInfoAlreadyExistsException,
           TagInfoNotEditableException, TagInfoNotFoundException {

        TagInfo tagInfo;
        if (tagInfoDto.getId() == null) tagInfo = createInfo(null, tagInfoDto);
        else tagInfo = updateInfo(tagInfoDto);
        tagInfoDao.save(tagInfo);
        return modelMapper.map(tagInfo, TagInfoDto.class);
    }

    private boolean infoExistsByIdAndSupportLanguageId(Integer tagId, Integer supportLanguageId) {
        long count = tagInfoDao.count(TagSpec.findInfoByIdAndSupportLanguageId(tagId, supportLanguageId));
        return count > 0;
    }

    private TagInfo createInfo(Tag tag, TagInfoDto tagInfoDto)
    throws TagNotFoundException, SupportLanguageNotFoundException, TagInfoAlreadyExistsException {

        if (tag == null) tag = findEntityById(tagInfoDto.getTagId());

        Integer supportLanguageId = tagInfoDto.getSupportLanguageId();
        SupportLanguage supportLanguage = supportLanguageEntityService.findEntityById(supportLanguageId);

        if (infoExistsByIdAndSupportLanguageId(tag.getId(), supportLanguageId))
            throw new TagInfoAlreadyExistsException(tag.getName(), supportLanguage.getName());

        TagInfo tagInfo = modelMapper.map(tagInfoDto, TagInfo.class);
        tagInfo.setForeignEntities(tag, supportLanguage);
        tagInfo.denormalize(tag);

        return tagInfo;
    }

    private TagInfo updateInfo(TagInfoDto tagInfoDto)
    throws TagInfoNotFoundException, TagInfoNotEditableException {

        TagInfo tagInfo = findInfoEntityByInfoId(tagInfoDto.getId());

        if (tagInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
            throw new TagInfoNotEditableException();

        if (!tagInfo.getName().equals(tagInfoDto.getName())) {
            tagInfo.clone(tagInfoDto);
            updateTagNameOfTourSpot(tagInfo);
            updateTagNameOfActivity(tagInfo);
        } else tagInfo.clone(tagInfoDto);

        tagInfo.setUpdatedAt(new Date());
        return tagInfo;
    }

    private void updateTagNameOfTourSpot(TagInfo tagInfo) {
        List<TourSpotInfo> tourSpotInfoList =  tourSpotInfoDao.findAll(TourSpotSpec.findInfoByTagInfoId(tagInfo.getId()));
        for (TourSpotInfo tourSpotInfo : tourSpotInfoList) tourSpotInfo.setTagName(tagInfo.getName());
        tourSpotInfoDao.saveAll(tourSpotInfoList);
    }

    private void updateTagNameOfActivity(TagInfo tagInfo) {

    }




}























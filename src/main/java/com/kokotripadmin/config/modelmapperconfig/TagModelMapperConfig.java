package com.kokotripadmin.config.modelmapperconfig;

import com.kokotripadmin.dto.common.ThemeRelDto;
import com.kokotripadmin.dto.tag.TagDto;
import com.kokotripadmin.dto.tag.TagInfoDto;
import com.kokotripadmin.dto.theme.ThemeDto;
import com.kokotripadmin.dto.theme.ThemeInfoDto;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tag.TagInfo;
import com.kokotripadmin.entity.tag.ThemeInfo;
import com.kokotripadmin.viewmodel.common.ThemeRelVm;
import com.kokotripadmin.viewmodel.tag.TagVm;
import com.kokotripadmin.viewmodel.theme.ThemeVm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class TagModelMapperConfig {

    public static void tagToDto(ModelMapper modelMapper) {
        TypeMap<Tag, TagDto> typeMap = modelMapper.createTypeMap(Tag.class, TagDto.class);
        typeMap.addMapping(src -> src.getTheme().getName(), TagDto::setThemeName);
    }


    public static void tagInfoToDto(ModelMapper modelMapper) {
        TypeMap<TagInfo, TagInfoDto> typeMap = modelMapper.createTypeMap(TagInfo.class, TagInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), TagInfoDto::setSupportLanguageName);
    }


    public static void tagDtoToVm(ModelMapper modelMapper) {
        TypeMap<TagDto, TagVm> typeMap = modelMapper.createTypeMap(TagDto.class, TagVm.class);
        typeMap.addMapping(TagDto::getTagInfoDtoList, TagVm::setTagInfoVmList);
    }

}

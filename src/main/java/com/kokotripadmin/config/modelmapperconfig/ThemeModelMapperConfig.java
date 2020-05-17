package com.kokotripadmin.config.modelmapperconfig;

import com.kokotripadmin.dto.common.ThemeRelDto;
import com.kokotripadmin.dto.theme.ThemeDto;
import com.kokotripadmin.dto.theme.ThemeInfoDto;
import com.kokotripadmin.entity.tag.Theme;
import com.kokotripadmin.entity.tag.ThemeInfo;
import com.kokotripadmin.viewmodel.common.ThemeRelVm;
import com.kokotripadmin.viewmodel.theme.ThemeVm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class ThemeModelMapperConfig {


    public static void themeRelDtoToVm(ModelMapper modelMapper) {
        TypeMap<ThemeRelDto, ThemeRelVm> typeMap = modelMapper.createTypeMap(ThemeRelDto.class, ThemeRelVm.class);
        typeMap.addMapping(ThemeRelDto::getThemeTagRelDtoList, ThemeRelVm::setThemeTagRelVmList);
    }


    public static void themeInfoToDto(ModelMapper modelMapper) {
        TypeMap<ThemeInfo, ThemeInfoDto> typeMap = modelMapper.createTypeMap(ThemeInfo.class, ThemeInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), ThemeInfoDto::setSupportLanguageName);
    }

    public static void themeDtoToVm(ModelMapper modelMapper) {
        TypeMap<ThemeDto, ThemeVm> typeMap = modelMapper.createTypeMap(ThemeDto.class, ThemeVm.class);
        typeMap.addMapping(ThemeDto::getThemeInfoDtoList, ThemeVm::setThemeInfoVmList);
        typeMap.addMapping(ThemeDto::getTagDtoList, ThemeVm::setTagVmList);
    }

}

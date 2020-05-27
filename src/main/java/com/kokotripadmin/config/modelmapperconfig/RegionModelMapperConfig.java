package com.kokotripadmin.config.modelmapperconfig;


import com.kokotripadmin.dto.common.ThemeRelDto;
import com.kokotripadmin.dto.common.ThemeTagRelDto;
import com.kokotripadmin.dto.region.RegionDto;
import com.kokotripadmin.dto.region.RegionInfoDto;
import com.kokotripadmin.entity.region.Region;
import com.kokotripadmin.entity.region.RegionInfo;
import com.kokotripadmin.entity.region.RegionThemeRel;
import com.kokotripadmin.entity.region.RegionThemeTagRel;
import com.kokotripadmin.viewmodel.region.RegionVm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class RegionModelMapperConfig {

    public static void regionToDto(ModelMapper modelMapper) {
        TypeMap<Region, RegionDto> typeMap = modelMapper.createTypeMap(Region.class, RegionDto.class);
        typeMap.addMapping(src -> src.getCity().getName(), RegionDto::setCityName);
        typeMap.addMapping(src -> src.getCity().getStateId(), RegionDto::setStateId);
        typeMap.addMapping(src -> src.getCity().getState().getName(), RegionDto::setStateName);
    }


    public static void regionInfoToDto(ModelMapper modelMapper) {
        TypeMap<RegionInfo, RegionInfoDto> typeMap = modelMapper.createTypeMap(RegionInfo.class, RegionInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), RegionInfoDto::setSupportLanguageName);
    }

    public static void regionThemeRelToDto(ModelMapper modelMapper) {
        TypeMap<RegionThemeRel, ThemeRelDto> typeMap = modelMapper.createTypeMap(RegionThemeRel.class, ThemeRelDto.class);
        typeMap.addMapping(RegionThemeRel::getRegionThemeTagRelList, ThemeRelDto::setThemeTagRelDtoList);
        typeMap.addMapping(src -> src.getTheme().getName(), ThemeRelDto::setThemeName);
    }

    public static void regionThemeTagRelToDto(ModelMapper modelMapper) {
        TypeMap<RegionThemeTagRel, ThemeTagRelDto> typeMap = modelMapper.createTypeMap(RegionThemeTagRel.class, ThemeTagRelDto.class);
        typeMap.addMapping(src -> src.getTag().getName(), ThemeTagRelDto::setTagName);
    }

    @SuppressWarnings("Duplicates")
    public static void regionDtoToVm(ModelMapper modelMapper) {
        TypeMap<RegionDto, RegionVm> typeMap = modelMapper.createTypeMap(RegionDto.class, RegionVm.class);
        typeMap.addMapping(RegionDto::getRegionInfoDtoList, RegionVm::setRegionInfoVmList);
        typeMap.addMapping(RegionDto::getThemeRelDtoList, RegionVm::setThemeRelVmList);
        typeMap.addMapping(RegionDto::getRegionImageDtoList, RegionVm::setBaseImageVmList);
    }

}

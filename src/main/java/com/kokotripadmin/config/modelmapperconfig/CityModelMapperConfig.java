package com.kokotripadmin.config.modelmapperconfig;

import com.kokotripadmin.dto.city.CityDto;
import com.kokotripadmin.dto.city.CityInfoDto;
import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.common.ThemeRelDto;
import com.kokotripadmin.dto.common.ThemeTagRelDto;
import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.city.CityInfo;
import com.kokotripadmin.entity.city.CityThemeRel;
import com.kokotripadmin.entity.city.CityThemeTagRel;
import com.kokotripadmin.viewmodel.city.CityVm;
import org.modelmapper.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class CityModelMapperConfig {


    public static void cityToDto(ModelMapper modelMapper) {
        TypeMap<City, CityDto> typeMap = modelMapper.createTypeMap(City.class, CityDto.class);
        typeMap.addMapping(src -> src.getState().getName(), CityDto::setStateName);
        typeMap.addMapping(City::getCityImageList, CityDto::setBaseImageDtoList);
    }

    public static void cityInfoToDto(ModelMapper modelMapper) {
        TypeMap<CityInfo, CityInfoDto> typeMap = modelMapper.createTypeMap(CityInfo.class, CityInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), CityInfoDto::setSupportLanguageName);
    }


    public static void cityThemeRelToDto(ModelMapper modelMapper) {
        TypeMap<CityThemeRel, ThemeRelDto> typeMap = modelMapper.createTypeMap(CityThemeRel.class, ThemeRelDto.class);
        typeMap.addMapping(CityThemeRel::getCityThemeTagRelList, ThemeRelDto::setThemeTagRelDtoList);
        typeMap.addMapping(src -> src.getTheme().getName(), ThemeRelDto::setThemeName);
    }

    public static void cityThemeTagRelToDto(ModelMapper modelMapper) {
        TypeMap<CityThemeTagRel, ThemeTagRelDto> typeMap = modelMapper.createTypeMap(CityThemeTagRel.class, ThemeTagRelDto.class);
        typeMap.addMapping(src -> src.getTag().getName(), ThemeTagRelDto::setTagName);
    }

    public static void cityDtoToVm(ModelMapper modelMapper) {
        TypeMap<CityDto, CityVm> typeMap = modelMapper.createTypeMap(CityDto.class, CityVm.class);
        typeMap.addMapping(CityDto::getCityInfoDtoList, CityVm::setCityInfoVmList);
        typeMap.addMapping(CityDto::getThemeRelDtoList, CityVm::setThemeRelVmList);
        typeMap.addMapping(CityDto::getRegionDtoList, CityVm::setRegionVmList);
        typeMap.addMapping(CityDto::getBaseImageDtoList, CityVm::setBaseImageVmList);
    }

}

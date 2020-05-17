package com.kokotripadmin.config.modelmapperconfig;

import com.kokotripadmin.entity.city.CityThemeRel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class CityThemeRelModelConfig {


    public static void setCityThemeRelEntityToCityThemeRelDtoTypeMap(ModelMapper modelMapper) {
//        TypeMap<CityThemeRel, CityThemeRelDto> typeMap
//                = modelMapper.createTypeMap(CityThemeRel.class, CityThemeRelDto.class);
//        typeMap.addMapping(CityThemeRel::getTheme, CityThemeRelDto::setThemeDto);
//        typeMap.addMapping(CityThemeRel::getCityThemeTagRelList, CityThemeRelDto::setCityThemeTagRelDtoList);
    }

    public static void setCityThemeRelDtoToCityThemeRelViewModelTypeMap(ModelMapper modelMapper) {
//        TypeMap<CityThemeRelDto, CityThemeRelViewModel> typeMap = modelMapper.createTypeMap(CityThemeRelDto.class,
//                                                                                            CityThemeRelViewModel.class);
//        typeMap.addMapping(CityThemeRelDto::getThemeDto, CityThemeRelViewModel::setThemeViewModel);
//        typeMap.addMapping(CityThemeRelDto::getCityThemeTagRelDtoList, CityThemeRelViewModel::setCityThemeTagRelViewModelList);
    }
}

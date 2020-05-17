package com.kokotripadmin.config.modelmapperconfig;

import com.kokotripadmin.entity.city.CityThemeTagRel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class CityThemeTagRelModelConfig {



    public static void setCityThemeTagRelEntityToCityThemeTagRelDtoTypeMap(ModelMapper modelMapper) {
//        TypeMap<CityThemeTagRel, CityThemeTagRelDto> typeMap
//                = modelMapper.createTypeMap(CityThemeTagRel.class, CityThemeTagRelDto.class);
//        typeMap.addMapping(CityThemeTagRel::getTag, CityThemeTagRelDto::setTagDto);
    }

    public static void setCityThemeTagRelDtoToCityThemeTagRelViewModelTypeMap(ModelMapper modelMapper) {
//        TypeMap<CityThemeTagRelDto, CityThemeTagRelViewModel> typeMap = modelMapper.createTypeMap(CityThemeTagRelDto.class,
//                                                                                                  CityThemeTagRelViewModel.class);
//        typeMap.addMapping(CityThemeTagRelDto::getTagDto, CityThemeTagRelViewModel::setTagViewModel);
    }
}

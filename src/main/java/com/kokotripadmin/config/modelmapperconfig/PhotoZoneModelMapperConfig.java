package com.kokotripadmin.config.modelmapperconfig;

import com.kokotripadmin.dto.activity.ActivityDto;
import com.kokotripadmin.dto.photozone.PhotoZoneDto;
import com.kokotripadmin.dto.photozone.PhotoZoneInfoDto;
import com.kokotripadmin.entity.activity.Activity;
import com.kokotripadmin.entity.photozone.PhotoZone;
import com.kokotripadmin.entity.photozone.PhotoZoneInfo;
import com.kokotripadmin.viewmodel.photozone.PhotoZoneVm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class PhotoZoneModelMapperConfig {



    public static void PhotoZoneToDto(ModelMapper modelMapper) {
        TypeMap<PhotoZone, PhotoZoneDto> typeMap = modelMapper.createTypeMap(PhotoZone.class, PhotoZoneDto.class);
        typeMap.addMapping(src -> src.getParentTourSpot().getId(), PhotoZoneDto::setParentTourSpotId);
        typeMap.addMapping(src -> src.getParentTourSpot().getName(), PhotoZoneDto::setParentTourSpotName);
        typeMap.addMapping(src -> src.getTourSpot().getId(), PhotoZoneDto::setTourSpotId);
        typeMap.addMapping(src -> src.getTourSpot().getName(), PhotoZoneDto::setTourSpotName);
        typeMap.addMapping(src -> src.getActivity().getId(), PhotoZoneDto::setActivityId);
        typeMap.addMapping(src -> src.getActivity().getName(), PhotoZoneDto::setActivityName);
    }

    public static void PhotoZoneInfoToDto(ModelMapper modelMapper) {
        TypeMap<PhotoZoneInfo, PhotoZoneInfoDto> typeMap = modelMapper.createTypeMap(PhotoZoneInfo.class, PhotoZoneInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), PhotoZoneInfoDto::setSupportLanguageName);
    }

    public static void PhotoZoneDtoToVm(ModelMapper modelMapper) {
        TypeMap<PhotoZoneDto, PhotoZoneVm> typeMap = modelMapper.createTypeMap(PhotoZoneDto.class, PhotoZoneVm.class);
        typeMap.addMapping(PhotoZoneDto::getPhotoZoneInfoDtoList, PhotoZoneVm::setPhotoZoneInfoVmList);
    }
}

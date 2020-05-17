package com.kokotripadmin.config.modelmapperconfig;

import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionDto;
import com.kokotripadmin.dto.tourspot.TourSpotDescriptionInfoDto;
import com.kokotripadmin.dto.tourspot.TourSpotDto;
import com.kokotripadmin.dto.tourspot.TourSpotInfoDto;
import com.kokotripadmin.entity.tourspot.TourSpotDescription;
import com.kokotripadmin.entity.tourspot.TourSpotDescriptionInfo;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import com.kokotripadmin.entity.tourspot.TourSpot;
import com.kokotripadmin.viewmodel.tourspot.TourSpotDescriptionVm;
import com.kokotripadmin.viewmodel.tourspot.TourSpotVm;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.ui.Model;

public class TourSpotModelMapperConfig {

    public static void tourSpotVmToDto(ModelMapper modelMapper) {
        TypeMap<TourSpotVm, TourSpotDto> typeMap = modelMapper.createTypeMap(TourSpotVm.class, TourSpotDto.class);
        typeMap.addMapping(TourSpotVm::getTradingHourVmList, TourSpotDto::setTradingHourDtoList);
    }

    public static void tourSpotToDto(ModelMapper modelMapper) {
        TypeMap<TourSpot, TourSpotDto> typeMap = modelMapper.createTypeMap(TourSpot.class, TourSpotDto.class);
        typeMap.addMapping(TourSpot::getTourSpotTradingHourList, TourSpotDto::setTradingHourDtoList);
        typeMap.addMapping(src -> src.getCity().getName(), TourSpotDto::setCityName);
        typeMap.addMapping(src -> src.getRegion().getName(), TourSpotDto::setRegionName);
        typeMap.addMapping(src -> src.getTag().getName(), TourSpotDto::setTagName);

    }

    public static void tourSpotInfoToDto(ModelMapper modelMapper) {
        TypeMap<TourSpotInfo, TourSpotInfoDto> typeMap = modelMapper.createTypeMap(TourSpotInfo.class, TourSpotInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), TourSpotInfoDto::setSupportLanguageName);
        typeMap.addMapping(src -> src.getTagInfo().getName(), TourSpotInfoDto::setTagName);
    }

    @SuppressWarnings("Duplicates")
    public static void tourSpotDtoToVm(ModelMapper modelMapper) {
        TypeMap<TourSpotDto, TourSpotVm> typeMap = modelMapper.createTypeMap(TourSpotDto.class, TourSpotVm.class);
        typeMap.addMapping(TourSpotDto::getTradingHourDtoList, TourSpotVm::setTradingHourVmList);
        typeMap.addMapping(TourSpotDto::getTourSpotInfoDtoList, TourSpotVm::setTourSpotInfoVmList);
        typeMap.addMapping(TourSpotDto::getTourSpotDescriptionDtoList, TourSpotVm::setTourSpotDescriptionVmList);
        typeMap.addMapping(TourSpotDto::getTourSpotTicketDtoList, TourSpotVm::setTourSpotTicketVmList);
        typeMap.addMapping(TourSpotDto::getActivityDtoList, TourSpotVm::setActivityVmList);
        typeMap.addMapping(TourSpotDto::getPhotoZoneDtoList, TourSpotVm::setPhotoZoneVmList);
    }

    public static void tourSpotDescriptionToDto(ModelMapper modelMapper) {
        TypeMap<TourSpotDescription, TourSpotDescriptionDto> typeMap =
                modelMapper.createTypeMap(TourSpotDescription.class, TourSpotDescriptionDto.class);

        typeMap.addMapping(src -> src.getTourSpot().getName(), TourSpotDescriptionDto::setTourSpotName);
    }

    public static void tourSpotDescriptionDtoToVm(ModelMapper modelMapper) {
        TypeMap<TourSpotDescriptionDto, TourSpotDescriptionVm> typeMap =
                modelMapper.createTypeMap(TourSpotDescriptionDto.class, TourSpotDescriptionVm.class);

        typeMap.addMapping(TourSpotDescriptionDto::getTourSpotDescriptionInfoDtoList,
                           TourSpotDescriptionVm::setTourSpotDescriptionInfoVmList);
    }

    public static void tourSpotDescriptionInfoToDto(ModelMapper modelMapper) {
        TypeMap<TourSpotDescriptionInfo, TourSpotDescriptionInfoDto> typeMap =
                modelMapper.createTypeMap(TourSpotDescriptionInfo.class, TourSpotDescriptionInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), TourSpotDescriptionInfoDto::setSupportLanguageName);
    }


}

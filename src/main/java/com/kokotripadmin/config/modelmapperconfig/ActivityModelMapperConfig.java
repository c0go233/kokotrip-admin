package com.kokotripadmin.config.modelmapperconfig;

import com.kokotripadmin.dto.activity.ActivityDescriptionDto;
import com.kokotripadmin.dto.activity.ActivityDescriptionInfoDto;
import com.kokotripadmin.dto.activity.ActivityDto;
import com.kokotripadmin.dto.activity.ActivityInfoDto;
import com.kokotripadmin.entity.activity.*;
import com.kokotripadmin.entity.activity.ActivityDescriptionInfo;
import com.kokotripadmin.entity.activity.ActivityInfo;
import com.kokotripadmin.viewmodel.activity.ActivityDescriptionVm;
import com.kokotripadmin.viewmodel.activity.ActivityVm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class ActivityModelMapperConfig {


    public static void activityToDto(ModelMapper modelMapper) {
        TypeMap<Activity, ActivityDto> typeMap = modelMapper.createTypeMap(Activity.class, ActivityDto.class);
        typeMap.addMapping(src -> src.getTag().getName(), ActivityDto::setTagName);
        typeMap.addMapping(src -> src.getTourSpot().getName(), ActivityDto::setTourSpotName);
    }

    public static void activityInfoToDto(ModelMapper modelMapper) {
        TypeMap<ActivityInfo, ActivityInfoDto> typeMap = modelMapper.createTypeMap(ActivityInfo.class, ActivityInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), ActivityInfoDto::setSupportLanguageName);
    }

    @SuppressWarnings("Duplicates")
    public static void activityDtoToVm(ModelMapper modelMapper) {
        TypeMap<ActivityDto, ActivityVm> typeMap = modelMapper.createTypeMap(ActivityDto.class, ActivityVm.class);
        typeMap.addMapping(ActivityDto::getActivityInfoDtoList, ActivityVm::setActivityInfoVmList);
        typeMap.addMapping(ActivityDto::getActivityDescriptionDtoList, ActivityVm::setActivityDescriptionVmList);
        typeMap.addMapping(ActivityDto::getActivityTicketDtoList, ActivityVm::setActivityTicketVmList);
        typeMap.addMapping(ActivityDto::getActivityImageDtoList, ActivityVm::setBaseImageVmList);
    }


    @SuppressWarnings("Duplicates")
    public static void activityDescriptionToDto(ModelMapper modelMapper) {
        TypeMap<ActivityDescription, ActivityDescriptionDto> typeMap =
                modelMapper.createTypeMap(ActivityDescription.class, ActivityDescriptionDto.class);

        typeMap.addMapping(src -> src.getActivity().getName(), ActivityDescriptionDto::setActivityName);
        typeMap.addMapping(src -> src.getActivity().getTourSpot().getName(), ActivityDescriptionDto::setTourSpotName);
        typeMap.addMapping(src -> src.getActivity().getTourSpot().getId(), ActivityDescriptionDto::setTourSpotId);
    }

    public static void activityDescriptionDtoToVm(ModelMapper modelMapper) {
        TypeMap<ActivityDescriptionDto, ActivityDescriptionVm> typeMap =
                modelMapper.createTypeMap(ActivityDescriptionDto.class, ActivityDescriptionVm.class);

        typeMap.addMapping(ActivityDescriptionDto::getActivityDescriptionInfoDtoList,
                           ActivityDescriptionVm::setActivityDescriptionInfoVmList);
        typeMap.addMapping(ActivityDescriptionDto::getActivityDescriptionImageDtoList, ActivityDescriptionVm::setBaseImageVmList);
    }

    public static void activityDescriptionInfoToDto(ModelMapper modelMapper) {
        TypeMap<ActivityDescriptionInfo, ActivityDescriptionInfoDto> typeMap =
                modelMapper.createTypeMap(ActivityDescriptionInfo.class, ActivityDescriptionInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), ActivityDescriptionInfoDto::setSupportLanguageName);
    }



}

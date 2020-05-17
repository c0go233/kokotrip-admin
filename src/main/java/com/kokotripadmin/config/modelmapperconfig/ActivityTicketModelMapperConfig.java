package com.kokotripadmin.config.modelmapperconfig;

import com.kokotripadmin.dto.activity.ActivityTicketDescriptionDto;
import com.kokotripadmin.dto.activity.ActivityTicketDescriptionInfoDto;
import com.kokotripadmin.dto.activity.ActivityTicketDto;
import com.kokotripadmin.dto.activity.ActivityTicketInfoDto;
import com.kokotripadmin.dto.ticket.TicketPriceDto;
import com.kokotripadmin.entity.activity.*;
import com.kokotripadmin.viewmodel.activity.ActivityTicketDescriptionVm;
import com.kokotripadmin.viewmodel.activity.ActivityTicketVm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class ActivityTicketModelMapperConfig {

    public static void activityTicketVmToDto(ModelMapper modelMapper) {
        TypeMap<ActivityTicketVm, ActivityTicketDto> typeMap =
                modelMapper.createTypeMap(ActivityTicketVm.class, ActivityTicketDto.class);
        typeMap.addMapping(ActivityTicketVm::getTicketPriceVmList, ActivityTicketDto::setTicketPriceDtoList);
    }

    @SuppressWarnings("Duplicates")
    public static void activityTicketToDto(ModelMapper modelMapper) {
        TypeMap<ActivityTicket, ActivityTicketDto> typeMap =
                modelMapper.createTypeMap(ActivityTicket.class, ActivityTicketDto.class);

        typeMap.addMapping(src -> src.getActivity().getName(), ActivityTicketDto::setActivityName);
        typeMap.addMapping(src -> src.getActivity().getTourSpot().getName(), ActivityTicketDto::setTourSpotName);
        typeMap.addMapping(src -> src.getActivity().getTourSpot().getId(), ActivityTicketDto::setTourSpotId);
        typeMap.addMapping(ActivityTicket::getActivityTicketPriceList, ActivityTicketDto::setTicketPriceDtoList);
    }

    @SuppressWarnings("Duplicates")
    public static void activityTicketDtoToVm(ModelMapper modelMapper) {
        TypeMap<ActivityTicketDto, ActivityTicketVm> typeMap =
                modelMapper.createTypeMap(ActivityTicketDto.class, ActivityTicketVm.class);
        typeMap.addMapping(ActivityTicketDto::getTicketPriceDtoList, ActivityTicketVm::setTicketPriceVmList);
        typeMap.addMapping(ActivityTicketDto::getActivityTicketInfoDtoList, ActivityTicketVm::setActivityTicketInfoVmList);
        typeMap.addMapping(ActivityTicketDto::getActivityTicketDescriptionDtoList, ActivityTicketVm::setActivityTicketDescriptionVmList);

    }

    public static void activityTicketInfoToDto(ModelMapper modelMapper) {
        TypeMap<ActivityTicketInfo, ActivityTicketInfoDto> typeMap = modelMapper.createTypeMap(ActivityTicketInfo.class,
                                                                                               ActivityTicketInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), ActivityTicketInfoDto::setSupportLanguageName);
    }

    public static void activityTicketPriceToDto(ModelMapper modelMapper) {
        TypeMap<ActivityTicketPrice, TicketPriceDto> typeMap =
                modelMapper.createTypeMap(ActivityTicketPrice.class, TicketPriceDto.class);
        typeMap.addMapping(src -> src.getTicketType().getName(), TicketPriceDto::setTicketTypeName);
    }



    public static void activityTicketDescriptionToDto(ModelMapper modelMapper) {
        TypeMap<ActivityTicketDescription, ActivityTicketDescriptionDto> typeMap =
                modelMapper.createTypeMap(ActivityTicketDescription.class, ActivityTicketDescriptionDto.class);

        typeMap.addMapping(src -> src.getActivityTicket().getName(), ActivityTicketDescriptionDto::setActivityTicketName);
        typeMap.addMapping(src -> src.getActivityTicket().getActivity().getName(), ActivityTicketDescriptionDto::setActivityName);
        typeMap.addMapping(src -> src.getActivityTicket().getActivity().getId(), ActivityTicketDescriptionDto::setActivityId);


        typeMap.addMapping(src -> src.getActivityTicket().getActivity().getTourSpot().getId(),
                           ActivityTicketDescriptionDto::setTourSpotId);
        typeMap.addMapping(src -> src.getActivityTicket().getActivity().getTourSpot().getName(),
                           ActivityTicketDescriptionDto::setTourSpotName);


    }

    public static void activityTicketDescriptionDtoToVm(ModelMapper modelMapper) {
        TypeMap<ActivityTicketDescriptionDto, ActivityTicketDescriptionVm> typeMap =
                modelMapper.createTypeMap(ActivityTicketDescriptionDto.class, ActivityTicketDescriptionVm.class);

        typeMap.addMapping(ActivityTicketDescriptionDto::getActivityTicketDescriptionInfoDtoList,
                           ActivityTicketDescriptionVm::setActivityTicketDescriptionInfoVmList);
    }

    public static void activityTicketDescriptionInfoToDto(ModelMapper modelMapper) {
        TypeMap<ActivityTicketDescriptionInfo, ActivityTicketDescriptionInfoDto> typeMap =
                modelMapper.createTypeMap(ActivityTicketDescriptionInfo.class, ActivityTicketDescriptionInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), ActivityTicketDescriptionInfoDto::setSupportLanguageName);
    }
}

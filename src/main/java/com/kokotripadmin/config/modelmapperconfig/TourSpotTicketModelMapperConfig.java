package com.kokotripadmin.config.modelmapperconfig;


import com.kokotripadmin.dto.ticket.TicketPriceDto;
import com.kokotripadmin.dto.tourspot.*;

import com.kokotripadmin.entity.tourspot.ticket.*;
import com.kokotripadmin.viewmodel.tourspot.TourSpotTicketDescriptionVm;
import com.kokotripadmin.viewmodel.tourspot.TourSpotTicketVm;
import com.kokotripadmin.viewmodel.tourspot.TourSpotVm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class TourSpotTicketModelMapperConfig {


    public static void tourSpotTicketVmToDto(ModelMapper modelMapper) {
        TypeMap<TourSpotTicketVm, TourSpotTicketDto> typeMap =
                modelMapper.createTypeMap(TourSpotTicketVm.class, TourSpotTicketDto.class);
        typeMap.addMapping(TourSpotTicketVm::getTicketPriceVmList, TourSpotTicketDto::setTicketPriceDtoList);
    }

    public static void tourSpotTicketToDto(ModelMapper modelMapper) {
        TypeMap<TourSpotTicket, TourSpotTicketDto> typeMap =
                modelMapper.createTypeMap(TourSpotTicket.class, TourSpotTicketDto.class);
        typeMap.addMapping(src -> src.getTourSpot().getName(), TourSpotTicketDto::setTourSpotName);
        typeMap.addMapping(TourSpotTicket::getTourSpotTicketPriceList, TourSpotTicketDto::setTicketPriceDtoList);
    }

    @SuppressWarnings("Duplicates")
    public static void tourSpotTicketDtoToVm(ModelMapper modelMapper) {
        TypeMap<TourSpotTicketDto, TourSpotTicketVm> typeMap =
                modelMapper.createTypeMap(TourSpotTicketDto.class, TourSpotTicketVm.class);
        typeMap.addMapping(TourSpotTicketDto::getTicketPriceDtoList, TourSpotTicketVm::setTicketPriceVmList);
        typeMap.addMapping(TourSpotTicketDto::getTourSpotTicketInfoDtoList, TourSpotTicketVm::setTourSpotTicketInfoVmList);
        typeMap.addMapping(TourSpotTicketDto::getTourSpotTicketDescriptionDtoList, TourSpotTicketVm::setTourSpotTicketDescriptionVmList);
        typeMap.addMapping(TourSpotTicketDto::getTourSpotTicketImageDtoList, TourSpotTicketVm::setBaseImageVmList);

    }

    public static void tourSpotTicketInfoToDto(ModelMapper modelMapper) {
        TypeMap<TourSpotTicketInfo, TourSpotTicketInfoDto> typeMap = modelMapper.createTypeMap(TourSpotTicketInfo.class, TourSpotTicketInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), TourSpotTicketInfoDto::setSupportLanguageName);
    }

    public static void tourSpotTicketPriceToDto(ModelMapper modelMapper) {
        TypeMap<TourSpotTicketPrice, TicketPriceDto> typeMap =
                modelMapper.createTypeMap(TourSpotTicketPrice.class, TicketPriceDto.class);
        typeMap.addMapping(src -> src.getTicketType().getName(), TicketPriceDto::setTicketTypeName);
    }



    public static void tourSpotTicketDescriptionToDto(ModelMapper modelMapper) {
        TypeMap<TourSpotTicketDescription, TourSpotTicketDescriptionDto> typeMap =
                modelMapper.createTypeMap(TourSpotTicketDescription.class, TourSpotTicketDescriptionDto.class);

        typeMap.addMapping(src -> src.getTourSpotTicket().getTourSpot().getName(), TourSpotTicketDescriptionDto::setTourSpotName);
        typeMap.addMapping(src -> src.getTourSpotTicket().getTourSpot().getId(), TourSpotTicketDescriptionDto::setTourSpotId);
        typeMap.addMapping(src -> src.getTourSpotTicket().getName(), TourSpotTicketDescriptionDto::setTourSpotTicketName);
    }

    public static void tourSpotTicketDescriptionDtoToVm(ModelMapper modelMapper) {
        TypeMap<TourSpotTicketDescriptionDto, TourSpotTicketDescriptionVm> typeMap =
                modelMapper.createTypeMap(TourSpotTicketDescriptionDto.class, TourSpotTicketDescriptionVm.class);

        typeMap.addMapping(TourSpotTicketDescriptionDto::getTourSpotTicketDescriptionInfoDtoList,
                           TourSpotTicketDescriptionVm::setTourSpotTicketDescriptionInfoVmList);
        typeMap.addMapping(TourSpotTicketDescriptionDto::getTourSpotTicketDescriptionImageDtoList,
                           TourSpotTicketDescriptionVm::setBaseImageVmList);
    }

    public static void tourSpotTicketDescriptionInfoToDto(ModelMapper modelMapper) {
        TypeMap<TourSpotTicketDescriptionInfo, TourSpotTicketDescriptionInfoDto> typeMap =
                modelMapper.createTypeMap(TourSpotTicketDescriptionInfo.class, TourSpotTicketDescriptionInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), TourSpotTicketDescriptionInfoDto::setSupportLanguageName);
    }

}

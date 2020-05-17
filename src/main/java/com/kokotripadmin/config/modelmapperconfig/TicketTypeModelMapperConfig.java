package com.kokotripadmin.config.modelmapperconfig;

import com.kokotripadmin.dto.ticket.TicketTypeDto;
import com.kokotripadmin.dto.ticket.TicketTypeInfoDto;
import com.kokotripadmin.entity.ticket.TicketType;
import com.kokotripadmin.entity.ticket.TicketTypeInfo;
import com.kokotripadmin.viewmodel.ticket.TicketTypeVm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class TicketTypeModelMapperConfig {


    public static void ticketTypeDtoToVm(ModelMapper modelMapper) {
        TypeMap<TicketTypeDto, TicketTypeVm> typeMap = modelMapper.createTypeMap(TicketTypeDto.class, TicketTypeVm.class);
        typeMap.addMapping(TicketTypeDto::getTicketTypeInfoDtoList, TicketTypeVm::setTicketTypeInfoVmList);
    }

    public static void ticketTypeInfoToDto(ModelMapper modelMapper) {
        TypeMap<TicketTypeInfo, TicketTypeInfoDto> typeMap = modelMapper.createTypeMap(TicketTypeInfo.class, TicketTypeInfoDto.class);
        typeMap.addMapping(src -> src.getSupportLanguage().getName(), TicketTypeInfoDto::setSupportLanguageName);
    }
}

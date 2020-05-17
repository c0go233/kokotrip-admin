package com.kokotripadmin.config.modelmapperconfig;

import com.kokotripadmin.dto.common.TradingHourDto;
import com.kokotripadmin.entity.tourspot.TourSpotTradingHour;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class TradingHourModelMapperConfig {



    public static void setTourSpotTradingHourEntityToTradingHourDto(ModelMapper modelMapper) {
        TypeMap<TourSpotTradingHour, TradingHourDto> typeMap = modelMapper.createTypeMap(TourSpotTradingHour.class, TradingHourDto.class);
        typeMap.addMapping(src -> src.getDayOfWeek().getId(), TradingHourDto::setDayOfWeekId);
        typeMap.addMapping(src -> src.getDayOfWeek().getName(), TradingHourDto::setDayOfWeekName);
        typeMap.addMapping(src -> src.getTradingHourType().getId(), TradingHourDto::setTradingHourTypeId);
        typeMap.addMapping(src -> src.getTradingHourType().getName(), TradingHourDto::setTradingHourTypeName);

    }


}

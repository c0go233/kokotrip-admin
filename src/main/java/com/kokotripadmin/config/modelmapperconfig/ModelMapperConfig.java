package com.kokotripadmin.config.modelmapperconfig;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelMapperConfig {


    public static void set(ModelMapper modelMapper) {


        Converter<Date, String> dateStringConverter = new AbstractConverter<Date, String>() {
            @Override
            protected String convert(Date date) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            }
        };

        modelMapper.addConverter(dateStringConverter);

//      ================================ STATE ====================================  //

        StateModelMapperConfig.stateDtoToVm(modelMapper);


//      ================================ CITY ====================================  //

        CityModelMapperConfig.cityToDto(modelMapper);
        CityModelMapperConfig.cityThemeRelToDto(modelMapper);
        CityModelMapperConfig.cityThemeTagRelToDto(modelMapper);
        CityModelMapperConfig.cityDtoToVm(modelMapper);
        CityModelMapperConfig.cityInfoToDto(modelMapper);



//      ================================ REGION ====================================  //

        RegionModelMapperConfig.regionToDto(modelMapper);
        RegionModelMapperConfig.regionDtoToVm(modelMapper);
        RegionModelMapperConfig.regionThemeRelToDto(modelMapper);
        RegionModelMapperConfig.regionThemeTagRelToDto(modelMapper);
        RegionModelMapperConfig.regionInfoToDto(modelMapper);

//      ================================ THEME ====================================  //

        ThemeModelMapperConfig.themeRelDtoToVm(modelMapper);
        ThemeModelMapperConfig.themeDtoToVm(modelMapper);
        ThemeModelMapperConfig.themeInfoToDto(modelMapper);

//      ================================ TAG ====================================  //

        TagModelMapperConfig.tagToDto(modelMapper);
        TagModelMapperConfig.tagDtoToVm(modelMapper);
        TagModelMapperConfig.tagInfoToDto(modelMapper);

//      ================================ TICKET-TYPE ====================================  //

        TicketTypeModelMapperConfig.ticketTypeDtoToVm(modelMapper);
        TicketTypeModelMapperConfig.ticketTypeInfoToDto(modelMapper);

//      ================================ TOUR SPOT ====================================  //

        TourSpotModelMapperConfig.tourSpotVmToDto(modelMapper);
        TourSpotModelMapperConfig.tourSpotToDto(modelMapper);
        TourSpotModelMapperConfig.tourSpotDtoToVm(modelMapper);
        TourSpotModelMapperConfig.tourSpotInfoToDto(modelMapper);

        TourSpotModelMapperConfig.tourSpotDescriptionToDto(modelMapper);
        TourSpotModelMapperConfig.tourSpotDescriptionDtoToVm(modelMapper);
        TourSpotModelMapperConfig.tourSpotDescriptionInfoToDto(modelMapper);

        TourSpotTicketModelMapperConfig.tourSpotTicketVmToDto(modelMapper);
        TourSpotTicketModelMapperConfig.tourSpotTicketToDto(modelMapper);
        TourSpotTicketModelMapperConfig.tourSpotTicketDtoToVm(modelMapper);
        TourSpotTicketModelMapperConfig.tourSpotTicketInfoToDto(modelMapper);
        TourSpotTicketModelMapperConfig.tourSpotTicketPriceToDto(modelMapper);

        TourSpotTicketModelMapperConfig.tourSpotTicketDescriptionDtoToVm(modelMapper);
        TourSpotTicketModelMapperConfig.tourSpotTicketDescriptionInfoToDto(modelMapper);
        TourSpotTicketModelMapperConfig.tourSpotTicketDescriptionToDto(modelMapper);

//      ================================ ACTIVITY ====================================  //

        ActivityModelMapperConfig.activityToDto(modelMapper);
        ActivityModelMapperConfig.activityInfoToDto(modelMapper);
        ActivityModelMapperConfig.activityDtoToVm(modelMapper);

        ActivityModelMapperConfig.activityDescriptionDtoToVm(modelMapper);
        ActivityModelMapperConfig.activityDescriptionInfoToDto(modelMapper);
        ActivityModelMapperConfig.activityDescriptionToDto(modelMapper);

        ActivityTicketModelMapperConfig.activityTicketVmToDto(modelMapper);
        ActivityTicketModelMapperConfig.activityTicketToDto(modelMapper);
        ActivityTicketModelMapperConfig.activityTicketDtoToVm(modelMapper);
        ActivityTicketModelMapperConfig.activityTicketInfoToDto(modelMapper);
        ActivityTicketModelMapperConfig.activityTicketPriceToDto(modelMapper);

        ActivityTicketModelMapperConfig.activityTicketDescriptionDtoToVm(modelMapper);
        ActivityTicketModelMapperConfig.activityTicketDescriptionInfoToDto(modelMapper);
        ActivityTicketModelMapperConfig.activityTicketDescriptionToDto(modelMapper);

//      ================================ PHOTOZONE ====================================  //

        PhotoZoneModelMapperConfig.PhotoZoneToDto(modelMapper);
        PhotoZoneModelMapperConfig.PhotoZoneDtoToVm(modelMapper);
        PhotoZoneModelMapperConfig.PhotoZoneInfoToDto(modelMapper);
    }

}

package com.kokotripadmin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotripadmin.constant.AppConstant;
import com.kokotripadmin.dto.activity.*;
import com.kokotripadmin.dto.city.CityDto;
import com.kokotripadmin.dto.city.CityInfoDto;
import com.kokotripadmin.dto.common.LocatableAutoCompleteDto;
import com.kokotripadmin.dto.common.ThemeRelDto;
import com.kokotripadmin.dto.photozone.PhotoZoneDto;
import com.kokotripadmin.dto.photozone.PhotoZoneInfoDto;
import com.kokotripadmin.dto.region.RegionDto;
import com.kokotripadmin.dto.region.RegionInfoDto;
import com.kokotripadmin.dto.state.StateDto;
import com.kokotripadmin.dto.tag.TagDto;
import com.kokotripadmin.dto.tag.TagInfoDto;
import com.kokotripadmin.dto.theme.ThemeDto;
import com.kokotripadmin.dto.theme.ThemeInfoDto;
import com.kokotripadmin.dto.ticket.TicketTypeDto;
import com.kokotripadmin.dto.ticket.TicketTypeInfoDto;
import com.kokotripadmin.dto.tourspot.*;
import com.kokotripadmin.entity.State;
import com.kokotripadmin.entity.activity.*;
import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.city.CityImage;
import com.kokotripadmin.entity.city.CityInfo;
import com.kokotripadmin.entity.city.CityThemeRel;
import com.kokotripadmin.entity.photozone.PhotoZone;
import com.kokotripadmin.entity.photozone.PhotoZoneInfo;
import com.kokotripadmin.entity.region.Region;
import com.kokotripadmin.entity.region.RegionInfo;
import com.kokotripadmin.entity.region.RegionThemeRel;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tag.TagInfo;
import com.kokotripadmin.entity.tag.Theme;
import com.kokotripadmin.entity.tag.ThemeInfo;
import com.kokotripadmin.entity.ticket.TicketType;
import com.kokotripadmin.entity.ticket.TicketTypeInfo;
import com.kokotripadmin.entity.tourspot.*;
import com.kokotripadmin.entity.tourspot.ticket.*;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Convert {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public String fieldErrorsToJson(BindingResult bindingResult) throws JsonProcessingException {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors())
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        return objectMapper.writeValueAsString(errors);
    }

    public String exceptionToJson(String exceptionMessage) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AppConstant.EXCEPTION, exceptionMessage);
        return jsonObject.toString();
    }

    public String resultToJson(String resultMessage) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", resultMessage);
        return jsonObject.toString();
    }

    public File multiPartToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

    public String toAmazonS3EndPoint(String bucketName, String region, String directory, String fileName) {
        return "https://" + bucketName + "." + region + ".amazonaws.com/" + directory + "/" + fileName;
    }

//  ============================================= STATE ================================================  //

    public Function<State, StateDto> stateToDto() {
        return state -> {
            return new StateDto(state.getId(), state.getName(), state.isEnabled());
        };
    }

    public StateDto stateToDto(State state) {
        return stateToDto().apply(state);
    }

    public StateDto stateToDtoInDetail(State state) {
        StateDto stateDto = modelMapper.map(state, StateDto.class);
        for (City city : state.getCityList())
            stateDto.getCityDtoList().add(cityToDto(city));
        return stateDto;
    }



//  ============================================= CITY ================================================  //


    public Function<City, CityDto> cityToDto() {
        return city -> {
            return new CityDto(city.getId(), city.getName(), city.isEnabled(), city.getDescription());
        };
    }

    public CityDto cityToDto(City city) {
        return cityToDto().apply(city);
    }

    public CityDto cityToDtoInDetail(City city, List<CityThemeRel> cityThemeRelList) {

        CityDto cityDto = modelMapper.map(city, CityDto.class);
        for (CityInfo cityInfo : city.getCityInfoList())
            cityDto.getCityInfoDtoList().add(modelMapper.map(cityInfo, CityInfoDto.class));

        for (Region region : city.getRegionList())
            cityDto.getRegionDtoList().add(regionToDto(region));

        for (CityThemeRel cityThemeRel : cityThemeRelList)
            cityDto.getThemeRelDtoList().add(modelMapper.map(cityThemeRel, ThemeRelDto.class));


        return cityDto;
    }




//  ============================================= REGION ================================================  //


    public Function<Region, RegionDto> regionToDto() {
        return region -> new RegionDto(region.getId(), region.getName(), region.isEnabled(), region.getDescription());
    }

    public RegionDto regionToDto(Region region) {
        return regionToDto().apply(region);
    }

    public RegionDto regionToDtoInDetail(Region region, List<RegionThemeRel> regionThemeRelList) {
        RegionDto regionDto = modelMapper.map(region, RegionDto.class);
        for (RegionInfo regionInfo : region.getRegionInfoList())
            regionDto.getRegionInfoDtoList().add(modelMapper.map(regionInfo, RegionInfoDto.class));

        for (RegionThemeRel regionThemeRel : regionThemeRelList)
            regionDto.getThemeRelDtoList().add(modelMapper.map(regionThemeRel, ThemeRelDto.class));

        return regionDto;
    }



//  ============================================= THEME & TAG ================================================  //


    public Function<Theme, ThemeDto> themeToDto() {
        return theme -> new ThemeDto(theme.getId(), theme.getName(), theme.isEnabled());
    }

    public ThemeDto themeToDto(Theme theme) {
        return themeToDto().apply(theme);
    }

    public ThemeDto themeToDtoInDetail(Theme theme) {
        ThemeDto themeDto = modelMapper.map(theme, ThemeDto.class);

        for (ThemeInfo themeInfo : theme.getThemeInfoList())
            themeDto.getThemeInfoDtoList().add(modelMapper.map(themeInfo, ThemeInfoDto.class));

        for (Tag tag : theme.getTagList())
            themeDto.getTagDtoList().add(tagToDto(tag));

        return themeDto;
    }


    public Function<Tag, TagDto> tagToDto() {
        return tag -> new TagDto(tag.getId(), tag.getName(), tag.isEnabled());
    }

    public TagDto tagToDto(Tag tag) {
        return tagToDto().apply(tag);
    }

    public TagDto tagToDtoInDetail(Tag tag) {
        TagDto tagDto = modelMapper.map(tag, TagDto.class);

        for (TagInfo tagInfo : tag.getTagInfoList())
            tagDto.getTagInfoDtoList().add(modelMapper.map(tagInfo, TagInfoDto.class));

        return tagDto;
    }


//  ============================================= TICKET-TYPE ================================================  //

    public TicketTypeDto ticketTypeToDtoInDetail(TicketType ticketType) {
        TicketTypeDto ticketTypeDto = modelMapper.map(ticketType, TicketTypeDto.class);

        for (TicketTypeInfo ticketTypeInfo : ticketType.getTicketTypeInfoList())
            ticketTypeDto.getTicketTypeInfoDtoList().add(modelMapper.map(ticketTypeInfo, TicketTypeInfoDto.class));

        return ticketTypeDto;
    }



//  ============================================= TOUR SPOT ================================================  //


    public Function<TourSpot, TourSpotDto> tourSpotToDto() {
        return tourSpot -> new TourSpotDto(tourSpot.getId(), tourSpot.getName(), tourSpot.isEnabled(),
                                           tourSpot.getDescription(), tourSpot.getCity().getName(),
                                           tourSpot.getRegion() == null ? "" : tourSpot.getRegion().getName());
    }

    public TourSpotDto tourSpotToDtoInDetail(TourSpot tourSpot, List<TourSpotInfo> tourSpotInfoList) {
        TourSpotDto tourSpotDto = modelMapper.map(tourSpot, TourSpotDto.class);

        for (TourSpotInfo tourSpotInfo : tourSpotInfoList)
            tourSpotDto.getTourSpotInfoDtoList().add(modelMapper.map(tourSpotInfo, TourSpotInfoDto.class));

        for (TourSpotDescription tourSpotDescription : tourSpot.getTourSpotDescriptionList())
            tourSpotDto.getTourSpotDescriptionDtoList().add(tourSpotDescriptionToDto(tourSpotDescription));

        for (TourSpotTicket tourSpotTicket : tourSpot.getTourSpotTicketList())
            tourSpotDto.getTourSpotTicketDtoList().add(tourSpotTicketToDto(tourSpotTicket));

        for (Activity activity : tourSpot.getActivityList())
            tourSpotDto.getActivityDtoList().add(activityToDto(activity));

        for (PhotoZone photoZone : tourSpot.getPhotoZoneList())
            tourSpotDto.getPhotoZoneDtoList().add(photoZoneToDto(photoZone));

        return tourSpotDto;
    }

    public List<LocatableAutoCompleteDto> tourSpotToLocatableAutoCompleteDto(List<TourSpot> tourSpotList) {
        List<LocatableAutoCompleteDto> locatableAutoCompleteDtoList = new ArrayList<>();
        for (TourSpot tourSpot : tourSpotList) {
            locatableAutoCompleteDtoList.add(new LocatableAutoCompleteDto(tourSpot.getId(),
                                                                          tourSpot.getName(),
                                                                          tourSpot.getLatitude(),
                                                                          tourSpot.getLongitude()));
        }
        return locatableAutoCompleteDtoList;
    }

    public TourSpotDescriptionDto tourSpotDescriptionToDtoInDetail(TourSpotDescription tourSpotDescription) {
        TourSpotDescriptionDto tourSpotDescriptionDto = modelMapper.map(tourSpotDescription, TourSpotDescriptionDto.class);

        for (TourSpotDescriptionInfo tourSpotDescriptionInfo : tourSpotDescription.getTourSpotDescriptionInfoList())
            tourSpotDescriptionDto.getTourSpotDescriptionInfoDtoList().add(modelMapper.map(tourSpotDescriptionInfo,
                                                                                           TourSpotDescriptionInfoDto.class));

        return tourSpotDescriptionDto;
    }



    public TourSpotDescriptionDto tourSpotDescriptionToDto(TourSpotDescription tourSpotDescription) {
        return new TourSpotDescriptionDto(tourSpotDescription.getId(), tourSpotDescription.getName(),
                                          tourSpotDescription.isEnabled(), tourSpotDescription.getDescription(),
                                          tourSpotDescription.getOrder());
    }

    public TourSpotTicketDto tourSpotTicketToDtoInDetail(TourSpotTicket tourSpotTicket) {
        TourSpotTicketDto tourSpotTicketDto = modelMapper.map(tourSpotTicket, TourSpotTicketDto.class);

        for (TourSpotTicketInfo tourSpotTicketInfo : tourSpotTicket.getTourSpotTicketInfoList())
            tourSpotTicketDto.getTourSpotTicketInfoDtoList().add(modelMapper.map(tourSpotTicketInfo, TourSpotTicketInfoDto.class));


        for (TourSpotTicketDescription tourSpotTicketDescription : tourSpotTicket.getTourSpotTicketDescriptionList())
            tourSpotTicketDto.getTourSpotTicketDescriptionDtoList().add(tourSpotTicketDescriptionToDto(tourSpotTicketDescription));

        return tourSpotTicketDto;
    }

    public TourSpotTicketDto tourSpotTicketToDto(TourSpotTicket tourSpotTicket) {
        return new TourSpotTicketDto(tourSpotTicket.getId(), tourSpotTicket.getName(),
                                     tourSpotTicket.isEnabled(), tourSpotTicket.getDescription(),
                                     tourSpotTicket.getOrder());
    }

    public TourSpotTicketDescriptionDto tourSpotTicketDescriptionToDtoInDetail(TourSpotTicketDescription tourSpotTicketDescription) {
        TourSpotTicketDescriptionDto tourSpotTicketDescriptionDto =
                modelMapper.map(tourSpotTicketDescription, TourSpotTicketDescriptionDto.class);

        for (TourSpotTicketDescriptionInfo tourSpotTicketDescriptionInfo :
                tourSpotTicketDescription.getTourSpotTicketDescriptionInfoList())
            tourSpotTicketDescriptionDto.getTourSpotTicketDescriptionInfoDtoList()
                                        .add(modelMapper.map(tourSpotTicketDescriptionInfo,
                                                             TourSpotTicketDescriptionInfoDto.class));

        return tourSpotTicketDescriptionDto;
    }


    public TourSpotTicketDescriptionDto tourSpotTicketDescriptionToDto(TourSpotTicketDescription tourSpotTicketDescription) {
        return new TourSpotTicketDescriptionDto(tourSpotTicketDescription.getId(), tourSpotTicketDescription.getName(),
                                                tourSpotTicketDescription.isEnabled(), tourSpotTicketDescription.getDescription(),
                                                tourSpotTicketDescription.getOrder());
    }



//  ============================================= ACTIVITY ================================================  //

    public Function<Activity, ActivityDto> activityToDto() {
        return activity -> new ActivityDto(activity.getId(), activity.getName(), activity.isEnabled(),
                                           activity.getDescription(), activity.getTourSpot().getName(),
                                           activity.getTag().getName());
    }

    public ActivityDto activityToDto(Activity activity) {
        return new ActivityDto(activity.getId(), activity.getName(), activity.isEnabled(),
                               activity.getDescription(), activity.getOrder());
    }

    public ActivityDto activityToDtoInDetail(Activity activity) {
        ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);

        for (ActivityInfo activityInfo : activity.getActivityInfoList())
            activityDto.getActivityInfoDtoList().add(modelMapper.map(activityInfo, ActivityInfoDto.class));

        for (ActivityDescription activityDescription : activity.getActivityDescriptionList())
            activityDto.getActivityDescriptionDtoList().add(activityDescriptionToDto(activityDescription));

        for (ActivityTicket activityTicket : activity.getActivityTicketList())
            activityDto.getActivityTicketDtoList().add(activityTicketToDto(activityTicket));

        return activityDto;
    }

    public ActivityDescriptionDto activityDescriptionToDto(ActivityDescription activityDescription) {
        return new ActivityDescriptionDto(activityDescription.getId(), activityDescription.getName(),
                                          activityDescription.isEnabled(), activityDescription.getDescription(),
                                          activityDescription.getOrder());
    }

    public ActivityDescriptionDto activityDescriptionToDtoInDetail(ActivityDescription activityDescription) {
        ActivityDescriptionDto activityDescriptionDto =
                modelMapper.map(activityDescription, ActivityDescriptionDto.class);

        for (ActivityDescriptionInfo activityDescriptionInfo : activityDescription.getActivityDescriptionInfoList())
            activityDescriptionDto.getActivityDescriptionInfoDtoList()
                                  .add(modelMapper.map(activityDescriptionInfo,
                                                       ActivityDescriptionInfoDto.class));

        return activityDescriptionDto;
    }


//  ============================================= ACTIVITY TICKET ================================================  //

    public ActivityTicketDto activityTicketToDto(ActivityTicket activityTicket) {
        return new ActivityTicketDto(activityTicket.getId(), activityTicket.getName(),
                                     activityTicket.isEnabled(), activityTicket.getDescription(),
                                     activityTicket.getOrder());
    }

    public ActivityTicketDto activityTicketToDtoInDetail(ActivityTicket activityTicket) {
        ActivityTicketDto activityTicketDto = modelMapper.map(activityTicket, ActivityTicketDto.class);

        for (ActivityTicketInfo activityTicketInfo : activityTicket.getActivityTicketInfoList())
            activityTicketDto.getActivityTicketInfoDtoList().add(modelMapper.map(activityTicketInfo, ActivityTicketInfoDto.class));

        for (ActivityTicketDescription activityTicketDescription : activityTicket.getActivityTicketDescriptionList())
            activityTicketDto.getActivityTicketDescriptionDtoList().add(activityTicketDescriptionToDto(activityTicketDescription));

        return activityTicketDto;
    }

    public ActivityTicketDescriptionDto activityTicketDescriptionToDto(ActivityTicketDescription activityTicketDescription) {
        return new ActivityTicketDescriptionDto(activityTicketDescription.getId(), activityTicketDescription.getName(),
                                                activityTicketDescription.isEnabled(), activityTicketDescription.getDescription(),
                                                activityTicketDescription.getOrder());
    }

    public ActivityTicketDescriptionDto activityTicketDescriptionToDtoInDetail(ActivityTicketDescription activityTicketDescription) {
        ActivityTicketDescriptionDto activityTicketDescriptionDto =
                modelMapper.map(activityTicketDescription, ActivityTicketDescriptionDto.class);

        for (ActivityTicketDescriptionInfo activityTicketDescriptionInfo :
                activityTicketDescription.getActivityTicketDescriptionInfoList())
            activityTicketDescriptionDto.getActivityTicketDescriptionInfoDtoList()
                                        .add(modelMapper.map(activityTicketDescriptionInfo,
                                                             ActivityTicketDescriptionInfoDto.class));

        return activityTicketDescriptionDto;
    }

//  ============================================ PHOTO ZONE ====================================================================  //

    public PhotoZoneDto photoZoneToDto(PhotoZone photoZone) {
        return new PhotoZoneDto(photoZone.getId(), photoZone.getName(), photoZone.isEnabled(),
                                photoZone.getDescription(), photoZone.getOrder());
    }

    public PhotoZoneDto photoZoneToDtoInDetail(PhotoZone photoZone) {
        PhotoZoneDto photoZoneDto = modelMapper.map(photoZone, PhotoZoneDto.class);
        for (PhotoZoneInfo photoZoneInfo : photoZone.getPhotoZoneInfoList()) {
            photoZoneDto.getPhotoZoneInfoDtoList().add(modelMapper.map(photoZoneInfo, PhotoZoneInfoDto.class));
        }
        return photoZoneDto;
    }

    public List<LocatableAutoCompleteDto> activityToLocatableAutoCompleteDto(List<Activity> activityList) {
        List<LocatableAutoCompleteDto> locatableAutoCompleteDtoList = new ArrayList<>();
        for (Activity activity : activityList) {
            locatableAutoCompleteDtoList.add(new LocatableAutoCompleteDto(activity.getId(),
                                                                          activity.getName(),
                                                                          activity.getLatitude(),
                                                                          activity.getLongitude()));
        }

        return locatableAutoCompleteDtoList;
    }
}

package com.kokotripadmin.dto.tourspot;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kokotripadmin.dto.city.CityDto;
import com.kokotripadmin.dto.common.BaseDto;
import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.common.TradingHourDto;
import com.kokotripadmin.dto.activity.ActivityDto;
import com.kokotripadmin.dto.photozone.PhotoZoneDto;
import com.kokotripadmin.dto.region.RegionDto;
import com.kokotripadmin.entity.tourspot.TourSpotImage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TourSpotDto extends BaseDto {

    private String name;
    private boolean enabled;
    private String description;
    private double latitude;
    private double longitude;
    private String websiteLink;
    private String contact;
    private double averageRate;
    private double popularScore;
    private int numberOfRate;
    private int numberOfWishListSaved;
    private boolean alwaysOpen;
    private String address;
    private String repImagePath;
    private Integer cityId;
    private String cityName;
    private Integer regionId;
    private String regionName;
    private Integer tagId;
    private String tagName;

    private List<TourSpotInfoDto> tourSpotInfoDtoList = new ArrayList<>();
    private List<TourSpotDescriptionDto> tourSpotDescriptionDtoList = new ArrayList<>();
    private List<TourSpotTicketDto> tourSpotTicketDtoList = new ArrayList<>();
    private List<TradingHourDto> tradingHourDtoList = new ArrayList<>();
    private List<ActivityDto> activityDtoList = new ArrayList<>();
    private List<PhotoZoneDto> photoZoneDtoList = new ArrayList<>();
    private List<TourSpotImageDto> tourSpotImageDtoList = new ArrayList<>();

//  dummy property for datatables
    private CityDto city = new CityDto();
    private RegionDto region = new RegionDto();

    public TourSpotDto() {
    }

    public TourSpotDto(Integer id, String name, boolean enabled, String description, String cityName, String regionName) {
        super(id);
        this.name = name;
        this.enabled = enabled;
        this.description = description;
        this.city.setName(cityName);
        this.region.setName(regionName);
    }
}

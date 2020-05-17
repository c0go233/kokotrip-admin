package com.kokotripadmin.dto.activity;

import com.kokotripadmin.dto.common.BaseDto;
import com.kokotripadmin.dto.common.GenericInfoDto;
import com.kokotripadmin.dto.tag.TagDto;
import com.kokotripadmin.dto.tourspot.TourSpotDto;
import com.kokotripadmin.entity.activity.ActivityTicket;
import com.kokotripadmin.viewmodel.tourspot.TourSpotVm;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ActivityDto extends BaseDto {

    private String name;
    private boolean enabled;
    private String description;
    private Integer order;
    private double latitude;
    private double longitude;
    private double averageRate;
    private int numberOfRate;
    private int numberOfWishListSaved;
    private double popularScore;
    private String repImagePath;
    private String repImageFileType;
    private String tourSpotName;
    private Integer tourSpotId;
    private String tagName;
    private Integer tagId;

    private List<ActivityInfoDto> activityInfoDtoList = new ArrayList<>();
    private List<ActivityDescriptionDto> activityDescriptionDtoList = new ArrayList<>();
    private List<ActivityTicketDto> activityTicketDtoList = new ArrayList<>();

    //For datatables output
    private TourSpotDto tourSpot = new TourSpotDto();
    private TagDto tag =  new TagDto();

    public ActivityDto() {
    }

    public ActivityDto(Integer id, String name, boolean enabled, String description, String tourSpotName,
                       String tagName) {
        super(id);
        this.name = name;
        this.enabled = enabled;
        this.description = description;
        this.tourSpot.setName(tourSpotName);
        this.tag.setName(tagName);
    }

    public ActivityDto(Integer id, String name, boolean enabled, String description, Integer order) {
        super(id);
        this.name = name;
        this.enabled = enabled;
        this.description = description;
        this.order = order;
    }
}

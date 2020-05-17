package com.kokotripadmin.dto.photozone;

import com.kokotripadmin.dto.common.BaseDto;
import com.kokotripadmin.entity.photozone.PhotoZoneInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PhotoZoneDto extends BaseDto {

    private String name;
    private boolean enabled;
    private String description;
    private Integer order;
    private double latitude;
    private double longitude;
    private String repImagePath;
    private String repImageFileType;
    private Integer parentTourSpotId;
    private String parentTourSpotName;
    private Integer tourSpotId;
    private String tourSpotName;
    private Integer activityId;
    private String activityName;

    private List<PhotoZoneInfoDto> photoZoneInfoDtoList = new ArrayList<>();

    public PhotoZoneDto() {
    }

    public PhotoZoneDto(Integer id, String name, boolean enabled, String description, Integer order) {
        super(id);
        this.name = name;
        this.enabled = enabled;
        this.description = description;
        this.order = order;
    }
}

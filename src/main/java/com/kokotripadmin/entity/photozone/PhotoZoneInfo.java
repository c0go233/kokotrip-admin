package com.kokotripadmin.entity.photozone;

import com.kokotripadmin.entity.activity.Activity;
import com.kokotripadmin.entity.activity.ActivityInfo;
import com.kokotripadmin.entity.common.BaseDescribableInfoEntity;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.tourspot.TourSpot;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "photo_zone_info")
public class PhotoZoneInfo extends BaseDescribableInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "\"order\"")
    private Integer order;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "rep_image_file_type")
    private String repImageFileType;

    @Column(name = "photo_zone_id", insertable = false, updatable = false)
    private Integer photoZoneId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_zone_id")
    private PhotoZone photoZone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_tour_spot_info_id")
    private TourSpotInfo parentTourSpotInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_info_id")
    private TourSpotInfo tourSpotInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_info_id")
    private ActivityInfo activityInfo;

    public void setForeignEntities(PhotoZone photoZone, TourSpotInfo parentTourSpotInfo, TourSpotInfo tourSpotInfo,
                                   ActivityInfo activityInfo, SupportLanguage supportLanguage) {
        this.photoZone = photoZone;
        this.parentTourSpotInfo = parentTourSpotInfo;
        this.supportLanguage = supportLanguage;
        this.tourSpotInfo = tourSpotInfo;
        this.activityInfo = activityInfo;
    }

    @SuppressWarnings("Duplicates")
    public void denormalize(PhotoZone photoZone) {
        this.enabled = photoZone.isEnabled();
        this.order = photoZone.getOrder();
        this.latitude = photoZone.getLatitude();
        this.longitude = photoZone.getLongitude();
        this.repImagePath = photoZone.getRepImagePath();
        this.repImageFileType = photoZone.getRepImageFileType();
    }

    public void clone(PhotoZone photoZone) {
        this.name = photoZone.getName();
        this.description = photoZone.getDescription();
    }

}

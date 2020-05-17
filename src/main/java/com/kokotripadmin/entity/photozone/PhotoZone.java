package com.kokotripadmin.entity.photozone;


import com.kokotripadmin.dto.photozone.PhotoZoneDto;
import com.kokotripadmin.entity.activity.Activity;
import com.kokotripadmin.entity.common.BaseEntity;
import com.kokotripadmin.entity.tourspot.TourSpot;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "photo_zone")
@Getter
@Setter
public class PhotoZone extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "description")
    private String description;

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

    @Column(name = "parent_tour_spot_id", insertable = false, updatable = false)
    private Integer parentTourSpotId;

    @Column(name = "tour_spot_id", insertable = false, updatable = false)
    private Integer tourSpotId;

    @Column(name = "activity_id", insertable = false, updatable = false)
    private Integer activityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_tour_spot_id")
    private TourSpot parentTourSpot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_id")
    private TourSpot tourSpot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "photoZone",
               fetch = FetchType.LAZY,
               orphanRemoval=true)
    private List<PhotoZoneInfo> photoZoneInfoList = new ArrayList<>();

    public void setForeignEntities(TourSpot parentTourSpot, TourSpot tourSpot, Activity activity) {
        this.parentTourSpot = parentTourSpot;
        setForeignEntities(tourSpot, activity);
    }

    public void setForeignEntities(TourSpot tourSpot, Activity activity) {
        this.tourSpot = tourSpot;
        this.activity = activity;
    }

    @SuppressWarnings("Duplicates")
    public void clone(PhotoZoneDto photoZoneDto) {
        this.name = photoZoneDto.getName();
        this.enabled = photoZoneDto.isEnabled();
        this.description = photoZoneDto.getDescription();
        this.latitude = photoZoneDto.getLatitude();
        this.longitude = photoZoneDto.getLongitude();
        this.repImagePath = photoZoneDto.getRepImagePath();
        this.repImageFileType = photoZoneDto.getRepImageFileType();
    }

    public void denormalize(TourSpot tourSpot, Activity activity) {
        if (tourSpot != null) {
            this.setLatitude(tourSpot.getLatitude());
            this.setLongitude(tourSpot.getLongitude());
        } else if (activity != null) {
            this.setLatitude(activity.getLatitude());
            this.setLongitude(activity.getLongitude());
        }
    }
}

package com.kokotripadmin.entity.activity;

import com.kokotripadmin.dto.activity.ActivityDto;
import com.kokotripadmin.entity.common.BaseEntity;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tourspot.TourSpot;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "activity")
public class Activity extends BaseEntity {

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

    @Column(name = "average_rate")
    private double averageRate;

    @Column(name = "number_of_rate")
    private int numberOfRate;

    @Column(name = "number_of_wish_list_saved")
    private int numberOfWishListSaved;

    @Column(name = "popular_score")
    private double popularScore;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "tour_spot_id", insertable = false, updatable = false)
    private Integer tourSpotId;

    @Column(name = "tag_id", insertable = false, updatable = false)
    private Integer tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_id")
    private TourSpot tourSpot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "activity",
               fetch = FetchType.LAZY,
               orphanRemoval=true)
    private List<ActivityInfo> activityInfoList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "activity",
               fetch = FetchType.LAZY,
               orphanRemoval=true)
    private List<ActivityDescription> activityDescriptionList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "activity",
               fetch = FetchType.LAZY,
               orphanRemoval=true)
    private List<ActivityTicket> activityTicketList = new ArrayList<>();

    public void setForeignEntities(TourSpot tourSpot, Tag tag) {
        this.tourSpot = tourSpot;
        this.tag = tag;
    }

    @SuppressWarnings("Duplicates")
    public void clone(ActivityDto activityDto) {
        this.name = activityDto.getName();
        this.enabled = activityDto.isEnabled();
        this.description = activityDto.getDescription();
        this.latitude = activityDto.getLatitude();
        this.longitude = activityDto.getLongitude();
        this.popularScore = activityDto.getPopularScore();
        this.repImagePath = activityDto.getRepImagePath();
    }
}

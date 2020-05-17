package com.kokotripadmin.entity.tourspot;

import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.common.BaseDescribableInfoEntity;
import com.kokotripadmin.entity.region.Region;
import com.kokotripadmin.entity.tag.TagInfo;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicketInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tour_spot_info")
@Getter
@Setter
public class TourSpotInfo extends BaseDescribableInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "average_rate")
    private double averageRate;

    @Column(name = "number_of_rate")
    private int numberOfRate;

    @Column(name = "popular_score")
    private double popularScore;

    @Column(name = "number_of_wish_list_saved")
    private int numberOfWishListSaved;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "rep_image_file_type")
    private String repImageFileType;

    @Column(name = "always_open")
    private boolean alwaysOpen;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "tour_spot_id", insertable = false, updatable = false)
    private Integer tourSpotId;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Integer regionId;

    @Column(name = "tag_info_id", insertable = false, updatable = false)
    private Integer tagInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_id")
    private TourSpot tourSpot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_info_id")
    private TagInfo tagInfo;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "tourSpotInfo",
               orphanRemoval = true)
    private List<TourSpotDescriptionInfo> tourSpotDescriptionInfoList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "tourSpotInfo",
               orphanRemoval = true)
    private List<TourSpotTicketInfo> tourSpotTicketInfoList = new ArrayList<>();

    public void setForeignEntities(TourSpot tourSpot, SupportLanguage supportLanguage, TagInfo tagInfo) {
        this.tourSpot = tourSpot;
        this.supportLanguage = supportLanguage;
        this.tagInfo = tagInfo;
    }

//  1. we don't need to update average_rate, number_of_rate, and number_of_wish
//     because updating tour_spot will not change any of the fields' values
//     but just have method to sync values between tour_spot and tour_spot_info

    public void denormalize(TourSpot tourSpot, String tagName) {
        denormalize(tourSpot);
        this.tagName = tagName;
    }

    @SuppressWarnings("Duplicates")
    public void denormalize(TourSpot tourSpot) {
        this.city = tourSpot.getCity();
        this.region = tourSpot.getRegion();
        this.enabled = tourSpot.isEnabled();
        this.latitude = tourSpot.getLatitude();
        this.longitude = tourSpot.getLongitude();
        this.alwaysOpen = tourSpot.isAlwaysOpen();
        this.averageRate = tourSpot.getAverageRate();
        this.numberOfRate = tourSpot.getNumberOfRate();
        this.numberOfWishListSaved = tourSpot.getNumberOfWishListSaved();
        this.popularScore = tourSpot.getPopularScore();
        this.repImagePath = tourSpot.getRepImagePath();
        this.repImageFileType = tourSpot.getRepImageFileType();
    }

    public void clone(TourSpot tourSpot) {
        this.name = tourSpot.getName();
        this.description = tourSpot.getDescription();
    }


}

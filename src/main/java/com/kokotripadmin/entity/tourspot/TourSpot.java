package com.kokotripadmin.entity.tourspot;


import com.kokotripadmin.dto.common.TradingHourDto;
import com.kokotripadmin.dto.tourspot.TourSpotDto;
import com.kokotripadmin.entity.activity.Activity;
import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.common.BaseEntity;
import com.kokotripadmin.entity.photozone.PhotoZone;
import com.kokotripadmin.entity.region.Region;
import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.tourspot.ticket.TourSpotTicket;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tour_spot")
@Getter
@Setter
public class TourSpot extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "description")
    private String description;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "website_link")
    private String websiteLink;

    @Column(name = "contact")
    private String contact;

    @Column(name = "average_rate")
    private double averageRate;

    @Column(name = "number_of_rate")
    private int numberOfRate;

    @Column(name = "popular_score")
    private double popularScore;

    @Column(name = "number_of_wish_list_saved")
    private int numberOfWishListSaved;

    @Column(name = "address")
    private String address;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "always_open")
    private boolean alwaysOpen;

    @Column(name = "tag_id", insertable = false, updatable = false)
    private Integer tagId;

    @Column(name = "city_id", insertable = false, updatable = false)
    private Integer cityId;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Integer regionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "tourSpot",
               fetch = FetchType.LAZY,
               orphanRemoval = true)
    private List<TourSpotInfo> tourSpotInfoList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "tourSpot",
               fetch = FetchType.LAZY,
               orphanRemoval = true)
    private List<TourSpotTradingHour> tourSpotTradingHourList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "tourSpot",
               fetch = FetchType.LAZY,
               orphanRemoval = true)
    private List<TourSpotDescription> tourSpotDescriptionList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "tourSpot",
               fetch = FetchType.LAZY,
               orphanRemoval = true)
    private List<TourSpotTicket> tourSpotTicketList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "tourSpot",
               fetch = FetchType.LAZY,
               orphanRemoval = true)
    private List<Activity> activityList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "parentTourSpot",
               fetch = FetchType.LAZY,
               orphanRemoval = true)
    private List<PhotoZone> photoZoneList = new ArrayList<>();

    public void setForeignEntities(City city, Region region, Tag tag) {
        this.city = city;
        this.region = region;
        this.tag = tag;
    }

    @SuppressWarnings("Duplicates")
    public void clone(TourSpotDto tourSpotDto) {
        this.name = tourSpotDto.getName();
        this.enabled = tourSpotDto.isEnabled();
        this.description = tourSpotDto.getDescription();
        this.latitude = tourSpotDto.getLatitude();
        this.longitude = tourSpotDto.getLongitude();
        this.websiteLink = tourSpotDto.getWebsiteLink();
        this.address = tourSpotDto.getAddress();
        this.alwaysOpen = tourSpotDto.isAlwaysOpen();
        this.contact = tourSpotDto.getContact();
        this.popularScore = tourSpotDto.getPopularScore();
        this.repImagePath = tourSpotDto.getRepImagePath();
    }



}

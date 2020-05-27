package com.kokotripadmin.entity.region;

import com.kokotripadmin.dto.region.RegionDto;
import com.kokotripadmin.entity.city.CityImage;
import com.kokotripadmin.entity.common.BaseEntity;
import com.kokotripadmin.entity.city.City;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "region")
@Getter
@Setter
public class Region extends BaseEntity {

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

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "city_id", insertable = false, updatable = false)
    private Integer cityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "region",
               orphanRemoval = true)
    private List<RegionThemeRel> regionThemeRelList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "region",
               orphanRemoval = true)
    private List<RegionInfo> regionInfoList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "region",
               orphanRemoval = true)
    private List<RegionThemeTagRel> regionThemeTagRelList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "region",
               orphanRemoval = true)
    @OrderBy("order asc")
    private List<RegionImage> regionImageList = new ArrayList<>();

    @SuppressWarnings("Duplicates")
    public void clone(RegionDto regionDto) {
        this.name = regionDto.getName();
        this.enabled = regionDto.isEnabled();
        this.description = regionDto.getDescription();
        this.latitude = regionDto.getLatitude();
        this.longitude = regionDto.getLongitude();
        this.repImagePath = regionDto.getRepImagePath();
    }

}

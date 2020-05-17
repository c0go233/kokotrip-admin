package com.kokotripadmin.entity.city;


import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dto.city.CityDto;
import com.kokotripadmin.entity.region.Region;
import com.kokotripadmin.entity.State;
import com.kokotripadmin.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "city")
@Getter
@Setter
public class City extends BaseEntity {

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

    @Column(name = "rep_image_file_type")
    private String repImageFileType;

    @Column(name = "state_id", insertable = false, updatable = false)
    private Integer stateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @OneToMany(mappedBy = "city",
               fetch = FetchType.LAZY)
    private List<Region> regionList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "city",
               fetch = FetchType.LAZY,
               orphanRemoval = true)
    private List<CityInfo> cityInfoList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "city",
               orphanRemoval = true)
    private List<CityThemeRel> cityThemeRelList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "city",
               orphanRemoval = true)
    private List<CityImage> cityImageList = new ArrayList<>();


    @SuppressWarnings("Duplicates")
    public void clone(CityDto cityDto) {
        this.name = cityDto.getName();
        this.enabled = cityDto.isEnabled();
        this.description = cityDto.getDescription();
        this.latitude = cityDto.getLatitude();
        this.longitude = cityDto.getLongitude();
        this.repImagePath = cityDto.getRepImagePath();
        this.repImageFileType = cityDto.getRepImageFileType();
    }

    public void updateInfos() {
        for (CityInfo cityInfo : this.getCityInfoList()) {

            cityInfo.denormalize(this);
            if (cityInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
                cityInfo.clone(this);
        }
    }

}

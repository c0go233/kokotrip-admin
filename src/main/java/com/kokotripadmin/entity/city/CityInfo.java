package com.kokotripadmin.entity.city;


import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.common.BaseDescribableInfoEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "city_info")
@Getter
@Setter
public class CityInfo extends BaseDescribableInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

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

    public void clone(City city) {
        this.name = city.getName();
        this.description = city.getDescription();
    }

    public void denormalize(City city) {
        this.enabled = city.isEnabled();
        this.repImagePath = city.getRepImagePath();
        this.latitude = city.getLatitude();
        this.longitude = city.getLongitude();
    }

    public void setForeignEntities(City city, SupportLanguage supportLanguage) {
        this.city = city;
        this.supportLanguage = supportLanguage;
    }
}

package com.kokotripadmin.entity.region;

import com.kokotripadmin.entity.city.CityInfo;
import com.kokotripadmin.entity.common.BaseDescribableInfoEntity;
import com.kokotripadmin.entity.common.SupportLanguage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "region_info")
@Getter
@Setter
public class RegionInfo extends BaseDescribableInfoEntity {


    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "rep_image_file_type")
    private String repImageFileType;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Integer regionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_info_id")
    private CityInfo cityInfo;

    public void clone(Region region) {
        this.name = region.getName();
        this.description = region.getDescription();
    }

    public void denormalize(Region region) {
        this.enabled = region.isEnabled();
        this.repImagePath = region.getRepImagePath();
        this.repImageFileType = region.getRepImageFileType();
    }

    public void setForeignEntities(CityInfo cityInfo, Region region, SupportLanguage supportLanguage) {
        this.region = region;
        this.supportLanguage = supportLanguage;
        this.cityInfo = cityInfo;
    }

    public void setForeignEntities(CityInfo cityInfo, Region region) {
        setForeignEntities(cityInfo, region, cityInfo.getSupportLanguage());
    }
}

package com.kokotripadmin.entity.region;

import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.common.BaseImageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "region_image")
@Getter
@Setter
public class RegionImage extends BaseImageEntity {

    @Column(name = "region_id", insertable = false, updatable = false)
    private Integer regionId;

    @Column(name = "rep_image")
    private boolean repImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

}

package com.kokotripadmin.entity.region;

import com.kokotripadmin.entity.common.BaseImageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "region_image")
@Getter
@Setter
public class RegionImage extends BaseImageEntity {

    @Column(name = "region_id")
    private int regionId;
}

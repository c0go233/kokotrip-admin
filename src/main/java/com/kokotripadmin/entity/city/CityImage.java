package com.kokotripadmin.entity.city;


import com.kokotripadmin.entity.common.BaseImageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "city_image")
@Getter
@Setter
public class CityImage extends BaseImageEntity {

    @Column(name = "city_id", insertable = false, updatable = false)
    private Integer cityId;

    @Column(name = "rep_image")
    private boolean repImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    public CityImage() {
    }

}

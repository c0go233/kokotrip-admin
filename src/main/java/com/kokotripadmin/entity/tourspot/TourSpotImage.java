package com.kokotripadmin.entity.tourspot;

import com.kokotripadmin.entity.activity.Activity;
import com.kokotripadmin.entity.common.BaseImageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tour_spot_image")
public class TourSpotImage extends BaseImageEntity {

    @Column(name = "tour_spot_id", insertable = false, updatable = false)
    private Integer tourSpotId;

    @Column(name = "rep_image")
    private boolean repImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_id")
    private TourSpot tourSpot;

}

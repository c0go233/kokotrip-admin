package com.kokotripadmin.entity.tourspot;

import com.kokotripadmin.entity.common.BaseImageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tour_spot_description_image")
public class TourSpotDescriptionImage extends BaseImageEntity {

    @Column(name = "tour_spot_id", insertable = false, updatable = false)
    private Integer tourSpotId;

    @Column(name = "tour_spot_description_id", insertable = false, updatable = false)
    private Integer tourSpotDescriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_id")
    private TourSpot tourSpot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_description_id")
    private TourSpotDescription tourSpotDescription;
}

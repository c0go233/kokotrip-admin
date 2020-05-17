package com.kokotripadmin.entity.tourspot;

import com.kokotripadmin.dto.tourspot.TourSpotDescriptionDto;
import com.kokotripadmin.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tour_spot_description")
@Getter
@Setter
public class TourSpotDescription extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "description")
    private String description;

    @Column(name = "\"order\"")
    private int order;

    @Column(name = "popup")
    private boolean popup;

    @Column(name = "tour_spot_id", insertable = false, updatable = false)
    private Integer tourSpotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_id")
    private TourSpot tourSpot;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "tourSpotDescription",
               orphanRemoval = true)
    private List<TourSpotDescriptionInfo> tourSpotDescriptionInfoList = new ArrayList<>();


    @SuppressWarnings("Duplicates")
    public void clone(TourSpotDescriptionDto tourSpotDescriptionDto) {
        this.name = tourSpotDescriptionDto.getName();
        this.enabled = tourSpotDescriptionDto.isEnabled();
        this.description = tourSpotDescriptionDto.getDescription();
        this.order = tourSpotDescriptionDto.getOrder();
        this.popup = tourSpotDescriptionDto.isPopup();
    }

}

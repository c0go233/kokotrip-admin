package com.kokotripadmin.entity.tourspot;

import com.kokotripadmin.entity.common.BaseDescribableInfoEntity;
import com.kokotripadmin.entity.common.SupportLanguage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tour_spot_description_info")
@Getter
@Setter
public class TourSpotDescriptionInfo extends BaseDescribableInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "\"order\"")
    private int order;

    @Column(name = "popup")
    private boolean popup;

    @Column(name = "tour_spot_description_id", insertable = false, updatable = false)
    private Integer tourSpotDescriptionId;

    @Column(name = "tour_spot_info_id", insertable = false, updatable = false)
    private Integer tourSpotInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_description_id")
    private TourSpotDescription tourSpotDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_info_id")
    private TourSpotInfo tourSpotInfo;


    public void setForeignEntities(TourSpotDescription tourSpotDescription,
                                   TourSpotInfo tourSpotInfo,
                                   SupportLanguage supportLanguage) {
        this.tourSpotDescription = tourSpotDescription;
        this.tourSpotInfo = tourSpotInfo;
        this.supportLanguage = supportLanguage;
    }

    public void denormalize(TourSpotDescription tourSpotDescription) {
        this.enabled = tourSpotDescription.isEnabled();
        this.order = tourSpotDescription.getOrder();
        this.popup = tourSpotDescription.isPopup();
    }


    public void clone(TourSpotDescription tourSpotDescription) {
        this.name = tourSpotDescription.getName();
        this.description = tourSpotDescription.getDescription();
    }

}

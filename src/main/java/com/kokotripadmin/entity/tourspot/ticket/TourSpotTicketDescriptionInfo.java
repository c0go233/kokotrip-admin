package com.kokotripadmin.entity.tourspot.ticket;

import com.kokotripadmin.entity.common.BaseDescribableInfoEntity;
import com.kokotripadmin.entity.common.SupportLanguage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tour_spot_ticket_description_info")
public class TourSpotTicketDescriptionInfo extends BaseDescribableInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "\"order\"")
    private int order;

    @Column(name = "popup")
    private boolean popup;

    @Column(name = "tour_spot_ticket_description_id", insertable =  false, updatable =  false)
    private Integer tourSpotTicketDescriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_ticket_info_id")
    private TourSpotTicketInfo tourSpotTicketInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_ticket_description_id")
    private TourSpotTicketDescription tourSpotTicketDescription;

    public void setForeignEntities(TourSpotTicketInfo tourSpotTicketInfo,
                                   TourSpotTicketDescription tourSpotTicketDescription,
                                   SupportLanguage supportLanguage) {
        this.tourSpotTicketInfo = tourSpotTicketInfo;
        this.supportLanguage = supportLanguage;
        this.tourSpotTicketDescription = tourSpotTicketDescription;
    }

    public void denormalize(TourSpotTicketDescription tourSpotTicketDescription) {
        this.enabled = tourSpotTicketDescription.isEnabled();
        this.order = tourSpotTicketDescription.getOrder();
        this.popup = tourSpotTicketDescription.isPopup();
    }

    public void clone(TourSpotTicketDescription tourSpotTicketDescription) {
        this.name = tourSpotTicketDescription.getName();
        this.description = tourSpotTicketDescription.getDescription();
    }

}

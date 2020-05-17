package com.kokotripadmin.entity.tourspot.ticket;

import com.kokotripadmin.entity.common.BaseDescribableInfoEntity;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.tourspot.TourSpotInfo;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Table(name = "tour_spot_ticket_info")
@Getter
@Setter
public class TourSpotTicketInfo extends BaseDescribableInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "\"order\"")
    private Integer order;

    @Column(name = "rep_price")
    private Double repPrice;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "rep_image_file_type")
    private String repImageFileType;

    @Column(name = "tour_spot_ticket_id", insertable = false, updatable = false)
    private Integer tourSpotTicketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_info_id")
    private TourSpotInfo tourSpotInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_ticket_id")
    private TourSpotTicket tourSpotTicket;

    public void setForeignEntities(TourSpotInfo tourSpotInfo, TourSpotTicket tourSpotTicket, SupportLanguage supportLanguage) {
        this.tourSpotInfo = tourSpotInfo;
        this.supportLanguage = supportLanguage;
        this.tourSpotTicket = tourSpotTicket;
    }

    public void denormalize(TourSpotTicket tourSpotTicket) {
        this.enabled = tourSpotTicket.isEnabled();
        this.order = tourSpotTicket.getOrder();
        this.repPrice = tourSpotTicket.getRepPrice();
        this.repImagePath = tourSpotTicket.getRepImagePath();
        this.repImageFileType = tourSpotTicket.getRepImageFileType();
    }

    public void clone(TourSpotTicket tourSpotTicket) {
        this.name = tourSpotTicket.getName();
        this.description = tourSpotTicket.getDescription();
    }

}

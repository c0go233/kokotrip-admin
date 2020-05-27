package com.kokotripadmin.entity.tourspot.ticket;

import com.kokotripadmin.entity.common.BaseImageEntity;
import com.kokotripadmin.entity.tourspot.TourSpot;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tour_spot_ticket_image")
public class TourSpotTicketImage extends BaseImageEntity {

    @Column(name = "tour_spot_ticket_id", insertable = false, updatable = false)
    private Integer tourSpotTicketId;

    @Column(name = "rep_image")
    private boolean repImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_ticket_id")
    private TourSpotTicket tourSpotTicket;

}

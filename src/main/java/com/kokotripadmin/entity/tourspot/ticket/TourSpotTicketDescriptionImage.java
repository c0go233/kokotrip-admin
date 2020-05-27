package com.kokotripadmin.entity.tourspot.ticket;

import com.kokotripadmin.entity.common.BaseImageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "tour_spot_ticket_description_image")
public class TourSpotTicketDescriptionImage extends BaseImageEntity {

    @Column(name = "tour_spot_ticket_id", insertable = false, updatable = false)
    private Integer tourSpotTicketId;

    @Column(name = "tour_spot_ticket_description_id", insertable = false, updatable = false)
    private Integer tourSpotTicketDescriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_ticket_id")
    private TourSpotTicket tourSpotTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_ticket_description_id")
    private TourSpotTicketDescription tourSpotTicketDescription;

}

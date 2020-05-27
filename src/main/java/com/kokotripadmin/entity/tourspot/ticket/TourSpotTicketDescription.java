package com.kokotripadmin.entity.tourspot.ticket;

import com.kokotripadmin.dto.tourspot.TourSpotTicketDescriptionDto;
import com.kokotripadmin.entity.common.BaseEntity;
import com.kokotripadmin.entity.tourspot.TourSpotImage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tour_spot_ticket_description")
public class TourSpotTicketDescription extends BaseEntity {

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

    @Column(name = "tour_spot_ticket_id", insertable = false, updatable = false)
    private Integer tourSpotTicketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_ticket_id")
    private TourSpotTicket tourSpotTicket;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "tourSpotTicketDescription",
               orphanRemoval = true)
    private List<TourSpotTicketDescriptionInfo> tourSpotTicketDescriptionInfoList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "tourSpotTicketDescription",
               orphanRemoval = true)
    @OrderBy("order asc")
    private List<TourSpotTicketDescriptionImage> tourSpotTicketDescriptionImageList = new ArrayList<>();

    public void clone(TourSpotTicketDescriptionDto tourSpotTicketDescriptionDto) {
        this.name = tourSpotTicketDescriptionDto.getName();
        this.enabled = tourSpotTicketDescriptionDto.isEnabled();
        this.description = tourSpotTicketDescriptionDto.getDescription();
        this.order = tourSpotTicketDescriptionDto.getOrder();
        this.popup = tourSpotTicketDescriptionDto.isPopup();
    }

}

package com.kokotripadmin.entity.tourspot.ticket;

import com.kokotripadmin.dto.tourspot.TourSpotTicketDto;
import com.kokotripadmin.entity.common.BaseEntity;
import com.kokotripadmin.entity.tourspot.TourSpot;
import com.kokotripadmin.entity.tourspot.TourSpotImage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tour_spot_ticket")
@Getter
@Setter
public class TourSpotTicket extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "description")
    private String description;

    @Column(name = "\"order\"")
    private Integer order;

    @Column(name = "rep_price")
    private Double repPrice;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "tour_spot_id", insertable = false, updatable = false)
    private Integer tourSpotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_spot_id")
    private TourSpot tourSpot;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "tourSpotTicket",
               orphanRemoval = true)
    private List<TourSpotTicketPrice> tourSpotTicketPriceList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "tourSpotTicket",
               orphanRemoval = true)
    private List<TourSpotTicketInfo> tourSpotTicketInfoList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "tourSpotTicket",
               orphanRemoval = true)
    private List<TourSpotTicketDescription> tourSpotTicketDescriptionList =  new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "tourSpotTicket",
               orphanRemoval = true)
    @OrderBy("order asc")
    private List<TourSpotTicketImage> tourSpotTicketImageList = new ArrayList<>();

    @SuppressWarnings("Duplicates")
    public void clone(TourSpotTicketDto tourSpotTicketDto) {
        this.name = tourSpotTicketDto.getName();
        this.enabled = tourSpotTicketDto.isEnabled();
        this.order = tourSpotTicketDto.getOrder();
        this.description = tourSpotTicketDto.getDescription();
        this.repImagePath = tourSpotTicketDto.getRepImagePath();
    }

}

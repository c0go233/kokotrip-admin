package com.kokotripadmin.entity.activity;

import com.kokotripadmin.dto.activity.ActivityTicketDto;
import com.kokotripadmin.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "activity_ticket")
@Getter
@Setter
public class ActivityTicket extends BaseEntity {

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

    @Column(name = "activity_id", insertable = false, updatable = false)
    private Integer activityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "activityTicket",
               orphanRemoval = true)
    private List<ActivityTicketPrice> activityTicketPriceList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "activityTicket",
               orphanRemoval = true)
    private List<ActivityTicketInfo> activityTicketInfoList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "activityTicket",
               orphanRemoval = true)
    private List<ActivityTicketDescription> activityTicketDescriptionList = new ArrayList<>();

    @SuppressWarnings("Duplicates")
    public void clone(ActivityTicketDto activityTicketDto) {
        this.name = activityTicketDto.getName();
        this.enabled = activityTicketDto.isEnabled();
        this.order = activityTicketDto.getOrder();
        this.description = activityTicketDto.getDescription();
        this.repImagePath = activityTicketDto.getRepImagePath();
    }
}
package com.kokotripadmin.entity.activity;

import com.kokotripadmin.dto.activity.ActivityTicketDescriptionDto;
import com.kokotripadmin.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "activity_ticket_description")
public class ActivityTicketDescription extends BaseEntity {

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

    @Column(name = "activity_ticket_id", insertable = false, updatable = false)
    private Integer activityTicketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_ticket_id")
    private ActivityTicket activityTicket;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "activityTicketDescription",
               orphanRemoval = true)
    @OrderBy("order asc")
    private List<ActivityTicketDescriptionImage> activityTicketDescriptionImageList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "activityTicketDescription",
               orphanRemoval = true)
    private List<ActivityTicketDescriptionInfo> activityTicketDescriptionInfoList = new ArrayList<>();

    public void clone(ActivityTicketDescriptionDto activityTicketDescriptionDto) {
        this.name = activityTicketDescriptionDto.getName();
        this.enabled = activityTicketDescriptionDto.isEnabled();
        this.description = activityTicketDescriptionDto.getDescription();
        this.order = activityTicketDescriptionDto.getOrder();
        this.popup = activityTicketDescriptionDto.isPopup();
    }

}

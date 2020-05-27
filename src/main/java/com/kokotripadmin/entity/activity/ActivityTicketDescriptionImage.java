package com.kokotripadmin.entity.activity;

import com.kokotripadmin.entity.common.BaseImageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "activity_ticket_description_image")
public class ActivityTicketDescriptionImage extends BaseImageEntity {

    @Column(name = "activity_ticket_description_id", insertable = false, updatable = false)
    private Integer activityTicketDescriptionId;

    @Column(name = "activity_ticket_id", insertable = false, updatable = false)
    private Integer activityTicketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_ticket_id")
    private ActivityTicket activityTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_ticket_description_id")
    private ActivityTicketDescription activityTicketDescription;

    public ActivityTicketDescriptionImage() {
    }
}

package com.kokotripadmin.entity.activity;

import com.kokotripadmin.entity.common.BaseImageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "activity_ticket_image")
public class ActivityTicketImage extends BaseImageEntity {

    @Column(name = "activity_ticket_id", insertable = false, updatable = false)
    private Integer activityTicketId;

    @Column(name = "rep_image")
    private boolean repImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_ticket_id")
    private ActivityTicket activityTicket;

    public ActivityTicketImage() {
    }

}

package com.kokotripadmin.entity.activity;

import com.kokotripadmin.entity.common.BaseDescribableInfoEntity;
import com.kokotripadmin.entity.common.SupportLanguage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "activity_ticket_info")
@Getter
@Setter
public class ActivityTicketInfo extends BaseDescribableInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "\"order\"")
    private Integer order;

    @Column(name = "rep_price")
    private Double repPrice;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "activity_ticket_id", insertable = false, updatable = false)
    private Integer activityTicketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_info_id")
    private ActivityInfo activityInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_ticket_id")
    private ActivityTicket activityTicket;

    public void setForeignEntities(ActivityInfo activityInfo, ActivityTicket activityTicket, SupportLanguage supportLanguage) {
        this.activityInfo = activityInfo;
        this.supportLanguage = supportLanguage;
        this.activityTicket = activityTicket;
    }

    public void denormalize(ActivityTicket activityTicket) {
        this.enabled = activityTicket.isEnabled();
        this.order = activityTicket.getOrder();
        this.repPrice = activityTicket.getRepPrice();
        this.repImagePath = activityTicket.getRepImagePath();
    }

    public void clone(ActivityTicket activityTicket) {
        this.name = activityTicket.getName();
        this.description = activityTicket.getDescription();
    }

}

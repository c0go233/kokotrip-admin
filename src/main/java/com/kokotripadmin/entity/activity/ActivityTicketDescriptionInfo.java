package com.kokotripadmin.entity.activity;

import com.kokotripadmin.entity.common.BaseDescribableInfoEntity;
import com.kokotripadmin.entity.common.SupportLanguage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "activity_ticket_description_info")
public class ActivityTicketDescriptionInfo extends BaseDescribableInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "\"order\"")
    private int order;

    @Column(name = "popup")
    private boolean popup;

    @Column(name = "activity_ticket_description_id", insertable =  false, updatable =  false)
    private Integer activityTicketDescriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_ticket_info_id")
    private ActivityTicketInfo activityTicketInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_ticket_description_id")
    private ActivityTicketDescription activityTicketDescription;

    public void setForeignEntities(ActivityTicketInfo activityTicketInfo,
                                   ActivityTicketDescription activityTicketDescription,
                                   SupportLanguage supportLanguage) {
        this.activityTicketInfo = activityTicketInfo;
        this.supportLanguage = supportLanguage;
        this.activityTicketDescription = activityTicketDescription;
    }

    public void denormalize(ActivityTicketDescription activityTicketDescription) {
        this.enabled = activityTicketDescription.isEnabled();
        this.order = activityTicketDescription.getOrder();
        this.popup = activityTicketDescription.isPopup();
    }

    public void clone(ActivityTicketDescription activityTicketDescription) {
        this.name = activityTicketDescription.getName();
        this.description = activityTicketDescription.getDescription();
    }

}

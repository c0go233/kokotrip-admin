package com.kokotripadmin.entity.activity;

import com.kokotripadmin.entity.common.BaseDescribableInfoEntity;
import com.kokotripadmin.entity.common.SupportLanguage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "activity_description_info")
@Getter
@Setter
public class ActivityDescriptionInfo extends BaseDescribableInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "\"order\"")
    private int order;

    @Column(name = "popup")
    private boolean popup;

    @Column(name = "activity_description_id", insertable = false, updatable = false)
    private int activityDescriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_info_id")
    private ActivityInfo activityInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_description_id")
    private ActivityDescription activityDescription;

    public void clone(ActivityDescription activityDescription) {
        this.name = activityDescription.getName();
        this.description = activityDescription.getDescription();
    }

    public void denormalize(ActivityDescription activityDescription) {
        this.enabled = activityDescription.isEnabled();
        this.order = activityDescription.getOrder();
        this.popup = activityDescription.isPopup();
    }

    public void setForeignEntities(ActivityInfo activityInfo,
                                   SupportLanguage supportLanguage,
                                   ActivityDescription activityDescription) {
        this.activityInfo = activityInfo;
        this.supportLanguage = supportLanguage;
        this.activityDescription = activityDescription;
    }

}

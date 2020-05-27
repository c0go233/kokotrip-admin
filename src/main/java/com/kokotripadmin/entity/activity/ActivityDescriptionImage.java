package com.kokotripadmin.entity.activity;

import com.kokotripadmin.entity.common.BaseImageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "activity_description_image")
public class ActivityDescriptionImage extends BaseImageEntity {

    @Column(name = "activity_description_id", insertable = false, updatable = false)
    private Integer activityDescriptionId;

    @Column(name = "activity_id", insertable = false, updatable = false)
    private Integer activityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_description_id")
    private ActivityDescription activityDescription;

    public ActivityDescriptionImage() {
    }
}

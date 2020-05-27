package com.kokotripadmin.entity.activity;

import com.kokotripadmin.dto.activity.ActivityDescriptionDto;
import com.kokotripadmin.entity.common.BaseEntity;
import com.kokotripadmin.entity.tourspot.TourSpotDescriptionImage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activity_description")
@Getter
@Setter
public class ActivityDescription extends BaseEntity {

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

    @Column(name = "activity_id", insertable = false, updatable = false)
    private int activityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "activityDescription",
               orphanRemoval = true)
    private List<ActivityDescriptionInfo> activityDescriptionInfoList = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "activityDescription",
               orphanRemoval = true)
    @OrderBy("order asc")
    private List<ActivityDescriptionImage> activityDescriptionImageList = new ArrayList<>();


    public void clone(ActivityDescriptionDto activityDescriptionDto) {
        this.name = activityDescriptionDto.getName();
        this.enabled = activityDescriptionDto.isEnabled();
        this.description = activityDescriptionDto.getDescription();
        this.order = activityDescriptionDto.getOrder();
        this.popup = activityDescriptionDto.isPopup();
    }
}

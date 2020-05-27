package com.kokotripadmin.entity.activity;

import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.common.BaseImageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "activity_image")
public class ActivityImage extends BaseImageEntity {

    @Column(name = "activity_id", insertable = false, updatable = false)
    private Integer activityId;

    @Column(name = "rep_image")
    private boolean repImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    public ActivityImage() {
    }

}

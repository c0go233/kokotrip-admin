package com.kokotripadmin.entity.photozone;

import com.kokotripadmin.entity.common.BaseImageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "photo_zone_image")
public class PhotoZoneImage extends BaseImageEntity {

    @Column(name = "photo_zone_id", insertable = false, updatable = false)
    private Integer photoZoneId;

    @Column(name = "rep_image")
    private boolean repImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_zone_id")
    private PhotoZone photoZone;
}

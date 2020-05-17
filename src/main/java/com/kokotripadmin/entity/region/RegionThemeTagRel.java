package com.kokotripadmin.entity.region;

import com.kokotripadmin.entity.common.BaseThemeTagRelEntity;
import com.kokotripadmin.entity.tag.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "region_theme_tag_rel")
@Getter
@Setter
public class RegionThemeTagRel extends BaseThemeTagRelEntity {


    @Column(name = "region_theme_rel_id", insertable = false, updatable = false)
    private Integer regionThemeRelId;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Integer regionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_theme_rel_id")
    private RegionThemeRel regionThemeRel;

    public void setForeignEntities(RegionThemeRel regionThemeRel, Tag tag, Region region) {
        this.regionThemeRel = regionThemeRel;
        this.tag = tag;
        this.region = region;
    }
}

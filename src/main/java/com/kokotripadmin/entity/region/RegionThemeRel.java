package com.kokotripadmin.entity.region;


import com.kokotripadmin.entity.common.BaseThemeRelEntity;
import com.kokotripadmin.entity.tag.Theme;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "region_theme_rel")
@Getter
@Setter
public class RegionThemeRel extends BaseThemeRelEntity {

    @Column(name = "region_id", insertable = false, updatable = false)
    private Integer regionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "regionThemeRel",
               orphanRemoval = true)
    private List<RegionThemeTagRel> regionThemeTagRelList =  new ArrayList<>();

    public void setForeignEntities(Region region, Theme theme) {
        this.region = region;
        this.theme = theme;
    }
}

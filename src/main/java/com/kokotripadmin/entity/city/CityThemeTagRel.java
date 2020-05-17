package com.kokotripadmin.entity.city;

import com.kokotripadmin.entity.tag.Tag;
import com.kokotripadmin.entity.common.BaseThemeTagRelEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "city_theme_tag_rel")
@Getter
@Setter
public class CityThemeTagRel extends BaseThemeTagRelEntity {

    @Column(name = "city_theme_rel_id", insertable = false, updatable = false)
    private int cityThemeRelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_theme_rel_id")
    private CityThemeRel cityThemeRel;


    public void setForeignEntities(CityThemeRel cityThemeRel, Tag tag) {
        this.cityThemeRel = cityThemeRel;
        this.tag = tag;
    }

}

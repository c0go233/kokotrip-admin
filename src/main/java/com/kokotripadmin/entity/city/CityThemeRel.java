package com.kokotripadmin.entity.city;

import com.kokotripadmin.entity.tag.Theme;
import com.kokotripadmin.entity.common.BaseThemeRelEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "city_theme_rel")
@Getter
@Setter
public class CityThemeRel extends BaseThemeRelEntity {

//    @Transient
//    private String themeName;

    @Column(name = "city_id", insertable = false, updatable = false)
    private int cityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "cityThemeRel",
               orphanRemoval = true)
    private List<CityThemeTagRel> cityThemeTagRelList =  new ArrayList<>();


    public void setForeignEntities(City city, Theme theme) {
        this.city = city;
        this.theme = theme;
    }



}

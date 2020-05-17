package com.kokotripadmin.entity.common;


import com.kokotripadmin.entity.tag.Theme;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public class BaseThemeRelEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "enabled")
    protected boolean enabled;

    @Column(name = "num_of_all_tag")
    protected int numOfAllTag;

    @Column(name = "theme_id", insertable = false, updatable = false)
    protected int themeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    protected Theme theme;

    public void addNumOfAllTag(int term) {
        if (this.numOfAllTag <= 0 && term < 0) return;
        this.numOfAllTag += term;
    }
}

package com.kokotripadmin.entity.tag;


import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.common.BaseInfoEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "theme_info")
@Getter
@Setter
public class ThemeInfo extends BaseInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "theme_id", insertable = false, updatable = false)
    private Integer themeId;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;

    public void setForeignEntities(Theme theme, SupportLanguage supportLanguage) {
        this.theme = theme;
        this.supportLanguage = supportLanguage;
    }

    public void clone(Theme theme) {
        this.name = theme.getName();
    }

    public void denormalize(Theme theme) {
        this.enabled = theme.isEnabled();
        this.repImagePath = theme.getRepImagePath();
    }
}

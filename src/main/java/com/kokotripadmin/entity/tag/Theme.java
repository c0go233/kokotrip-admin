package com.kokotripadmin.entity.tag;


import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dto.theme.ThemeDto;
import com.kokotripadmin.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theme")
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Theme extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "rep_image_file_type")
    private String repImageFileType;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "theme",
               orphanRemoval = true)
    private List<Tag> tagList =  new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "theme",
               orphanRemoval = true)
    private List<ThemeInfo> themeInfoList = new ArrayList<>();

    public void clone(ThemeDto themeDto) {
        this.name = themeDto.getName();
        this.enabled = themeDto.isEnabled();
        this.repImagePath = themeDto.getRepImagePath();
        this.repImageFileType = themeDto.getRepImageFileType();
    }

    public void updateInfos() {

        for (ThemeInfo themeInfo : this.themeInfoList) {

            themeInfo.denormalize(this);
            if (themeInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
                themeInfo.clone(this);
        }
    }
}

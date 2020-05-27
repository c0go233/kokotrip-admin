package com.kokotripadmin.entity.tag;


import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dto.tag.TagDto;
import com.kokotripadmin.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag")
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tag extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "theme_id", updatable=false, insertable=false)
    private Integer themeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "tag",
               orphanRemoval = true)
    private List<TagInfo> tagInfoList = new ArrayList<>();

    public void clone(TagDto tagDto) {
        this.name = tagDto.getName();
        this.enabled = tagDto.isEnabled();
        this.repImagePath = tagDto.getRepImagePath();
    }

}

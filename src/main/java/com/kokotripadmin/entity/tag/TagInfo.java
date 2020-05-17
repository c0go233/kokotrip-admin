package com.kokotripadmin.entity.tag;


import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.common.BaseInfoEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tag_info")
@Getter
@Setter
public class TagInfo extends BaseInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "rep_image_file_type")
    private String repImageFileType;

    @Column(name = "tag_id", insertable = false, updatable = false)
    private Integer tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public void setForeignEntities(Tag tag, SupportLanguage supportLanguage) {
        this.tag = tag;
        this.supportLanguage = supportLanguage;
    }

    public void clone(Tag tag) {
        this.name = tag.getName();
    }

    public void denormalize(Tag tag) {
        this.repImagePath = tag.getRepImagePath();
        this.repImageFileType = tag.getRepImageFileType();
        this.enabled = tag.isEnabled();
    }



}

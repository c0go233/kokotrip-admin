package com.kokotripadmin.entity.common;


import com.kokotripadmin.entity.tag.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public class BaseThemeTagRelEntity {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "enabled")
    protected boolean enabled;

    @Column(name = "num_of_tag")
    protected int numOfTag;

    @Column(name = "tag_id", insertable = false, updatable = false)
    protected int tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    protected Tag tag;

    public void addNumOfTag(int term) {
        if (this.numOfTag <= 0 && term < 0) return;
        this.numOfTag += term;
    }
}

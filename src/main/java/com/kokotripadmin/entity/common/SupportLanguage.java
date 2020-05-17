package com.kokotripadmin.entity.common;


import com.kokotripadmin.dto.common.SupportLanguageDto;
import com.kokotripadmin.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "support_language")
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SupportLanguage extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "\"order\"")
    private int order;

    @Column(name = "display_name")
    private String displayName;

    public void clone(SupportLanguageDto supportLanguageDto) {
        this.name = supportLanguageDto.getName();
        this.enabled = supportLanguageDto.isEnabled();
        this.order = supportLanguageDto.getOrder();
        this.displayName = supportLanguageDto.getDisplayName();
    }
}

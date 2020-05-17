package com.kokotripadmin.entity.common;

import com.kokotripadmin.dto.common.BaseInfoDto;
import com.kokotripadmin.dto.common.GenericInfoDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@MappedSuperclass
@Getter
@Setter
public class BaseInfoEntity extends BaseEntity {

    @Column(name = "name")
    protected String name;

    @Column(name = "support_language_id", insertable = false, updatable = false)
    protected Integer supportLanguageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "support_language_id")
    protected SupportLanguage supportLanguage;

    public void clone(BaseInfoDto baseInfoDto) {
        this.name = baseInfoDto.getName();
    }

}

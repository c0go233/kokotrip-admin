package com.kokotripadmin.entity.common;

import com.kokotripadmin.dto.common.BaseDescribableInfoDto;
import com.kokotripadmin.dto.common.GenericInfoDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class BaseDescribableInfoEntity extends BaseInfoEntity {

    @Column(name = "description")
    protected String description;

    public void clone(BaseDescribableInfoDto baseDescribableInfoDto) {
        super.clone(baseDescribableInfoDto);
        this.description = baseDescribableInfoDto.getDescription();
    }
}

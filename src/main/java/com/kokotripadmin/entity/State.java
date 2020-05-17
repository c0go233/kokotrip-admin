package com.kokotripadmin.entity;

import com.kokotripadmin.dto.state.StateDto;
import com.kokotripadmin.entity.city.City;
import com.kokotripadmin.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "state")
@Getter
@Setter
public class State extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "state")
    private List<City> cityList = new ArrayList<>();

    public void clone(StateDto stateDto) {
        this.name = stateDto.getName();
        this.enabled = stateDto.isEnabled();
    }
}

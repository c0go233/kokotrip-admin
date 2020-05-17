package com.kokotripadmin.entity.ticket;


import com.kokotripadmin.constant.SupportLanguageEnum;
import com.kokotripadmin.dto.ticket.TicketTypeDto;
import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.common.BaseEntity;
import com.kokotripadmin.entity.tourspot.TourSpotDescription;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket_type")
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TicketType extends BaseEntity {


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
               mappedBy = "ticketType",
               orphanRemoval = true)
    private List<TicketTypeInfo> ticketTypeInfoList = new ArrayList<>();


    public void clone(TicketTypeDto ticketTypeDto) {
        this.name = ticketTypeDto.getName();
        this.enabled = ticketTypeDto.isEnabled();
        this.repImagePath = ticketTypeDto.getRepImagePath();
        this.repImageFileType = ticketTypeDto.getRepImageFileType();
    }

    public void updateInfos() {

        for (TicketTypeInfo ticketTypeInfo : this.ticketTypeInfoList) {

            ticketTypeInfo.denormalize(this);
            if (ticketTypeInfo.getSupportLanguageId() == SupportLanguageEnum.Korean.getId())
                ticketTypeInfo.clone(this);
        }
    }

}

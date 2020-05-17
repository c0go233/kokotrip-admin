package com.kokotripadmin.entity.ticket;

import com.kokotripadmin.entity.common.BaseInfoEntity;
import com.kokotripadmin.entity.common.SupportLanguage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ticket_type_info")
@Getter
@Setter
public class TicketTypeInfo extends BaseInfoEntity {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "rep_image_path")
    private String repImagePath;

    @Column(name = "rep_image_file_type")
    private String repImageFileType;

    @Column(name = "ticket_type_id", insertable = false, updatable = false)
    private Integer ticketTypeId;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    public void setForeignEntities(TicketType ticketType, SupportLanguage supportLanguage) {
        this.ticketType = ticketType;
        this.supportLanguage = supportLanguage;
    }

    public void clone(TicketType ticketType) {
        this.name = ticketType.getName();
    }

    public void denormalize(TicketType ticketType) {
        this.enabled = ticketType.isEnabled();
        this.repImagePath = ticketType.getRepImagePath();
        this.repImageFileType = ticketType.getRepImageFileType();
    }
}

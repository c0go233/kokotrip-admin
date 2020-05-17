package com.kokotripadmin.entity.ticket;

import com.kokotripadmin.entity.common.BaseEntity;
import com.kokotripadmin.entity.ticket.TicketType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class TicketPrice extends BaseEntity {

    @Column(name = "price")
    protected Double price;

    @Column(name = "rep_price")
    protected boolean repPrice;

    @Column(name = "ticket_type_id", insertable = false, updatable = false)
    protected Integer ticketTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_type_id")
    protected TicketType ticketType;

}

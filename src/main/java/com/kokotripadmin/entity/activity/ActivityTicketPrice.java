package com.kokotripadmin.entity.activity;

import com.kokotripadmin.entity.common.BaseEntity;
import com.kokotripadmin.entity.ticket.TicketType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "activity_ticket_price")
@Getter
@Setter
public class ActivityTicketPrice extends BaseEntity {

    @Column(name = "price")
    protected Double price;

    @Column(name = "rep_price")
    protected boolean repPrice;

    @Column(name = "ticket_type_id", insertable = false, updatable = false)
    protected Integer ticketTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_type_id")
    protected TicketType ticketType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_ticket_id")
    private ActivityTicket activityTicket;
}
package com.kokotripadmin.dao.interfaces.ticket;

import com.kokotripadmin.dao.custom.CustomizedTicketTypeDao;
import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.ticket.TicketType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeDao extends GenericDao<TicketType, Integer>, JpaSpecificationExecutor<TicketType>, CustomizedTicketTypeDao {
}

package com.kokotripadmin.dao.interfaces.ticket;

import com.kokotripadmin.dao.interfaces.common.GenericDao;
import com.kokotripadmin.entity.ticket.TicketTypeInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeInfoDao extends GenericDao<TicketTypeInfo, Integer>, JpaSpecificationExecutor<TicketTypeInfo> {
}

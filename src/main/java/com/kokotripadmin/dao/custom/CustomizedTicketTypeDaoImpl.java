package com.kokotripadmin.dao.custom;

import com.kokotripadmin.entity.common.SupportLanguage;
import com.kokotripadmin.entity.common.SupportLanguage_;
import com.kokotripadmin.entity.ticket.TicketType;
import com.kokotripadmin.entity.ticket.TicketType_;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomizedTicketTypeDaoImpl implements CustomizedTicketTypeDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TicketType> findAllByEnabled(boolean ticketTypeEnabled) {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<TicketType> criteriaQuery = criteriaBuilder.createQuery(TicketType.class);
        Root<TicketType> ticketTypeRoot = criteriaQuery.from(TicketType.class);
        criteriaQuery.select(ticketTypeRoot);

        Predicate predicate = criteriaBuilder.equal(ticketTypeRoot.get(TicketType_.enabled), ticketTypeEnabled);
        criteriaQuery.where(predicate);

        Query<TicketType> query = session.createQuery(criteriaQuery);
        query.setCacheable(true);

        return query.getResultList();
    }
}

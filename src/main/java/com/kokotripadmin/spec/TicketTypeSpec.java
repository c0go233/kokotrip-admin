package com.kokotripadmin.spec;

import com.kokotripadmin.entity.ticket.TicketType;
import com.kokotripadmin.entity.ticket.TicketTypeInfo;
import com.kokotripadmin.entity.ticket.TicketTypeInfo_;
import com.kokotripadmin.entity.ticket.TicketType_;
import org.springframework.data.jpa.domain.Specification;

public class TicketTypeSpec {

    public static Specification<TicketType> findByName(String ticketTypeName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TicketType_.name), ticketTypeName);
        };
    }

    public static Specification<TicketTypeInfo> findInfoById(Integer ticketTypeId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TicketTypeInfo_.ticketTypeId), ticketTypeId);
        };
    }

    public static Specification<TicketTypeInfo> findInfoBySupportLanguageId(Integer supportLanguageId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(TicketTypeInfo_.supportLanguageId), supportLanguageId);
        };
    }

    public static Specification<TicketTypeInfo> findInfoByIdAndSupportLanguageId(Integer ticketTypeId, Integer supportLanguageId) {
        return Specification.where(findInfoById(ticketTypeId)).and(findInfoBySupportLanguageId(supportLanguageId));
    }
}

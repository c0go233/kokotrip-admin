package com.kokotripadmin.datatablesdao;


import com.kokotripadmin.entity.common.SupportLanguage;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportLanguageDataTablesDao extends DataTablesRepository<SupportLanguage, Integer> {
}

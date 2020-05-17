package com.kokotripadmin.datatablesdao;

import com.kokotripadmin.entity.tag.Theme;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeDataTablesDao extends DataTablesRepository<Theme, Integer> {
}

package com.kokotripadmin.datatablesdao;

import com.kokotripadmin.entity.tag.Tag;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDataTableDao extends DataTablesRepository<Tag, Integer> {
}

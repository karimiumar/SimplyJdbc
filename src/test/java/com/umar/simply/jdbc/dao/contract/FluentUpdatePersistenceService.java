package com.umar.simply.jdbc.dao.contract;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.dao.UpdatePersistenceService;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.util.List;

public interface FluentUpdatePersistenceService<T> {
    UpdatePersistenceService update(Table table);
    UpdatePersistenceService using(RowMapper<T> rowMapper);
    UpdatePersistenceService where(List<ColumnValue> existingVals);
    UpdatePersistenceService assignNewValues(List<ColumnValue> newVals);
    UpdatePersistenceService of(Long id);
    T execute();
}

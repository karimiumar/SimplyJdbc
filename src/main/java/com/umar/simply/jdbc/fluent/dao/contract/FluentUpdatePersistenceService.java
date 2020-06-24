package com.umar.simply.jdbc.fluent.dao.contract;

import com.umar.simply.jdbc.fluent.dao.UpdatePersistenceService;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.util.List;
import java.util.Optional;
import com.umar.simply.jdbc.ResultSetMapper;

public interface FluentUpdatePersistenceService<T> {
    UpdatePersistenceService update(Table table);
    UpdatePersistenceService using(ResultSetMapper<T> rowMapper);
    UpdatePersistenceService where(List<ColumnValue<?>> existingVals);
    UpdatePersistenceService assignNewValues(List<ColumnValue<?>> newVals);
    UpdatePersistenceService of(int id);
    Optional<T> execute();
}

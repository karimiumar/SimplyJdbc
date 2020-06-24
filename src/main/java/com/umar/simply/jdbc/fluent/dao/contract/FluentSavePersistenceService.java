package com.umar.simply.jdbc.fluent.dao.contract;

import com.umar.simply.jdbc.fluent.dao.SavePersistenceService;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.util.List;
import com.umar.simply.jdbc.ResultSetMapper;

public interface FluentSavePersistenceService<T> {

    SavePersistenceService<T> save(Table table);
    SavePersistenceService<T> using(ResultSetMapper<T> rowMapper);
    SavePersistenceService<T> withValues(List<ColumnValue<?>> newVals);
    T execute();
}

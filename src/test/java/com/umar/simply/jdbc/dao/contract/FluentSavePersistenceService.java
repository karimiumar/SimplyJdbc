package com.umar.simply.jdbc.dao.contract;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.dao.SavePersistenceService;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.util.List;

public interface FluentSavePersistenceService<T> {

    SavePersistenceService<T> save(Table table);
    SavePersistenceService<T> using(RowMapper<T> rowMapper);
    SavePersistenceService<T> withValues(List<ColumnValue> newVals);
    T execute();
}

package com.umar.simply.jdbc.fluent.dao.contract;

import com.umar.simply.jdbc.dml.operations.api.SelectFunction;
import com.umar.simply.jdbc.fluent.dao.DeletePersistenceService;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.util.List;
import com.umar.simply.jdbc.ResultSetMapper;

public interface FluentDeletePersistenceService<T> {
    DeletePersistenceService<T> from(Table table);
    DeletePersistenceService<T> where(SelectFunction op);
    DeletePersistenceService<T> where();
    DeletePersistenceService<T> anyColumnValues(List<ColumnValue<?>> columnValues);
    DeletePersistenceService<T> using(ResultSetMapper<T> rowMapper);
    void execute();
}

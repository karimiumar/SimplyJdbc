package com.umar.simply.jdbc.fluent.dao.contract;

import com.umar.simply.jdbc.fluent.dao.DeletePersistenceService;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.util.List;
import com.umar.simply.jdbc.ResultSetMapper;

public interface FluentDeletePersistenceService<T> {
    DeletePersistenceService from(Table table);
    DeletePersistenceService where(SelectOp op);
    DeletePersistenceService where();
    DeletePersistenceService anyColumnValues(List<ColumnValue> columnValues);
    DeletePersistenceService using(ResultSetMapper<T> rowMapper);
    void execute();
}

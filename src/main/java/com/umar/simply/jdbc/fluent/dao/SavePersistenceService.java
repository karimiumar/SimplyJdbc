package com.umar.simply.jdbc.fluent.dao;

import com.umar.simply.jdbc.fluent.dao.contract.FluentSavePersistenceService;
import com.umar.simply.jdbc.dml.operations.InsertOp;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static com.umar.simply.jdbc.meta.ColumnValue.set;
import com.umar.simply.jdbc.ResultSetMapper;

public class SavePersistenceService<T> extends AbstractPersistenceService<T> implements FluentSavePersistenceService<T> {

    private final InsertOp sql = InsertOp.create();
    private ResultSetMapper<T> rowMapper;
    private Table table;

    public SavePersistenceService(final Connection connection) {
        super(connection);
    }

    @Override
    public SavePersistenceService<T> save(Table table) {
        this.table = table;
        sql.intoTable(table);
        return this;
    }

    @Override
    public SavePersistenceService<T> using(ResultSetMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
        return this;
    }

    @Override
    public T execute() {
        int id = getSavedResult(sql);
        Optional<T> optional = findById(table,rowMapper,set(table.getIdColumn(),id));
        return optional.get();
    }

    @Override
    public SavePersistenceService<T> withValues(List<ColumnValue> newVals) {
        sql.columnValues(newVals);
        return this;
    }
}

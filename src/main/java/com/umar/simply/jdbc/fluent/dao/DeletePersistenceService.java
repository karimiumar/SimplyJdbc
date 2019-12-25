package com.umar.simply.jdbc.fluent.dao;

import com.umar.simply.jdbc.fluent.dao.contract.FluentDeletePersistenceService;
import com.umar.simply.jdbc.dml.operations.DeleteOp;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.sql.Connection;
import java.util.List;
import com.umar.simply.jdbc.ResultSetMapper;

public class DeletePersistenceService<T> extends AbstractPersistenceService<T> implements FluentDeletePersistenceService<T> {

    private ResultSetMapper<T> rowMapper;

    DeleteOp sql = DeleteOp.create();

    public DeletePersistenceService(final Connection connection) {
        super(connection);
    }

    @Override
    public DeletePersistenceService from(Table table) {
        sql.deleteFrom(table);
        return this;
    }

    @Override
    public DeletePersistenceService where(SelectOp op) {
        sql.WHERE(op);
        return this;
    }

    @Override
    public DeletePersistenceService where() {
        sql.WHERE();
        return this;
    }

    @Override
    public DeletePersistenceService anyColumnValues(List<ColumnValue> columnValues) {
        sql.anyColumnValues(getValuesArray(columnValues));
        return this;
    }

    @Override
    public DeletePersistenceService using(ResultSetMapper rowMapper) {
        this.rowMapper = rowMapper;
        return this;
    }

    @Override
    public void execute() {
        getSavedResult(sql);
    }
}

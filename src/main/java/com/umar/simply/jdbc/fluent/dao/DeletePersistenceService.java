package com.umar.simply.jdbc.fluent.dao;

import com.umar.simply.jdbc.dml.operations.api.DeleteFunction;
import com.umar.simply.jdbc.dml.operations.api.SelectFunction;
import com.umar.simply.jdbc.fluent.dao.contract.FluentDeletePersistenceService;
import com.umar.simply.jdbc.dml.operations.DeleteOp;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.sql.Connection;
import java.util.List;
import com.umar.simply.jdbc.ResultSetMapper;

public class DeletePersistenceService<T> extends AbstractPersistenceService<T> implements FluentDeletePersistenceService<T> {

    DeleteFunction sql = DeleteOp.create();

    public DeletePersistenceService(final Connection connection) {
        super(connection);
    }

    @Override
    public DeletePersistenceService<T> from(Table table) {
        sql.DELETE_FROM(table);
        return this;
    }

    @Override
    public DeletePersistenceService<T> where(SelectFunction op) {
        sql.WHERE(op);
        return this;
    }

    @Override
    public DeletePersistenceService<T> where() {
        sql.WHERE();
        return this;
    }

    @Override
    public DeletePersistenceService<T> anyColumnValues(List<ColumnValue<?>> columnValues) {
        sql.anyColumnValues(getValuesArray(columnValues));
        return this;
    }

    @Override
    public DeletePersistenceService<T> using(ResultSetMapper<T> rowMapper) {
        return this;
    }

    @Override
    public void execute() {
        getSavedResult(sql);
    }
}

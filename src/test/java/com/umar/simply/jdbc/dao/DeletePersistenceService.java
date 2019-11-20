package com.umar.simply.jdbc.dao;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.dao.contract.FluentDeletePersistenceService;
import com.umar.simply.jdbc.dml.operations.DeleteOp;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DeletePersistenceService<T> extends AbstractPersistenceService<T> implements FluentDeletePersistenceService<T> {

    private RowMapper<T> rowMapper;

    DeleteOp sql = DeleteOp.create();

    @Override
    public DeletePersistenceService from(Table table) {
        sql.deleteFrom(table);
        return this;
    }

    @Override
    public DeletePersistenceService where(SelectOp op) {
        sql.where(op);
        return this;
    }

    @Override
    public DeletePersistenceService where() {
        sql.where();
        return this;
    }

    @Override
    public DeletePersistenceService anyColumnValues(List<ColumnValue> columnValues) {
        sql.anyColumnValues(getValuesArray(columnValues));
        return this;
    }

    @Override
    public DeletePersistenceService using(RowMapper rowMapper) {
        this.rowMapper = rowMapper;
        return this;
    }

    @Override
    public void execute() {
        try(Connection connection = util.getConnection()){
            getSavedResult(sql, connection);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

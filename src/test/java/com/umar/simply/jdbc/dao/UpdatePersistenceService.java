package com.umar.simply.jdbc.dao;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.dao.contract.FluentUpdatePersistenceService;
import com.umar.simply.jdbc.dml.operations.UpdateOp;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.umar.simply.jdbc.meta.ColumnValue.set;

public class UpdatePersistenceService<T> extends AbstractPersistenceService<T> implements FluentUpdatePersistenceService<T> {

    private UpdateOp sql = new UpdateOp();
    private RowMapper<T> rowMapper;
    private Long id;
    private Table table;

    @Override
    public UpdatePersistenceService update(Table table) {
        this.table = table;
        sql.table(table);
        return this;
    }

    @Override
    public UpdatePersistenceService using(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
        return this;
    }

    @Override
    public UpdatePersistenceService assignNewValues(List<ColumnValue> newVals) {
        sql.setColumnValues(getValuesArray(newVals));
        return this;
    }

    @Override
    public UpdatePersistenceService where(List<ColumnValue> existingVals) {
        sql.where().columnValueEq(getValuesArray(existingVals));
        return this;
    }

    @Override
    public UpdatePersistenceService of(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public T execute() {
        try(Connection connection = util.getConnection()){
            getSavedResult(sql, connection);
            Optional<T> optional = findById(table,rowMapper,set(table.getIdColumn(),id));
            return optional.get();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.umar.simply.jdbc.dao;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.dao.contract.FluentSavePersistenceService;
import com.umar.simply.jdbc.dml.operations.InsertOp;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.umar.simply.jdbc.meta.ColumnValue.set;

public class SavePersistenceService<T> extends AbstractPersistenceService<T> implements FluentSavePersistenceService<T> {

    private InsertOp sql = InsertOp.create();
    private RowMapper<T> rowMapper;
    private Table table;

    @Override
    public SavePersistenceService<T> save(Table table) {
        this.table = table;
        sql.intoTable(table);
        return this;
    }

    @Override
    public SavePersistenceService<T> using(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
        return this;
    }

    @Override
    public T execute() {
        try(Connection connection = util.getConnection()){
            Long id = getSavedResult(sql, connection);
            Optional<T> optional = findById(table,rowMapper,set(table.getIdColumn(),id));
            return optional.get();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SavePersistenceService<T> withValues(List<ColumnValue> newVals) {
        sql.columnValues(newVals);
        return this;
    }
}

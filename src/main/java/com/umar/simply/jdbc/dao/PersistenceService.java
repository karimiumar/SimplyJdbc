package com.umar.simply.jdbc.dao;

import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.util.List;
import java.util.Optional;
import com.umar.simply.jdbc.ResultSetMapper;

/**
 *
 * This is UGLY so deprecated. Use Fluent Style Programming
 */
@Deprecated
public interface PersistenceService<T> {
    List<T> select(Table table, ResultSetMapper<T> rowMapper, List<ColumnValue> columnValues);
    Optional<T> save(Table table, ResultSetMapper<T> rowMapper, List<ColumnValue> columnValues);
    T update(Table table, ResultSetMapper<T> rowMapper, List<ColumnValue> columnValuesToSet, List<ColumnValue> clauseValues, int dbSequence);
    Optional<T> find(Table table, ResultSetMapper<T> rowMapper, List<ColumnValue> columnValues);
    Optional<T> findById(Table table, ResultSetMapper<T> rowMapper, ColumnValue idColumn);
    List<T> getAll(Table table, ResultSetMapper<T> rowMapper);
    int count(Table table, ResultSetMapper<T> rowMapper, Column column);
}

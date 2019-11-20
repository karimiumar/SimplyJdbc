package com.umar.simply.jdbc.dao.async;

import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Deprecated
public interface PersistenceServiceAsync<T> {
    CompletableFuture<List<T>> select(Table[] tables, Column...columns);
    CompletableFuture<T> save(Table table, ColumnValue...columnValues);
    CompletableFuture<T> delete(Table table, ColumnValue...columnValues);
    CompletableFuture<T> find(Table table, ColumnValue...columnValues);
    CompletableFuture<Integer> count(Table table, ColumnValue...columnValues);
}

package com.umar.simply.jdbc.fluent.dao.contract;

import com.umar.simply.jdbc.fluent.dao.QueryService;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Index;
import com.umar.simply.jdbc.meta.Table;

import java.util.List;
import com.umar.simply.jdbc.ResultSetMapper;

public interface FluentQueryService<T> {
    QueryService<T> all();
    QueryService<T> all(List<String> aliases);
    QueryService<T> from(Table table);
    QueryService<T> where();
    QueryService<T> whereIn(Column<T> column, List<ColumnValue> values);
    QueryService<T> where(Column column);
    QueryService<T> values(List<ColumnValue> values);
    QueryService<T> column(List<Column> columns);
    QueryService<T> column(Column<T> column);
    QueryService<T> columnEq(List<ColumnValue> columnValues);
    QueryService<T> columnEq(ColumnValue columnValue);
    QueryService<T> ne(Column<T> condition);
    QueryService<T> not();
    QueryService<T> is();
    QueryService<T> nul();
    QueryService<T> and();
    QueryService<T> and(Column<T> column);
    QueryService<T> or();
    QueryService<T> ltCol(Column<T> column);
    QueryService<T> gtCol(Column<T> column);
    QueryService<T> geCol(Column<T> column);
    QueryService<T> leCol(Column<T> column);
    QueryService<T> gt();
    QueryService<T> lt();
    QueryService<T> ge();
    QueryService<T> eq(Column condition);
    QueryService<T> eq();
    QueryService<T> eq(ColumnValue value);
    QueryService<T> eq(String condition);
    QueryService<T> gt(ColumnValue value);
    QueryService<T> lt(ColumnValue value);
    QueryService<T> ge(ColumnValue columnValue);
    QueryService<T> le(ColumnValue columnValue);
    QueryService<T> upper();
    QueryService<T> having();
    QueryService<T> from(List<Table> tables);
    QueryService<T> year(Column column);
    QueryService<T> distinct();
    QueryService<T> distinct(Column column);
    QueryService<T> count(Column column);
    QueryService<T> count(SelectOp op);
    QueryService<T> max(Column column);
    QueryService<T> min(Column column);
    QueryService<T> avg(Column column);
    QueryService<T> sum(Column column);
    Column<T> sumOfColumn(Column column);
    QueryService<T> exists(SelectOp op);
    QueryService<T> between(List<ColumnValue> columnValues);
    QueryService<T> between(SelectOp op);
    QueryService<T> like(String pattern);
    QueryService<T> in(SelectOp op);
    QueryService<T> in(List<ColumnValue> columnValues);
    QueryService<T> limit(int n);
    QueryService<T> offset(int n);
    QueryService<T> as(String alias);
    QueryService<T> orderBy(Column column);
    QueryService<T> orderBy();
    QueryService<T> asc();
    QueryService<T> desc();
    QueryService<T> groupBy(List<Column> columns);
    QueryService<T> groupBy(Column column);
    QueryService<T> with(List<Column> columns);
    QueryService<T> with(Column column);
    QueryService<T> using(Column column);
    QueryService<T> minus();
    QueryService<T> inner();
    QueryService<T> join();
    QueryService<T> join(Table table);
    QueryService<T> join(SelectOp query);
    QueryService<T> left();
    QueryService<T> right();
    QueryService<T> union();
    QueryService<T> on();
    QueryService<T> on(Column column);
    QueryService<T> any();
    QueryService<T> some();
    QueryService<T> select(Column column);
    QueryService<T> table(Table table);
    QueryService<T> select(List<Column> columns);
    QueryService<T> select();
    QueryService<T> withIndex(Index index);
    List<?> execute();
    QueryService<T> using(ResultSetMapper<T> rowMapper);
    SelectOp getSQL();
}

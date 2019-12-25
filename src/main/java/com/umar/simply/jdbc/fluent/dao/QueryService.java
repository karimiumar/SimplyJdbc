package com.umar.simply.jdbc.fluent.dao;

import com.umar.simply.jdbc.fluent.dao.contract.FluentQueryService;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Index;
import com.umar.simply.jdbc.meta.Table;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import com.umar.simply.jdbc.ResultSetMapper;

public class QueryService<T> extends AbstractPersistenceService<T> implements FluentQueryService<T> {

    private final SelectOp sql = SelectOp.create();
    protected ResultSetMapper<T> rowMapper;

    public QueryService(final Connection connection) {
        super(connection);
    }

    @Override
    public QueryService<T> all() {
        sql.all();
        return this;
    }
    
    @Override
    public QueryService<T> all(List<String> aliases) {
        sql.all(aliases);
        return this;
    }

    @Override
    public QueryService<T> from(Table table) {
        sql.FROM(table);
        return this;
    }

    @Override
    public QueryService<T> where() {
        sql.WHERE();
        return this;
    }

    @Override
    public QueryService<T> where(Column column) {
        sql.WHERE(column);
        return this;
    }

    @Override
    public QueryService<T> values(List<ColumnValue> columnValues) {
        sql.values(getValuesArray(columnValues));
        return this;
    }

    @Override
    public QueryService<T> column(List<Column> columns) {
        sql.column(columns);
        return this;
    }

    @Override
    public QueryService<T> column(Column<T> column) {
        sql.column(column);
        return this;
    }

    @Override
    public QueryService<T> columnEq(List<ColumnValue> columnValues) {
        sql.columnEq(getValuesArray(columnValues));
        return this;
    }

    @Override
    public QueryService<T> columnEq(ColumnValue columnValue) {
        sql.columnEq(columnValue);
        return this;
    }

    @Override
    public QueryService<T> ne(Column<T> condition) {
        sql.NE(condition);
        return this;
    }

    @Override
    public QueryService<T> not() {
        sql.NOT();
        return this;
    }

    @Override
    public QueryService<T> is() {
        sql.IS();
        return this;
    }

    @Override
    public QueryService<T> nul() {
        sql.NULL();
        return this;
    }

    @Override
    public QueryService<T> and() {
        sql.AND();
        return this;
    }

    @Override
    public QueryService<T> and(Column<T> column) {
        sql.AND(column);
        return this;
    }

    @Override
    public QueryService<T> or() {
        sql.OR();
        return this;
    }

    @Override
    public QueryService<T> gt() {
        sql.GT();
        return this;
    }

    @Override
    public QueryService<T> lt() {
        sql.LT();
        return this;
    }

    @Override
    public QueryService<T> ge() {
        sql.GE();
        return this;
    }

    @Override
    public QueryService<T> eq(Column condition) {
        sql.EQ(condition);
        return this;
    }

    @Override
    public QueryService<T> eq(String condition) {
        sql.EQ(condition);
        return this;
    }

    @Override
    public QueryService<T> eq() {
        sql.EQ();
        return this;
    }

    @Override
    public QueryService<T> eq(ColumnValue value) {
        sql.EQ(value);
        return this;
    }

    @Override
    public QueryService<T> gt(ColumnValue value) {
        sql.GT(value);
        return this;
    }

    @Override
    public QueryService<T> lt(ColumnValue value) {
        sql.LT(value);
        return this;
    }

    @Override
    public QueryService<T> ge(ColumnValue columnValue) {
        sql.GE(columnValue);
        return this;
    }

    @Override
    public QueryService<T> le(ColumnValue columnValue) {
        sql.LE(columnValue);
        return this;
    }

    @Override
    public QueryService<T> upper() {
        sql.UPPER();
        return this;
    }

    @Override
    public QueryService<T> having() {
        sql.HAVING();
        return this;
    }

    @Override
    public QueryService<T> from(List<Table> tables) {
        sql.FROM(tables);
        return this;
    }

    @Override
    public QueryService<T> year(Column column) {
        sql.YEAR(column);
        return this;
    }

    @Override
    public QueryService<T> distinct() {
        sql.DISTINCT();
        return this;
    }

    @Override
    public QueryService<T> distinct(Column column) {
        sql.DISTINCT(column);
        return this;
    }

    @Override
    public QueryService<T> count(Column column) {
        sql.COUNT(column);
        return this;
    }

    @Override
    public QueryService<T> count(SelectOp op) {
        sql.COUNT(op);
        return this;
    }

    @Override
    public QueryService<T> max(Column column) {
        sql.MAX(column);
        return this;
    }

    @Override
    public QueryService<T> min(Column column) {
        sql.MIN(column);
        return this;
    }

    @Override
    public QueryService<T> avg(Column column) {
        sql.AVG(column);
        return this;
    }

    @Override
    public QueryService<T> sum(Column column) {
        sql.SUM(column);
        return this;
    }

    @Override
    public Column<T> sumOfColumn(Column column) {
        Column c = Column.column(column.getColumnName());
        sql.SUM(column);
        return c;
    }

    @Override
    public QueryService<T> exists(SelectOp op) {
        sql.EXISTS(op);
        return this;
    }

    @Override
    public QueryService<T> between(List<ColumnValue> columnValues) {
        sql.BETWEEN(columnValues);
        return this;
    }

    @Override
    public QueryService<T> between(SelectOp op) {
        sql.BETWEEN(op);
        return this;
    }

    @Override
    public QueryService<T> like(String pattern) {
        sql.LIKE(pattern);
        return this;
    }

    @Override
    public QueryService<T> in(List<ColumnValue> columnValues) {
        sql.IN(columnValues);
        return this;
    }
    
    @Override
    public QueryService<T> in(SelectOp op) {
        sql.IN(op);
        return this;
    }

    @Override
    public QueryService<T> limit(int n) {
        sql.LIMIT(n);
        return this;
    }

    @Override
    public QueryService<T> offset(int n) {
        sql.OFFSET(n);
        return this;
    }

    @Override
    public QueryService<T> as(String alias) {
        sql.AS(alias);
        return this;
    }

    @Override
    public QueryService<T> orderBy(Column column) {
        sql.ORDERBY(column);
        return this;
    }

    @Override
    public QueryService<T> orderBy() {
        sql.ORDERBY();
        return this;
    }
    
    @Override
    public QueryService<T> asc() {
        sql.ASC();
        return this;
    }

    @Override
    public QueryService<T> desc() {
        sql.DESC();
        return this;
    }

    @Override
    public QueryService<T> groupBy(List<Column> columns) {
        sql.GROUPBY(columns);
        return this;
    }

    @Override
    public QueryService<T> groupBy(Column column) {
        sql.GROUPBY(column);
        return this;
    }

    @Override
    public QueryService<T> with(List<Column> columns) {
        sql.with(columns);
        return this;
    }

    @Override
    public QueryService<T> with(Column column) {
        sql.with(column);
        return this;
    }

    @Override
    public QueryService<T> using(Column column) {
        sql.USING(column);
        return this;
    }

    @Override
    public QueryService<T> table(Table table) {
        sql.TABLE(table);
        return this;
    }

    @Override
    public QueryService<T> minus() {
        sql.MINUS();
        return this;
    }

    @Override
    public QueryService<T> inner() {
        sql.INNER();
        return this;
    }

    @Override
    public QueryService<T> join() {
        sql.JOIN();
        return this;
    }
    
    @Override
    public QueryService<T> join(Table table) {
        sql.JOIN(table);
        return this;
    }

    @Override
    public QueryService<T> join(SelectOp query) {
        sql.JOIN(query);
        return this;
    }

    @Override
    public QueryService<T> left() {
        sql.LEFT();
        return this;
    }

    @Override
    public QueryService<T> right() {
        sql.RIGHT();
        return this;
    }

    @Override
    public QueryService<T> union() {
        sql.UNION();
        return this;
    }

    @Override
    public QueryService<T> on() {
        sql.ON();
        return this;
    }
    
    @Override
    public QueryService<T> on(Column column) {
        sql.ON(column);
        return this;
    }

    @Override
    public QueryService<T> any() {
        sql.ANY();
        return this;
    }

    @Override
    public QueryService<T> some() {
        sql.SOME();
        return this;
    }

    @Override
    public QueryService<T> select(Column column) {
        sql.SELECT(column);
        return this;
    }

    @Override
    public QueryService<T> select(List<Column> columns) {
        sql.SELECT(columns);
        return this;
    }

    @Override
    public QueryService<T> select() {
        sql.SELECT();
        return this;
    }

    @Override
    public QueryService<T> withIndex(Index index) {
        sql.WITHINDEX(index);
        return this;
    }

    @Override
    public SelectOp getSQL() {
        return sql;
    }

    @Override
    public QueryService using(ResultSetMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
        return this;
    }

    @Override
    public List<?> execute() {
        List list = new ArrayList<>();
        getMappedResult(rowMapper, list, sql);
        return list;
    }
}

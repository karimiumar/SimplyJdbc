package com.umar.simply.jdbc.fluent.dao;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.fluent.dao.contract.FluentQueryService;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Index;
import com.umar.simply.jdbc.meta.Table;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class QueryService<T> extends AbstractPersistenceService<T> implements FluentQueryService<T> {

    private final SelectOp sql = SelectOp.create();
    protected RowMapper<T> rowMapper;

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
        sql.from(table);
        return this;
    }

    @Override
    public QueryService<T> where() {
        sql.where();
        return this;
    }

    @Override
    public QueryService<T> whereIn(Column<T> column, List<ColumnValue> columnValues) {
        sql.whereIn(column, columnValues);
        return this;
    }

    @Override
    public QueryService<T> where(Column column) {
        sql.where(column);
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
    public QueryService<T> columnValueEq(List<ColumnValue> columnValues) {
        sql.columnValueEq(getValuesArray(columnValues));
        return this;
    }

    @Override
    public QueryService<T> columnValueEq(ColumnValue columnValue) {
        sql.columnValueEq(columnValue);
        return this;
    }

    @Override
    public QueryService<T> columnEq(Column<T> column) {
        sql.columnEq(column);
        return this;
    }

    @Override
    public QueryService<T> ne(Column<T> condition) {
        sql.ne(condition);
        return this;
    }

    @Override
    public QueryService<T> not() {
        sql.not();
        return this;
    }

    @Override
    public QueryService<T> is() {
        sql.is();
        return this;
    }

    @Override
    public QueryService<T> nul() {
        sql.nul();
        return this;
    }

    @Override
    public QueryService<T> and() {
        sql.and();
        return this;
    }

    @Override
    public QueryService<T> and(Column<T> column) {
        sql.and(column);
        return this;
    }

    @Override
    public QueryService<T> beginComplex() {
        sql.beginComplex();
        return this;
    }

    @Override
    public QueryService<T> endComplex() {
        sql.endComplex();
        return this;
    }

    @Override
    public QueryService<T> or() {
        sql.or();
        return this;
    }

    @Override
    public QueryService<T> ltCol(Column<T> column) {
        sql.ltCol(column);
        return this;
    }

    @Override
    public QueryService<T> gtCol(Column<T> column) {
        sql.gtCol(column);
        return this;
    }

    @Override
    public QueryService<T> geCol(Column<T> column) {
        sql.geCol(column);
        return this;
    }

    @Override
    public QueryService<T> leCol(Column<T> column) {
        sql.leCol(column);
        return this;
    }

    @Override
    public QueryService<T> gt() {
        sql.gt();
        return this;
    }

    @Override
    public QueryService<T> lt() {
        sql.lt();
        return this;
    }

    @Override
    public QueryService<T> ge() {
        sql.ge();
        return this;
    }

    @Override
    public QueryService<T> eq(Column condition) {
        sql.eq(condition);
        return this;
    }

    @Override
    public QueryService<T> eq(String condition) {
        sql.eq(condition);
        return this;
    }

    @Override
    public QueryService<T> eq() {
        sql.eq();
        return this;
    }

    @Override
    public QueryService<T> eq(ColumnValue value) {
        sql.eq(value);
        return this;
    }

    @Override
    public QueryService<T> gt(ColumnValue value) {
        sql.gt(value);
        return this;
    }

    @Override
    public QueryService<T> lt(ColumnValue value) {
        sql.lt(value);
        return this;
    }

    @Override
    public QueryService<T> ge(ColumnValue columnValue) {
        sql.ge(columnValue);
        return this;
    }

    @Override
    public QueryService<T> le(ColumnValue columnValue) {
        sql.le(columnValue);
        return this;
    }

    @Override
    public QueryService<T> upper() {
        sql.upper();
        return this;
    }

    @Override
    public QueryService<T> having() {
        sql.having();
        return this;
    }

    @Override
    public QueryService<T> from(List<Table> tables) {
        sql.from(tables);
        return this;
    }

    @Override
    public QueryService<T> year(Column column) {
        sql.year(column);
        return this;
    }

    @Override
    public QueryService<T> distinct() {
        sql.distinct();
        return this;
    }

    @Override
    public QueryService<T> distinct(Column column) {
        sql.distinct(column);
        return this;
    }

    @Override
    public QueryService<T> count(Column column) {
        sql.count(column);
        return this;
    }

    @Override
    public QueryService<T> count(SelectOp op) {
        sql.count(op);
        return this;
    }

    @Override
    public QueryService<T> max(Column column) {
        sql.max(column);
        return this;
    }

    @Override
    public QueryService<T> min(Column column) {
        sql.min(column);
        return this;
    }

    @Override
    public QueryService<T> avg(Column column) {
        sql.avg(column);
        return this;
    }

    @Override
    public QueryService<T> sum(Column column) {
        sql.sum(column);
        return this;
    }

    @Override
    public Column<T> sumOfColumn(Column column) {
        Column c = Column.column(column.getColumnName());
        sql.sum(column);
        return c;
    }

    @Override
    public QueryService<T> exists(SelectOp op) {
        sql.exists(op);
        return this;
    }

    @Override
    public QueryService<T> between(List<ColumnValue> columnValues) {
        sql.between(columnValues);
        return this;
    }

    @Override
    public QueryService<T> between(SelectOp op) {
        sql.between(op);
        return this;
    }

    @Override
    public QueryService<T> like(String pattern) {
        sql.like(pattern);
        return this;
    }

    @Override
    public QueryService<T> in(SelectOp op) {
        sql.in(op);
        return this;
    }

    @Override
    public QueryService<T> limit(int n) {
        sql.limit(n);
        return this;
    }

    @Override
    public QueryService<T> offset(int n) {
        sql.offset(n);
        return this;
    }

    @Override
    public QueryService<T> as(String alias) {
        sql.as(alias);
        return this;
    }

    @Override
    public QueryService<T> orderBy(Column column) {
        sql.orderBy(column);
        return this;
    }

    @Override
    public QueryService<T> orderBy() {
        sql.orderBy();
        return this;
    }
    
    @Override
    public QueryService<T> asc() {
        sql.asc();
        return this;
    }

    @Override
    public QueryService<T> desc() {
        sql.desc();
        return this;
    }

    @Override
    public QueryService<T> groupBy(List<Column> columns) {
        sql.groupBy(columns);
        return this;
    }

    @Override
    public QueryService<T> groupBy(Column column) {
        sql.groupBy(column);
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
        sql.using(column);
        return this;
    }

    @Override
    public QueryService<T> table(Table table) {
        sql.table(table);
        return this;
    }

    @Override
    public QueryService<T> minus() {
        sql.minus();
        return this;
    }

    @Override
    public QueryService<T> inner() {
        sql.inner();
        return this;
    }

    @Override
    public QueryService<T> join() {
        sql.join();
        return this;
    }
    
    @Override
    public QueryService<T> join(Table table) {
        sql.join(table);
        return this;
    }

    @Override
    public QueryService<T> left() {
        sql.left();
        return this;
    }

    @Override
    public QueryService<T> right() {
        sql.right();
        return this;
    }

    @Override
    public QueryService<T> union() {
        sql.union();
        return this;
    }

    @Override
    public QueryService<T> on() {
        sql.on();
        return this;
    }

    @Override
    public QueryService<T> any() {
        sql.any();
        return this;
    }

    @Override
    public QueryService<T> some() {
        sql.some();
        return this;
    }

    @Override
    public QueryService<T> select(Column column) {
        sql.select(column);
        return this;
    }

    @Override
    public QueryService<T> select(List<Column> columns) {
        sql.select(columns);
        return this;
    }

    @Override
    public QueryService<T> select() {
        sql.select();
        return this;
    }

    @Override
    public QueryService<T> withIndex(Index index) {
        sql.withIndex(index);
        return this;
    }

    @Override
    public SelectOp getSQL() {
        return sql;
    }

    @Override
    public QueryService using(RowMapper<T> rowMapper) {
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

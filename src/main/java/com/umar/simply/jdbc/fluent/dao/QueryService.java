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
    public QueryService<T> ALL() {
        sql.all();
        return this;
    }
    
    @Override
    public QueryService<T> ALL(List<String> aliases) {
        sql.all(aliases);
        return this;
    }

    @Override
    public QueryService<T> FROM(Table table) {
        sql.FROM(table);
        return this;
    }

    @Override
    public QueryService<T> WHERE() {
        sql.WHERE();
        return this;
    }

    @Override
    public QueryService<T> WHERE(Column column) {
        sql.WHERE(column);
        return this;
    }

    @Override
    public QueryService<T> VALUES(List<ColumnValue> columnValues) {
        sql.values(getValuesArray(columnValues));
        return this;
    }

    @Override
    public QueryService<T> COLUMN(List<Column> columns) {
        sql.column(columns);
        return this;
    }

    @Override
    public QueryService<T> COLUMN(Column<T> column) {
        sql.column(column);
        return this;
    }

    @Override
    public QueryService<T> COLUMN_EQ(List<ColumnValue> columnValues) {
        sql.COLUMN_EQ(getValuesArray(columnValues));
        return this;
    }

    @Override
    public QueryService<T> COLUMN_EQ(ColumnValue columnValue) {
        sql.COLUMN_EQ(columnValue);
        return this;
    }

    @Override
    public QueryService<T> NE(Column<T> condition) {
        sql.NE(condition);
        return this;
    }

    @Override
    public QueryService<T> NOT() {
        sql.NOT();
        return this;
    }

    @Override
    public QueryService<T> IS() {
        sql.IS();
        return this;
    }

    @Override
    public QueryService<T> NULL() {
        sql.NULL();
        return this;
    }

    @Override
    public QueryService<T> AND() {
        sql.AND();
        return this;
    }

    @Override
    public QueryService<T> AND(Column<T> column) {
        sql.AND(column);
        return this;
    }

    @Override
    public QueryService<T> OR() {
        sql.OR();
        return this;
    }

    @Override
    public QueryService<T> GT() {
        sql.GT();
        return this;
    }

    @Override
    public QueryService<T> LT() {
        sql.LT();
        return this;
    }

    @Override
    public QueryService<T> GE() {
        sql.GE();
        return this;
    }

    @Override
    public QueryService<T> EQ(Column condition) {
        sql.EQ(condition);
        return this;
    }

    @Override
    public QueryService<T> EQ(String condition) {
        sql.EQ(condition);
        return this;
    }

    @Override
    public QueryService<T> EQ() {
        sql.EQ();
        return this;
    }

    @Override
    public QueryService<T> EQ(ColumnValue value) {
        sql.EQ(value);
        return this;
    }

    @Override
    public QueryService<T> GT(ColumnValue value) {
        sql.GT(value);
        return this;
    }

    @Override
    public QueryService<T> LT(ColumnValue value) {
        sql.LT(value);
        return this;
    }

    @Override
    public QueryService<T> GE(ColumnValue columnValue) {
        sql.GE(columnValue);
        return this;
    }

    @Override
    public QueryService<T> LE(ColumnValue columnValue) {
        sql.LE(columnValue);
        return this;
    }

    @Override
    public QueryService<T> UPPER() {
        sql.UPPER();
        return this;
    }

    @Override
    public QueryService<T> HAVING() {
        sql.HAVING();
        return this;
    }

    @Override
    public QueryService<T> FROM(List<Table> tables) {
        sql.FROM(tables);
        return this;
    }

    @Override
    public QueryService<T> YEAR(Column column) {
        sql.YEAR(column);
        return this;
    }

    @Override
    public QueryService<T> DISTINCT() {
        sql.DISTINCT();
        return this;
    }

    @Override
    public QueryService<T> DISTINCT(Column column) {
        sql.DISTINCT(column);
        return this;
    }

    @Override
    public QueryService<T> COUNT(Column column) {
        sql.COUNT(column);
        return this;
    }

    @Override
    public QueryService<T> COUNT(SelectOp op) {
        sql.COUNT(op);
        return this;
    }

    @Override
    public QueryService<T> MAX(Column column) {
        sql.MAX(column);
        return this;
    }

    @Override
    public QueryService<T> MIN(Column column) {
        sql.MIN(column);
        return this;
    }

    @Override
    public QueryService<T> AVG(Column column) {
        sql.AVG(column);
        return this;
    }

    @Override
    public QueryService<T> SUM(Column column) {
        sql.SUM(column);
        return this;
    }

    @Override
    public QueryService<T> EXISTS(SelectOp op) {
        sql.EXISTS(op);
        return this;
    }

    @Override
    public QueryService<T> BETWEEN(List<ColumnValue> columnValues) {
        sql.BETWEEN(columnValues);
        return this;
    }

    @Override
    public QueryService<T> BETWEEN(SelectOp op) {
        sql.BETWEEN(op);
        return this;
    }

    @Override
    public QueryService<T> LIKE(String pattern) {
        sql.LIKE(pattern);
        return this;
    }

    @Override
    public QueryService<T> IN(List<ColumnValue> columnValues) {
        sql.IN(columnValues);
        return this;
    }
    
    @Override
    public QueryService<T> IN(SelectOp op) {
        sql.IN(op);
        return this;
    }

    @Override
    public QueryService<T> LIMIT(int n) {
        sql.LIMIT(n);
        return this;
    }

    @Override
    public QueryService<T> OFFSET(int n) {
        sql.OFFSET(n);
        return this;
    }

    @Override
    public QueryService<T> AS(String alias) {
        sql.AS(alias);
        return this;
    }

    @Override
    public QueryService<T> ORDER_BY(Column column) {
        sql.ORDERBY(column);
        return this;
    }

    @Override
    public QueryService<T> ORDER_BY() {
        sql.ORDERBY();
        return this;
    }
    
    @Override
    public QueryService<T> ASC() {
        sql.ASC();
        return this;
    }

    @Override
    public QueryService<T> DESC() {
        sql.DESC();
        return this;
    }

    @Override
    public QueryService<T> GROUP_BY(List<Column> columns) {
        sql.GROUPBY(columns);
        return this;
    }

    @Override
    public QueryService<T> GROUP_BY(Column column) {
        sql.GROUPBY(column);
        return this;
    }

    @Override
    public QueryService<T> GROUP_WITH(List<Column> columns) {
        sql.GROUP_WITH(columns);
        return this;
    }

    @Override
    public QueryService<T> GROUP_WITH(Column column) {
        sql.GROUP_WITH(column);
        return this;
    }

    @Override
    public QueryService<T> USING(Column column) {
        sql.USING(column);
        return this;
    }

    @Override
    public QueryService<T> TABLE(Table table) {
        sql.TABLE(table);
        return this;
    }

    @Override
    public QueryService<T> MINUS() {
        sql.MINUS();
        return this;
    }

    @Override
    public QueryService<T> INNER() {
        sql.INNER();
        return this;
    }

    @Override
    public QueryService<T> JOIN() {
        sql.JOIN();
        return this;
    }
    
    @Override
    public QueryService<T> JOIN(Table table) {
        sql.JOIN(table);
        return this;
    }

    @Override
    public QueryService<T> JOIN(SelectOp query) {
        sql.JOIN(query);
        return this;
    }

    @Override
    public QueryService<T> LEFT() {
        sql.LEFT();
        return this;
    }

    @Override
    public QueryService<T> RIGHT() {
        sql.RIGHT();
        return this;
    }

    @Override
    public QueryService<T> UNION() {
        sql.UNION();
        return this;
    }

    @Override
    public QueryService<T> ON() {
        sql.ON();
        return this;
    }
    
    @Override
    public QueryService<T> ON(Column column) {
        sql.ON(column);
        return this;
    }

    @Override
    public QueryService<T> ANY() {
        sql.ANY();
        return this;
    }

    @Override
    public QueryService<T> SOME() {
        sql.SOME();
        return this;
    }

    @Override
    public QueryService<T> SELECT(Column column) {
        sql.SELECT(column);
        return this;
    }

    @Override
    public QueryService<T> SELECT(List<Column> columns) {
        sql.SELECT(columns);
        return this;
    }

    @Override
    public QueryService<T> SELECT() {
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

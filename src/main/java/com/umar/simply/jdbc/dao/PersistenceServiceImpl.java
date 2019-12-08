package com.umar.simply.jdbc.dao;

import com.umar.simply.jdbc.JdbcUtil;
import com.umar.simply.jdbc.dml.operations.AbstractOp;
import com.umar.simply.jdbc.dml.operations.InsertOp;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.dml.operations.UpdateOp;
import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.umar.simply.jdbc.meta.ColumnValue.set;
import com.umar.simply.jdbc.ResultSetMapper;
@Deprecated
public class PersistenceServiceImpl<T> implements PersistenceService<T>{
   
    private final Connection connection;

    public PersistenceServiceImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<T> select(Table table, ResultSetMapper<T> rowMapper, List<ColumnValue> columnValues) {
        final List<T> result = new ArrayList<>();
        SelectOp sql = SelectOp.create();
        sql.select().all().from(table).where().columnEq(getValuesArray(columnValues));
        getMappedResult(rowMapper, result, sql);
        return result;
    }

    @Override
    public Optional<T> save(Table table, ResultSetMapper<T> rowMapper, List<ColumnValue> columnValues) {
        InsertOp sql = InsertOp.create().intoTable(table).columnValues(columnValues);
        int databaseId = getSavedResult(sql);
        Optional<T> optional = findById(table,rowMapper,set(table.getIdColumn(),databaseId));
        return optional;
    }

    @Override
    public T update(Table table, ResultSetMapper<T> rowMapper, List<ColumnValue> columnValuesToSet, List<ColumnValue> clauseValues, int dbSequence) {
        UpdateOp sql = new UpdateOp().table(table).setColumnValues(getValuesArray(columnValuesToSet))
                .where().columnEq(getValuesArray(clauseValues));
        getSavedResult(sql);
        Optional<T> optional = findById(table,rowMapper,set(table.getIdColumn(),dbSequence));
        return optional.get();
    }

    @Override
    public Optional<T> findById(Table table, ResultSetMapper<T> rowMapper, ColumnValue idColumn){
        final List<T> result = new ArrayList<>(1);
        SelectOp sql = SelectOp.create().select().all().from(table).where().columnEq(idColumn);
        getMappedResult(rowMapper, result, sql);
        return result.size() > 0 ? Optional.of(result.get(0)) : Optional.empty();
    }

    @Override
    public Optional<T> find(Table table, ResultSetMapper<T> rowMapper, List<ColumnValue> columnValues) {
        final List<T> result = new ArrayList<>(1);
        SelectOp sql = SelectOp.create().select().all().from(table).where().columnEq(getValuesArray(columnValues));
        getMappedResult(rowMapper, result, sql);
        return result.size() > 0 ? Optional.of(result.get(0)) : Optional.empty();
    }

    @Override
    public List<T> getAll(Table table, ResultSetMapper<T> rowMapper) {
        final List<T> result = new ArrayList<>();
        SelectOp sql = SelectOp.create().select().all().from(table);
        getMappedResult(rowMapper, result, sql);
        return result;
    }

    @Override
    public int count(Table table, ResultSetMapper<T> rowMapper, Column column) {
        final List<T> result = new ArrayList<>(1);
        SelectOp sql = SelectOp.create().select().count(column).from(table);
        getMappedResult(rowMapper, result, sql);
        return result.size()>0 ? result.size() : 0;
    }

    protected Column [] getColumnArray(List<Column> columns) {
        Column [] columnArr = new Column[columns.size()];
        int idx = 0;
        for(Column c:columns){
            columnArr[idx++] = c;
        }
        return columnArr;
    }

    protected ColumnValue [] getValuesArray(List<ColumnValue> values){
        ColumnValue [] vals = new ColumnValue[values.size()];
        int idx = 0;
        for (ColumnValue e:values) {
            vals[idx++] = e;
        }
        return vals;
    }

    protected void getMappedResult(ResultSetMapper<T> rowMapper, List<T> result, AbstractOp sql) {
        try(PreparedStatement ps = prepareAndFill(sql);
            ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                result.add(rowMapper.map(rs));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    protected int getSavedResult(AbstractOp sql) {
        int key = -1;
        try(PreparedStatement ps = prepareAndFill(sql)){
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    key = rs.getInt(1);
                }else{
                    //Its an update..
                }
            }
            return key;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement prepareAndFill(AbstractOp sql) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql.getSQL(), Statement.RETURN_GENERATED_KEYS);
        sql.fill(ps);
        return ps;
    }
}

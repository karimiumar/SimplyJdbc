package com.umar.simply.jdbc.dao;

import com.umar.simply.jdbc.JdbcUtil;
import com.umar.simply.jdbc.RowMapper;
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
@Deprecated
public class PersistenceServiceImpl<T> implements PersistenceService<T>{
    protected JdbcUtil util;
    String driverClass = "org.h2.Driver";
    String url = "jdbc:h2:file:./target/foobar";
    String user = "sa";
    String passwd = "sa";

    public PersistenceServiceImpl(){
        util = JdbcUtil.init(driverClass,url,user,passwd);
    }

    public PersistenceServiceImpl(final String driverClass, final String url, final String user, final String passwd){
        util = JdbcUtil.init(driverClass,url,user,passwd);
    }


    @Override
    public List<T> select(Table table, RowMapper<T> rowMapper, List<ColumnValue> columnValues) {
        final List<T> result = new ArrayList<>();
        SelectOp sql = SelectOp.create();
        sql.select().all().from(table).where().columnValueEq(getValuesArray(columnValues));
        try(Connection connection = util.getConnection()){
            getMappedResult(rowMapper, result, sql, connection);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Optional<T> save(Table table, RowMapper<T> rowMapper, List<ColumnValue> columnValues) {
        InsertOp sql = InsertOp.create().intoTable(table).columnValues(columnValues);
        try(Connection connection = util.getConnection()){
            Long databaseId = getSavedResult(sql, connection);
            Optional<T> optional = findById(table,rowMapper,set(table.getIdColumn(),databaseId));
            return optional;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public T update(Table table, RowMapper<T> rowMapper, List<ColumnValue> columnValuesToSet, List<ColumnValue> clauseValues, Long dbSequence) {
        UpdateOp sql = new UpdateOp().table(table).setColumnValues(getValuesArray(columnValuesToSet))
                .where().columnValueEq(getValuesArray(clauseValues));
        try(Connection connection = util.getConnection()){
            getSavedResult(sql, connection);
            Optional<T> optional = findById(table,rowMapper,set(table.getIdColumn(),dbSequence));
            return optional.get();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> findById(Table table, RowMapper<T> rowMapper, ColumnValue idColumn){
        final List<T> result = new ArrayList<>(1);
        SelectOp sql = SelectOp.create().select().all().from(table).where().columnValueEq(idColumn);
        try(Connection connection = util.getConnection()){
            getMappedResult(rowMapper, result, sql, connection);
            return result.size() > 0 ? Optional.of(result.get(0)) : Optional.empty();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> find(Table table, RowMapper<T> rowMapper, List<ColumnValue> columnValues) {
        final List<T> result = new ArrayList<>(1);
        SelectOp sql = SelectOp.create().select().all().from(table).where().columnValueEq(getValuesArray(columnValues));
        try(Connection connection = util.getConnection()){
            getMappedResult(rowMapper, result, sql, connection);
            return result.size() > 0 ? Optional.of(result.get(0)) : Optional.empty();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> getAll(Table table, RowMapper<T> rowMapper) {
        final List<T> result = new ArrayList<>();
        SelectOp sql = SelectOp.create().select().all().from(table);
        try(Connection connection = util.getConnection()){
            getMappedResult(rowMapper, result, sql, connection);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Integer count(Table table, RowMapper<T> rowMapper, Column column) {
        final List<T> result = new ArrayList<>(1);
        SelectOp sql = SelectOp.create().select().count(column).from(table);
        try(Connection connection = util.getConnection()){
            getMappedResult(rowMapper, result, sql, connection);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
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

    protected void getMappedResult(RowMapper<T> rowMapper, List<T> result, AbstractOp sql, Connection connection) {
        try(PreparedStatement ps = prepareAndFill(sql, connection);
            ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                result.add(rowMapper.map(rs));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    protected Long getSavedResult(AbstractOp sql, Connection conn) {
        Long key = -1L;
        try(PreparedStatement ps = prepareAndFill(sql, conn)){
            System.out.println(sql.getSQL());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    key = (long)rs.getInt(1);
                }else{
                    //Its an update..
                }
            }
            return key;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement prepareAndFill(AbstractOp sql, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql.getSQL(), Statement.RETURN_GENERATED_KEYS);
        sql.fill(ps);
        return ps;
    }
}

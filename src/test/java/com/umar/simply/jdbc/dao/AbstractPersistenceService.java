package com.umar.simply.jdbc.dao;

import com.umar.simply.jdbc.JdbcUtil;
import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.dml.operations.AbstractOp;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractPersistenceService<T> {

    String driverClass = "org.h2.Driver";
    String url = "jdbc:h2:file:./target/ex";
    String user = "sa";
    String passwd = "sa";
    protected final JdbcUtil util = JdbcUtil.init(driverClass,url,user,passwd);

    Optional<T> findById(Table table, RowMapper<T> rowMapper, ColumnValue idColumn){
        final List<T> result = new ArrayList<>(1);
        SelectOp sql = SelectOp.create().select().all().from(table).where().columnValueEq(idColumn);
        try(Connection connection = util.getConnection()){
            getMappedResult(rowMapper, result, sql, connection);
            return result.size() > 0 ? Optional.of(result.get(0)) : Optional.empty();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    ColumnValue[] getValuesArray(List<ColumnValue> values){
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

    Long getSavedResult(AbstractOp sql, Connection conn) {
        long key = -1L;
        try(PreparedStatement ps = prepareAndFill(sql, conn)){
            System.out.println(sql.getSQL());
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

    private PreparedStatement prepareAndFill(AbstractOp sql, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql.getSQL(), Statement.RETURN_GENERATED_KEYS);
        sql.fill(ps);
        return ps;
    }
}

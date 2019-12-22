package com.umar.simply.jdbc.fluent.dao;

import com.umar.simply.jdbc.dml.operations.AbstractOp;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.umar.simply.jdbc.ResultSetMapper;

public abstract class AbstractPersistenceService<T> {
    private final Connection connection;
    
    public AbstractPersistenceService(final Connection connection) {
        this.connection = connection;
    }

    Optional<T> findById(Table table, ResultSetMapper<T> rowMapper, ColumnValue idColumn){
        final List<T> result = new ArrayList<>(1);
        SelectOp sql = SelectOp.create().select().all().from(table).where().columnEq(idColumn);
        getMappedResult(rowMapper, result, sql);
        return result.size() > 0 ? Optional.of(result.get(0)) : Optional.empty();
    }

    ColumnValue[] getValuesArray(List<ColumnValue> values){
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

    int getSavedResult(AbstractOp sql) {
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

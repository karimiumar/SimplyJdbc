package com.umar.simply.jdbc.dml.operations;

import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a database's <b>UPDATE</b> operation
 * @author Mohammad Umar Ali Karimi (karimiumar@gmail.com)
 */
public class UpdateOp extends AbstractOp<UpdateOp> {

    private final StringBuilder sb = new StringBuilder();
    private final List<ColumnValue<?>> values = new LinkedList<>();

    public UpdateOp() {
        op().append("UPDATE ");
    }

    /**
     * The SQL SET clause used in conjunction with UPDATE operation
     *
     * @param columnValues The {@link ColumnValue} objects
     * @return Returns {@link UpdateOp}
     */
    public UpdateOp SET(ColumnValue<?> ... columnValues){
        int len = columnValues.length;
        int cnt = 1;
        op().append(" SET ");
        for(ColumnValue<?> e: columnValues) {
            values.add(e);
            op().append(e.getColumnName());
            op().append(" =?");
            if(cnt++ < len){
                op().append(",");
            }
        }
        return this;
    }

    public String getSQL() {
        final String sql = op().toString().trim();
        op().setLength(0);
        return sql;
    }

    @Override
    public StringBuilder op() {
        return sb;
    }

    @Override
    public List<ColumnValue<?>> getValues() {
        return values;
    }

}

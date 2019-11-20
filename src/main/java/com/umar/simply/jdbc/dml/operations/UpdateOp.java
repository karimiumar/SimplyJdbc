package com.umar.simply.jdbc.dml.operations;

import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a database's <b>UPDATE</b> operation
 * @author umar
 */
public class UpdateOp extends AbstractOp<UpdateOp> {

    private final StringBuilder sb = new StringBuilder();
    private final List<ColumnValue> values = new LinkedList<>();

    public UpdateOp() {
        op().append("UPDATE ");
    }

    public UpdateOp setColumnValues(ColumnValue ... columnValues){
        int len = columnValues.length;
        int cnt = 1;
        op().append(" SET ");
        for(ColumnValue e: columnValues) {
            values.add(e);
            op().append(e.getColumnName());
            op().append("=?");
            if(cnt++ < len){
                op().append(",");
            }
        }
        return this;
    }

    public String getSQL() {
        return op().toString().trim();
    }

    @Override
    public StringBuilder op() {
        return sb;
    }

    @Override
    public List<ColumnValue> getValues() {
        return values;
    }

}

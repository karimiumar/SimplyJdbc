package com.umar.simply.jdbc.dml.operations;

import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a database's <b>DELETE</b> operation
 */
public class DeleteOp extends AbstractOp<DeleteOp> {

    private final StringBuilder sb = new StringBuilder();
    private final List<ColumnValue> values = new LinkedList<>();

    private DeleteOp(){}

    public static DeleteOp create() {
        return new DeleteOp();
    }

    public DeleteOp deleteFrom(String table) {
        op().append("DELETE FROM ");
        op().append(table);
        op().append(" ");
        return this;
    }

    public DeleteOp deleteFrom(Table table) {
        op().append("DELETE FROM ");
        op().append(table);
        op().append(" ");
        return this;
    }

    public DeleteOp column(String column) {
        op().append(column);
        return this;
    }

    public DeleteOp anyColumnValues(ColumnValue ... columnValues) {
        int len = columnValues.length;
        int cnt = 1;
        for(ColumnValue e: columnValues) {
            values.add(e);
            op().append(e.getColumnName());
            op().append("=?");
            if(cnt++ < len){
                op().append(" OR ");
            }
        }
        return this;
    }

    @Override
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

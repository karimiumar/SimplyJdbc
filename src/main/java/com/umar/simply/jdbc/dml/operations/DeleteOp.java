package com.umar.simply.jdbc.dml.operations;

import com.umar.simply.jdbc.dml.operations.api.DeleteFunction;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a database's <b>DELETE</b> operation
 * @author Mohammad Umar Ali Karimi (karimiumar@gmail.com)
 */
public class DeleteOp extends AbstractOp<DeleteOp> implements DeleteFunction {

    private final StringBuilder sb = new StringBuilder();
    private final List<ColumnValue<?>> values = new LinkedList<>();

    private DeleteOp(){}

    public static DeleteOp create() {
        return new DeleteOp();
    }

    /**
     * The DELETE FROM Table SQL Clause
     *
     * @param table The table name
     * @return Returns {@link DeleteOp}
     */
    public DeleteOp DELETE_FROM(String table) {
        op().append("DELETE FROM ");
        op().append(table);
        op().append(" ");
        return this;
    }

    /**
     * The DELETE FROM Table SQL clause
     *
     * @param table The {@link Table}
     * @return Returns {@link DeleteOp}
     */
    public DeleteOp DELETE_FROM(Table table) {
        op().append("DELETE FROM ");
        op().append(table);
        op().append(" ");
        return this;
    }

    public DeleteOp anyColumnValues(ColumnValue<?> ... columnValues) {
        int len = columnValues.length;
        int cnt = 1;
        for(ColumnValue<?> e: columnValues) {
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

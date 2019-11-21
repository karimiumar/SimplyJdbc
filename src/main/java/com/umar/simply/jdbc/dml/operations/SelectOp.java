package com.umar.simply.jdbc.dml.operations;

import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.LinkedList;
import java.util.List;

public class SelectOp extends AbstractOp<SelectOp> {

    private final StringBuilder sb = new StringBuilder();
    private final List<ColumnValue> values = new LinkedList<>();

    private SelectOp() {

    }

    public static SelectOp create() {
        return new SelectOp();
    }

    /**
     * SQL all. Its used in conjunction with SELECT operation to return all the columns
     *
     * @return Returns this object
     */
    public SelectOp all() {
        op().append("*");
        return this;
    }

    /**
     * The values to work with as actual parameters
     *
     * @param values The column values to fill
     * @return Returns this object
     */
    public SelectOp values(ColumnValue... values) {
        int len = values.length;
        int cnt = 1;
        for (ColumnValue value : values) {
            this.values.add(value);
            op().append("?");
            if (cnt++ < len) {
                op().append(",");
            }
        }
        return this;
    }


    /**
     * SQL Columns to be selected or worked with
     *
     * @param columns The columns to fetch
     * @return Returns this object
     */
    public SelectOp column(List<Column> columns) {
        int len = columns.size();
        int cnt = 1;
        for (Column column : columns) {
            op().append(column);
            if (cnt++ < len) {
                op().append(",");
            }
        }
        return this;
    }

    /**
     * SQL Columns to be selected or worked with
     *
     * @param column The columns to fetch
     * @return Returns this object
     */
    public SelectOp column(Column column) {
        op().append(column);
        return this;
    }

    /**
     * SQL Columns to be selected or worked with
     *
     * @param column The columns to fetch
     * @return Returns this object
     */
    public SelectOp column(String column) {
        op().append(column);
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
    public String toString() {
        return getSQL();
    }

    @Override
    public List<ColumnValue> getValues() {
        return values;
    }

}
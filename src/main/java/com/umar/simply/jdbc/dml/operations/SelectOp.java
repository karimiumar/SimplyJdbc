package com.umar.simply.jdbc.dml.operations;

import com.umar.simply.jdbc.dml.operations.api.SelectFunction;
import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a database's <b>SELECT</b> operation
 * @author Mohammad Umar Ali Karimi (karimiumar@gmail.com)
 */
public class SelectOp extends AbstractOp<SelectOp> implements SelectFunction {

    private final StringBuilder sb = new StringBuilder();
    private final List<ColumnValue<?>> values = new LinkedList<>();

    private SelectOp() {

    }

    public static SelectOp create() {
        return new SelectOp();
    }
    
    public SelectOp ALL(List<String> aliases) {
        int len = aliases.size();
        int cnt = 1;
        for(String alias:aliases) {
            op().append(alias);
            if(cnt++ < len) {
                op().append(",");
            }
        }
        return this;
    }

    /**
     * The values to work GROUP_WITH AS actual parameters
     *
     * @param values The COLUMN values to fill
     * @return Returns {@link SelectOp}
     */
    public SelectOp VALUES(ColumnValue<?>... values) {
        int len = values.length;
        int cnt = 1;
        for (ColumnValue<?> value : values) {
            this.values.add(value);
            op().append("?");
            if (cnt++ < len) {
                op().append(",");
            }
        }
        return this;
    }


    /**
     * SQL Columns to be selected OR worked GROUP_WITH
     *
     * @param columns The columns to fetch
     * @return Returns {@link SelectOp}
     */
    public SelectOp COLUMN(List<Column<?>> columns) {
        int len = columns.size();
        int cnt = 1;
        for (Column<?> column : columns) {
            op().append(column);
            if (cnt++ < len) {
                op().append(",");
            }
        }
        return this;
    }

    /**
     * SQL Columns to be selected OR worked GROUP_WITH
     *
     * @param column The columns to fetch
     * @return Returns {@link SelectOp}
     */
    public SelectOp COLUMN(Column<?> column) {
        op().append(column);
        return this;
    }

    /**
     * SQL Columns to be selected OR worked GROUP_WITH
     *
     * @param column The columns to fetch
     * @return Returns {@link SelectOp}
     */
    public SelectOp COLUMN(String column) {
        op().append(column);
        return this;
    }

    @Override
    public SelectOp CONDITION(String condition) {
        op().append(condition);
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
    public String toString() {
        return getSQL();
    }

    @Override
    public List<ColumnValue<?>> getValues() {
        return values;
    }

}

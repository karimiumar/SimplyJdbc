package com.umar.simply.jdbc.dml.operations;

import com.umar.simply.jdbc.dml.operations.api.InsertFunction;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a database's <b>INSERT</b> operation
 * @author Mohammad Umar Ali Karimi (karimiumar@gmail.com)
 */
public class InsertOp extends AbstractOp<InsertOp> implements InsertFunction {
    private final StringBuilder sb = new StringBuilder();
    private final List<ColumnValue<?>> values = new LinkedList<>();


    private InsertOp(){}

    public static InsertOp create(){
        return new InsertOp();
    }

    /**
     * The INSERT INTO operation.
     * @param table The TABLE name to use for INSERT operation
     *
     * @return Returns {@link InsertOp}
     */
    public InsertOp INTO_TABLE(String table) {
        op().append("INSERT INTO ");
        op().append(table);
        return this;
    }

    /**
     * The INSERT INTO operation.
     * @param table The {@link Table} to use for INSERT operation
     *
     * @return Returns {@link InsertOp}
     */
    public InsertOp INTO_TABLE(Table table) {
        op().append("INSERT INTO ");
        op().append(table);
        return this;
    }

    /**
     * The SQL VALUES Clause used in conjunction with INSERT operation.
     *
     * @param columnValues A list of {@link ColumnValue}
     * @return Returns {@link InsertOp}
     */
    public InsertOp VALUES(List<ColumnValue<?>> columnValues) {
        int len = columnValues.size();
        int cnt = 1;
        op().append("(");
        for (ColumnValue<?> e: columnValues){
            op().append(e.getColumnName());
            if(cnt++ < len){
                op().append(",");
            }
        }
        cnt = 1; //reinitialize cnt to 1
        op().append(")");
        op().append("VALUES");
        op().append("(");
        for(ColumnValue<?> e: columnValues) {
            op().append("?");
            if(cnt++ < len){
                op().append(",");
            }
            values.add(e);
        }
        op().append(")");
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

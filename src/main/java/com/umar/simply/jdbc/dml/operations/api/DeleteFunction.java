package com.umar.simply.jdbc.dml.operations.api;

import com.umar.simply.jdbc.dml.operations.DeleteOp;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

public interface DeleteFunction extends SqlFunctions<DeleteOp> {
    /**
     * The DELETE FROM Table SQL Clause
     *
     * @param table The table name
     * @return Returns {@link DeleteOp}
     */
    DeleteOp DELETE_FROM(String table);

    /**
     * The DELETE FROM Table SQL clause
     *
     * @param table The {@link Table}
     * @return Returns {@link DeleteOp}
     */
    DeleteOp DELETE_FROM(Table table);

    /**
     * This is used in conjunction with {@link DeleteOp#DELETE_FROM(Table)}
     * or {@link DeleteOp#DELETE_FROM(String)} along with  {@link SqlFunctions#WHERE()}
     * clause.
     *
     * @param columnValues The {@link ColumnValue} columns to delete
     *
     * @return Returns {@link DeleteOp}
     */
    DeleteOp anyColumnValues(ColumnValue<?>... columnValues);
}

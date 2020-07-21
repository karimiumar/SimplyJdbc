package com.umar.simply.jdbc.dml.operations.api;

import com.umar.simply.jdbc.dml.operations.InsertOp;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import java.util.List;

public interface InsertFunction extends SqlFunctions<InsertOp> {
    /**
     * The INSERT INTO operation.
     * @param table The TABLE name to use for INSERT operation
     *
     * @return Returns {@link InsertOp}
     */
    InsertOp INTO_TABLE(String table);

    /**
     * The INSERT INTO operation.
     * @param table The {@link Table} to use for INSERT operation
     *
     * @return Returns {@link InsertOp}
     */
    InsertOp INTO_TABLE(Table table);

    /**
     * The SQL VALUES Clause used in conjunction with INSERT operation.
     *
     * @param columnValues A list of {@link ColumnValue}
     * @return Returns {@link InsertOp}
     */
    InsertOp VALUES(List<ColumnValue<?>> columnValues);
}

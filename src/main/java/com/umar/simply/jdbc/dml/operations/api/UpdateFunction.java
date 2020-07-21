package com.umar.simply.jdbc.dml.operations.api;

import com.umar.simply.jdbc.dml.operations.UpdateOp;
import com.umar.simply.jdbc.meta.ColumnValue;

public interface UpdateFunction extends SqlFunctions<UpdateOp> {
    /**
     * The SQL SET clause used in conjunction with UPDATE operation
     *
     * @param columnValues The {@link ColumnValue} objects
     * @return Returns {@link UpdateOp}
     */
    UpdateOp SET(ColumnValue<?> ... columnValues);
}

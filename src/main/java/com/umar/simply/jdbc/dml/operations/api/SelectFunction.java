package com.umar.simply.jdbc.dml.operations.api;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.List;

public interface SelectFunction extends SqlFunctions<SelectOp> {

    /**
     * SQL all. Its used in conjunction with SELECT operation to return all the columns
     *
     * @param aliases The columns aliases to use
     *
     * @return Returns {@link SelectOp}
     */
    SelectOp ALL(List<String> aliases);

    /**
     * The values to work GROUP_WITH AS actual parameters
     *
     * @param values The COLUMN values to fill
     * @return Returns {@link SelectOp}
     */
    SelectOp VALUES(ColumnValue<?>... values);

    /**
     * SQL Columns to be selected OR worked GROUP_WITH
     *
     * @param columns The columns to fetch
     * @return Returns {@link SelectOp}
     */
    SelectOp COLUMN(List<Column<?>> columns);

    /**
     * SQL Columns to be selected OR worked GROUP_WITH
     *
     * @param column The columns to fetch
     * @return Returns {@link SelectOp}
     */
    SelectOp COLUMN(Column<?> column);

    /**
     * SQL Columns to be selected OR worked GROUP_WITH
     *
     * @param column The columns to fetch
     * @return Returns {@link SelectOp}
     */
    SelectOp COLUMN(String column);

    /**
     * This returns the resulting SQL
     * @return Returns the resulting SQL
     */
    String getSQL();
}

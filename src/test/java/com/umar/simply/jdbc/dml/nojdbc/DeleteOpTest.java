package com.umar.simply.jdbc.dml.nojdbc;

import com.umar.simply.jdbc.dml.operations.DeleteOp;
import static com.umar.simply.jdbc.meta.Column.column;

import com.umar.simply.jdbc.dml.operations.api.DeleteFunction;
import com.umar.simply.jdbc.meta.ColumnValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.umar.simply.jdbc.meta.ColumnValue.*;
import java.util.List;

public class DeleteOpTest {

    @Test
    public void whereEq(){
        DeleteFunction operation = DeleteOp.create();
        operation.DELETE_FROM("PERSON").WHERE().EQ(eq(column("firstname"), "abc"));
        String result = operation.getSQL();
        String expected = "DELETE FROM PERSON  WHERE firstname =?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue<?>> params = operation.getValues();
        Assertions.assertEquals(1, params.size());
    }

    @Test
    public void deleteDuplicates(){
        DeleteFunction sql = DeleteOp.create().DELETE_FROM("contacts t1")
                .INNER().JOIN().TABLE("contacts t2")
                .WHERE().COLUMN("t1.id").LT().COLUMN("t2.id").AND().COLUMN("t1.email").EQ("t2.email");
        String result = sql.getSQL();
        String expected = "DELETE FROM contacts t1  INNER JOIN contacts t2 WHERE t1.id < t2.id AND t1.email = t2.email";
        Assertions.assertEquals(result,expected);
    }
}

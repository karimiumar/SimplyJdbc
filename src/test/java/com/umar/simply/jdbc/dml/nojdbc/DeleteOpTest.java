package com.umar.simply.jdbc.dml.nojdbc;

import com.umar.simply.jdbc.dml.operations.DeleteOp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeleteOpTest {

    @Test
    public void whereEq(){
        DeleteOp operation = DeleteOp.create();
        operation.deleteFrom("PERSON").WHERE().columnEq("firstname");
        String result = operation.getSQL();
        String expected = "DELETE FROM PERSON  WHERE firstname=?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void deleteDuplicates(){
        DeleteOp sql = DeleteOp.create().deleteFrom("contacts t1")
                .INNER().JOIN().TABLE("contacts t2")
                .WHERE().column("t1.id").LT().column("t2.id").AND().column("t1.email").EQ("t2.email");
        String result = sql.getSQL();
        String expected = "DELETE FROM contacts t1  INNER JOIN contacts t2 WHERE t1.id<t2.id AND t1.email=t2.email";
        Assertions.assertEquals(result,expected);
    }
}

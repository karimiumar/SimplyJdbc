package com.umar.simply.jdbc.dml.nojdbc;

import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.dml.operations.UpdateOp;

import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.OrderTable.*;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UpdateTest {

    @Test
    public void combineAndConditionTest(){
        UpdateOp update = new UpdateOp();
        update.table("PERSON").setColumnValues(set(PERSON_FIRST_NAME,"Eva"))
                .where().not().columnEq(set(PERSON_ID,1))
                .and(SelectOp.create().columnEq(set(PERSON_IS_ADULT,true))
                        .or().columnEq(set(PERSON_EMAIL,"abc@abc.com")));
        String result = update.getSQL();
        String expected = "UPDATE PERSON SET firstname=? WHERE  NOT id=? AND (adult=? OR email=? )";
        System.out.println(update.getSQL());
        List<ColumnValue> params = update.getValues();
        Assertions.assertTrue(params.size() == 4);
        System.out.println(update.getSQL());
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void combineAndConditionWithValuesTest(){
        UpdateOp update = new UpdateOp();
        update.table(TBL_PERSON).setColumnValues(set(PERSON_FIRST_NAME,"Eva")).where().not().columnEq(set(PERSON_ID,123))
                .and(SelectOp.create().columnEq(set(PERSON_IS_ADULT,false)).or().columnEq(set(PERSON_EMAIL,"tina@123.com")));
        String result = update.getSQL();
        String expected = "UPDATE person SET firstname=? WHERE  NOT id=? AND (adult=? OR email=? )";
        List<ColumnValue> params = update.getValues();
        Assertions.assertTrue(params.size() == 4);
        System.out.println(update.getSQL());
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void complexOrConditionTest() {
        UpdateOp update = new UpdateOp();
        update.table(TBL_ORDERS).setColumnValues(set(ORDER_CUSTOMERID,44))
                .where()
                .not(SelectOp.create()
                    .ge(set(ORDER_TOTAL_AMT, 5000.00))
                    .and()
                    .le(set(ORDER_TOTAL_AMT, 12000.00))
                );
        String result = update.getSQL();
        String expected = "UPDATE orders SET customer_id=? WHERE  NOT( total_amount>=? AND total_amount<=? )";
        System.out.println(result);
        List<ColumnValue> params = update.getValues();
        Assertions.assertTrue(params.size() == 3);
        System.out.println(update.getSQL());
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void complexConditionTest() {
        UpdateOp update = new UpdateOp();
        update.table(TBL_PERSON).setColumnValues(set(PERSON_CITY, "Dusseldorf"))
                .where()
                .not(SelectOp.create()
                .columnEq(set(PERSON_COUNTRY, "Germany"))
                );
        String result = update.getSQL();
        String expected = "UPDATE person SET city=? WHERE  NOT( country=? )";
        System.out.println(result);
        Assertions.assertEquals(result, expected);
        List<ColumnValue> params = update.getValues();
        Assertions.assertTrue(params.size() == 2);
        System.out.println(update.getSQL());
    }

}

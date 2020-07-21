package com.umar.simply.jdbc.dml.nojdbc;

import com.umar.simply.jdbc.dml.operations.InsertOp;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.ProductTable.*;
import static com.umar.simply.jdbc.meta.ColumnValue.*;

import static java.util.Arrays.asList;

import com.umar.simply.jdbc.dml.operations.api.InsertFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InsertOpTest {

    public void insertIntoWithSelect() {
        String expected = "INSERT INTO Customer (FirstName, LastName, City, Country, Phone)\n" +
                "    SELECT LEFT(ContactName, CHARINDEX(' ',ContactName) - 1), \n" +
                "           SUBSTRING(ContactName, CHARINDEX(' ',ContactName) + 1, 100), \n" +
                "           City, Country, Phone\n" +
                "      FROM Supplier\n" +
                "     WHERE CompanyName = 'Bigfoot Breweries'";
    }

    @Test
    public void insertIntoProduct() {
        InsertFunction sql = InsertOp.create();
        sql.INTO_TABLE(TBL_PRODUCT).VALUES(asList(
                set(PRODUCT_NAME_COL,"Nike Shoes"),
                set(PRODUCT_SUPPLIERID_COL, 2),
                set(PRODUCT_UNIT_PRICE_COL, 2345.00),
                set(PRODUCT_DISCONTINUED_COL,false),
                set(PRODUCT_CAT_ID_COL,1)
        ));
        String expected = "INSERT INTO product(product_name,supplier_id,unit_price,is_discontinued,category_id)VALUES(?,?,?,?,?)";
        String result = sql.getSQL();
        Assertions.assertEquals(result,expected);
        int sz = sql.getValues().size();
        Assertions.assertEquals(5,sz);
    }

    @Test
    public void insertIntoPerson(){
        InsertFunction sql = InsertOp.create();
        sql.INTO_TABLE(TBL_PERSON).VALUES(asList(
                set(PERSON_FIRST_NAME,"Jasmine"),
                set(PERSON_LAST_NAME, "Black"), 
                set(PERSON_EMAIL,"jb@gmail.com"),
                set(PERSON_COUNTRY,"India"),
                set(PERSON_CITY,"New Delhi"),
                set(PERSON_IS_ADULT, true)
        ));
        String expected = "INSERT INTO person(firstname,lastname,email,country,city,adult)VALUES(?,?,?,?,?,?)";
        String result = sql.getSQL();
        Assertions.assertEquals(result,expected);
        int sz = sql.getValues().size();
        Assertions.assertEquals(6,sz);
    }
}

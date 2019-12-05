package com.umar.simply.jdbc.dml.nojdbc;

import com.umar.simply.jdbc.dml.operations.InsertOp;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.ProductTable.*;


import static java.util.Arrays.asList;
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
        InsertOp sql = InsertOp.create();
        sql.intoTable(TBL_PRODUCT).columns(asList(PRODUCT_NAME_COL,PRODUCT_SUPPLIERID_COL,PRODUCT_UNIT_PRICE_COL,PRODUCT_DISCONTINUED_COL,PRODUCT_CAT_ID_COL));
        String expected = "INSERT INTO product(product_name,supplier_id,unit_price,is_discontinued,category_id)VALUES(?,?,?,?,?)";
        String result = sql.getSQL();
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void insertIntoPerson(){
        InsertOp sql = InsertOp.create();
        sql.intoTable(TBL_PERSON).columns(asList(PERSON_FIRST_NAME,PERSON_LAST_NAME,PERSON_EMAIL,PERSON_COUNTRY,PERSON_CITY,PERSON_IS_ADULT));
        String expected = "INSERT INTO person(firstname,lastname,email,country,city,adult)VALUES(?,?,?,?,?,?)";
        String result = sql.getSQL();
        Assertions.assertEquals(result,expected);
    }
}

package com.umar.simply.jdbc.dml.nojdbc;

import com.umar.simply.jdbc.fluent.dao.person.Person;
import com.umar.simply.jdbc.dml.operations.SelectOp;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.CustomerTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.ProductTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.OrderTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.SupplierTable.*;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;
import static com.umar.simply.jdbc.meta.Column.column;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static com.umar.simply.jdbc.dml.operations.SelectOp.create;
import com.umar.simply.jdbc.meta.ColumnValue;
import com.umar.simply.jdbc.meta.Table;

import static java.util.Arrays.asList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SqlQueryTest {
    @Test
    public void selectDistinctColumn(){
        SelectOp select = create();
        select.select()
                .distinct(CUSTOMER_COUNTRY)
                .from(TBL_CUSTOMERS)
                .orderBy(CUSTOMER_COUNTRY)
                .asc();
        String result = select.getSQL();
        String expected = "SELECT  DISTINCT country FROM customer ORDER BY country ASC";
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void selectCountDistinctColumn(){
        SelectOp sql = create();
        sql.select().count(create().distinct("country")).from("customer");
        String result = sql.getSQL();
        String expected = "SELECT  COUNT(DISTINCT country) FROM customer";
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void selectMySQLTop10() {
        SelectOp sql = create();
        sql.select().column(asList(CUSTOMER_COUNTRY, CUSTOMER_CITY)).from(TBL_CUSTOMERS).as("C1").limit(10);
        String result = sql.getSQL();
        String expected = "SELECT country,city FROM customer AS C1  LIMIT ?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue>params = sql.getValues();
        Assertions.assertTrue(params.size() == 1);
    }

    @Test
    public void selectMySQLOffset6To10() {
        SelectOp sql = create();
        sql.select().column(asList(CUSTOMER_COUNTRY, CUSTOMER_CITY)).from(TBL_CUSTOMERS).limit(5).offset(5);
        String result = sql.getSQL();
        String expected = "SELECT country,city FROM customer LIMIT ? OFFSET ?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue>params = sql.getValues();
        Assertions.assertTrue(params.size() == 2);
    }

    @Test
    public void selectAll(){
        SelectOp sql = create();
        sql.select().all().from("customer c");
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer c";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void in() {
        SelectOp sql = create();
        sql.select().all().from(TBL_CUSTOMERS)
                .where().column(asList(CUSTOMER_COUNTRY)).in(asList(set("Germany"), set("US")));
        String result = sql.getSQL();
        //"SELECT * FROM customer c1 WHERE country IN ('Germany','US')";
        String expected = "SELECT * FROM customer WHERE country IN (?,?)";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void inUsingSchemaVars() {
        SelectOp sql = create();
        sql.select().all().from(TBL_CUSTOMERS)
                .where().column(asList(CUSTOMER_COUNTRY))
                .in(asList(set("Germany"), set("US")));
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer WHERE country IN (?,?)";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void whereInUsingExSchemaVars() {
        SelectOp sql = create();
        sql.select().all().from(TBL_CUSTOMERS).whereIn(CUSTOMER_COUNTRY, asList(set("Germany"), set("US")));
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer WHERE country IN (?,?)";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
        Assertions.assertEquals(result,expected);
    }

   @Test
    public void notIn() {
        SelectOp sql = create();
        sql.select().all().from(TBL_CUSTOMERS)
                .where().column(CUSTOMER_COUNTRY).not().in(
                asList(set("Germany"), set("US")));
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer WHERE country NOT  IN (?,?)";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void notInSubQuery() {
        SelectOp sql = create();
        sql.select().all().from(TBL_CUSTOMERS)
                .where().column(CUSTOMER_FIRST_NAME).not().in(
                create().select().column(PERSON_FIRST_NAME).as("p1.firstname").from(TBL_PERSON).as("p1"));
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer WHERE first_name NOT  IN (SELECT firstname AS p1.firstname  FROM person AS p1)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void avg() {
        SelectOp sql = create();
        sql.select().avg(PRODUCT_UNIT_PRICE_COL).from(TBL_PRODUCT);
        String result = sql.getSQL();
        String expected = "SELECT  AVG(unit_price) FROM product";
        Assertions.assertEquals(result,expected);
    }
    
    @Test
    public void max() {
        SelectOp sql = create();
        sql.select().max(PRODUCT_UNIT_PRICE_COL).from(TBL_PRODUCT);
        String result = sql.getSQL();
        String expected = "SELECT  MAX(unit_price) FROM product";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void min() {
        SelectOp sql = create();
        sql.select().min(column("price")).from("product");
        String result = sql.getSQL();
        String expected = "SELECT  MIN(price) FROM product";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void sum(){
        SelectOp sql = create();
        sql.select().sum(PRODUCT_UNIT_PRICE_COL).from(TBL_PRODUCT);
        String result = sql.getSQL();
        String expected = "SELECT  SUM(unit_price) FROM product";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void asTest() {
        SelectOp sql = create();
        sql.select().column(PERSON_FIRST_NAME).as("NAME").from(TBL_PERSON);
        String result = sql.getSQL();
        String expected = "SELECT firstname AS NAME  FROM person";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void likeStartingWith_a() {
        SelectOp sql = create();
        sql.select().all().from(TBL_PERSON).where().column(PERSON_FIRST_NAME).like("a%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void likeEndingWith_a() {
        SelectOp sql = create();
        sql.select().all().from(TBL_PERSON).where().column(PERSON_FIRST_NAME).like("%a");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void nameHaving_or() {
        SelectOp sql = create();
        sql.select().all().from(TBL_PERSON).where().column(PERSON_FIRST_NAME).like("%or%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void nameHaving_r_InSecondPos() {
        SelectOp sql = create();
        sql.select().all().from(TBL_PERSON).where().column(PERSON_FIRST_NAME).like("_r%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void nameStartingWith_a_AndAtleast3Chars() {
        SelectOp sql = create();
        sql.select().all().from(TBL_PERSON).where().column(PERSON_FIRST_NAME).like("a_%_%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void nameStartsWith_a_AndEndsWith_o() {
        SelectOp sql = create();
        sql.select().all().from(TBL_PERSON).where().column(PERSON_FIRST_NAME).like("a%o");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void notStartWith_a() {
        SelectOp sql = create();
        sql.select().all().from(TBL_PERSON).where().column(PERSON_FIRST_NAME).not().like("a%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person WHERE firstname NOT  LIKE ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void betweenTextValues(){
        SelectOp sql = create().select().all().from(TBL_PRODUCT).where().column(PRODUCT_NAME_COL)
                .between(asList(set("Carnarvon Tigers"),set("Mozzarella di Giovanni")));
        String result = sql.getSQL();
        //SELECT * FROM product WHERE product_name BETWEEN 'Carnarvon Tigers' AND 'Mozzarella di Giovanni'
        String expected = "SELECT * FROM product WHERE product_name BETWEEN ? AND ?";
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
        Assertions.assertEquals(result,expected);
    }

   @Test
    public void betweenWithIn(){
        SelectOp sql = create().select().all().from(TBL_PRODUCT).where(create().column(PRODUCT_UNIT_PRICE_COL)
                .between(asList(set(10), set(20))))
                .and().not().column(PRODUCT_CAT_ID_COL).in(asList(set(1), set(2), set(3)));
        String result = sql.getSQL();
        //"SELECT * FROM product WHERE (unit_price BETWEEN '10' AND '20') AND  NOT category_id IN ('1','2','3')"
        String expected = "SELECT * FROM product WHERE (unit_price BETWEEN ? AND ?) AND  NOT category_id IN (?,?,?)";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(5, params.size());
    }

    @Test
    public void notBetween(){
        SelectOp sql = create().select().all().from(TBL_PRODUCT)
                .where().column(PRODUCT_UNIT_PRICE_COL).not().between(asList(set(10),set(20)));
        String result = sql.getSQL();
        String expected = "SELECT * FROM product WHERE unit_price NOT  BETWEEN ? AND ?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
    }

    @Test
    public void isNull(){
        SelectOp sql = create().select().all().from(TBL_PRODUCT)
                .where().column(PRODUCT_NAME_COL).is().values(set(null));
        String result = sql.getSQL();
        String expected = "SELECT * FROM product WHERE product_name IS ?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
    }

    @Test
    public void orderBy(){
        SelectOp sql = create().select(asList(PRODUCT_ID_COL, PRODUCT_NAME_COL, PRODUCT_UNIT_PRICE_COL))
                .from(TBL_PRODUCT).where().column(PRODUCT_UNIT_PRICE_COL).eq().values(set(20))
                .orderBy().column(PRODUCT_NAME_COL);
        String result = sql.getSQL();
        String expected = "SELECT id,product_name,unit_price FROM product WHERE unit_price=? ORDER BY product_name";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
    }

    @Test
    public void groupBy(){
        SelectOp sql = create().select().count(CUSTOMER_ID)
                .with(CUSTOMER_COUNTRY)
                .from(TBL_CUSTOMERS).as("c1").groupBy(CUSTOMER_COUNTRY);
        String result = sql.getSQL();
        String expected = "SELECT  COUNT(id), country FROM customer AS c1  GROUP BY country";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(0, params.size());
    }

    @Test
    public void groupByOrderByDesc(){
        SelectOp sql = create()
                .select().count(column("id"))
                .with(column("country"))
                .from("customer")
                .groupBy(column("country"))
                .orderBy().count(column("id")).desc();
        String result = sql.getSQL();
        String expected = "SELECT  COUNT(id), country FROM customer GROUP BY country ORDER BY  COUNT(id) DESC";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(0, params.size());
    }
    
    @Test
    public void subQuerySelectOrderByDesc(){
        SelectOp sql = create().select().all().from(TBL_CUSTOMERS)
                .left().join(create()
                .select().sum(ORDER_TOTAL_AMT).as("TOTAL_AMOUNT").with(asList(ORDER_CUSTOMERID))
                .from(TBL_ORDERS).groupBy(ORDER_CUSTOMERID))
                .as("CUSTOMER_TOTALS")
                .on().column(CUSTOMER_ID).eq("CUSTOMER_TOTALS.CUSTOMER_ID")
                .orderBy().column("CUSTOMER_TOTALS.TOTAL_AMOUNT").desc();
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer LEFT JOIN (SELECT  SUM(total_amount) AS TOTAL_AMOUNT ,customer_id FROM orders GROUP BY customer_id ) AS CUSTOMER_TOTALS  ON id=CUSTOMER_TOTALS.CUSTOMER_ID ORDER BY CUSTOMER_TOTALS.TOTAL_AMOUNT DESC";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void totalAmtOrderedForEachCustomer(){
        SelectOp sql = create().select().sum(ORDER_TOTAL_AMT).with(asList(CUSTOMER_FIRST_NAME,CUSTOMER_LAST_NAME))
                .from(TBL_ORDERS)
                .join().table(TBL_CUSTOMERS).as("C")
                .on().column(ORDER_CUSTOMERID).eq().column(column("C.id"))
                .groupBy(asList(CUSTOMER_FIRST_NAME,CUSTOMER_LAST_NAME))
                .orderBy().sum(ORDER_TOTAL_AMT)
                .desc();
        String result = sql.getSQL();
        String expected = "SELECT  SUM(total_amount),first_name,last_name FROM orders JOIN customer AS C  ON customer_id=C.id GROUP BY first_name,last_name ORDER BY  SUM(total_amount) DESC";
        Assertions.assertEquals(result,expected);
    }

    /*
       Tests for HAVING CLAUSE
     */
    @Test
    public void havingCount(){
        SelectOp sql = create()
                .select().count(column("C.id")).with(column("C.country"))
                .from(TBL_CUSTOMERS).as("C")
                .groupBy(column("C.country"))
                .having().count(column("C.id")).gt(set(10));
        String result = sql.getSQL();
        String expected = "SELECT  COUNT(C.id), C.country FROM customer AS C  GROUP BY C.country HAVING  COUNT(C.id)>?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        ColumnValue cv0 = params.get(0);
        Assertions.assertTrue(10 == (Integer) cv0.getValue());
    }

    @Test
    public void listAllProductsStartingWithCha_or_Chan(){
        SelectOp sql = create().select().column(asList(PRODUCT_ID_COL, PRODUCT_NAME_COL, PRODUCT_UNIT_PRICE_COL))
                .from(TBL_PRODUCT).where().column(PRODUCT_NAME_COL).like("Cha_").or().column(PRODUCT_NAME_COL).like("Chan_");
        String result = sql.getSQL();
        String expected = "SELECT id,product_name,unit_price FROM product WHERE product_name LIKE ? OR product_name LIKE ?";
        Assertions.assertEquals(result, expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(2, params.size());
    }

    @Test
    public void selfJoin(){
        /**Match customers that are from the same city and country*/
        
        SelectOp sql = SelectOp.create().select()
                .column(asList(column("c1.first_name"), column("c1.last_name"),
                        column("c2.first_name"), column("c2.last_name"),
                        column("c2.city"), column("c2.country")))
                .from(asList(Table.as(TBL_CUSTOMERS.getTableName(), "c1"),Table.as(TBL_CUSTOMERS.getTableName(), "c2")))
                .where(column("c1.id")).ne(column("c2.id")).and(column("c1.city")).eq(column("c2.city"))
                .and(column("c1.country")).eq(column("c2.country")).orderBy(column("c1.country"));
        String result = sql.getSQL();
        String expected = "SELECT c1.first_name,c1.last_name,c2.first_name,c2.last_name,"
                +"c2.city,c2.country FROM customer c1,customer c2 WHERE c1.id<>c2.id AND c1.city=c2.city AND c1.country=c2.country ORDER BY c1.country";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void union(){

        SelectOp sql = SelectOp.create().select().column("c.*").from("customer c")
                .union()
                .select().column("s.*").from("customer c1");
        String result = sql.getSQL();
        String expected = "SELECT c.* FROM customer c UNION SELECT s.* FROM customer c1";
        Assertions.assertEquals(result,expected);
    }
    
    @Test
    public void intersectUsingInnerJoin(){
        /*Simulate INTERSECT operator in MySQL using DISTINCT and INNER JOIN*/
        SelectOp sql = SelectOp.create().select().distinct().column("id").from("t1").inner().join().table("t2").using("id");
        String result = sql.getSQL();
        String expected = "SELECT  DISTINCT id FROM t1 INNER JOIN t2 USING(id)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void insersectUsingInAndSubQuery(){
        /*Simulate INTERSECT operator in MySQL using IN and Subquery*/
        SelectOp sql = SelectOp.create().select().distinct().column("id").from("t1").where()
                .column("id").in(create().select().column("id").from("t2"));
        String result = sql.getSQL();
        String expected = "SELECT  DISTINCT id FROM t1 WHERE id IN (SELECT id FROM t2)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void minus(){
        SelectOp sql = SelectOp.create().select().column("id").from("t1").minus().select().column("id").from("t2");
        String result = sql.getSQL();
        String expected = "SELECT id FROM t1 MINUS SELECT id FROM t2";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void minusSimulationUsingJoin(){
        SelectOp sql = SelectOp.create().select().column("id").from("t1").left().join().table("t2").using("id")
                .where().column("t2.id").is().nul();
        String result = sql.getSQL();
        String expected = "SELECT id FROM t1 LEFT JOIN t2 USING(id) WHERE t2.id IS  NULL";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void findDuplicates(){
        SelectOp sql = SelectOp.create().select().count(PERSON_EMAIL).with(PERSON_EMAIL)
                .from(TBL_PERSON).groupBy(PERSON_EMAIL).having().count(PERSON_EMAIL).gt().values(set(1));
        String result = sql.getSQL();
        String expected = "SELECT  COUNT(email), email FROM person GROUP BY email HAVING  COUNT(email)>?";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
    }

    @Test
    public void crossJoin(){
        SelectOp sql = SelectOp.create().select().all().from(asList(TBL_PRODUCT, TBL_ORDERS));
        String result = sql.getSQL();
        String expected = "SELECT * FROM product,orders";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void testExists(){
        SelectOp sql = create().select().all().from(TBL_SUPPLIER).as("s1").where()
                .exists(
                        create().select(PRODUCT_NAME_COL).as("prd_name").from(TBL_PRODUCT)
                        .where(PRODUCT_SUPPLIERID_COL).eq(column("s1.id")).and(PRODUCT_UNIT_PRICE_COL).lt().values(set(20))
                );
        String result = sql.getSQL();
        String expected = "SELECT * FROM supplier AS s1  WHERE  EXISTS (SELECT product_name AS prd_name  FROM product WHERE supplier_id=s1.id AND unit_price<? )";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
    }
    
    @Test
    public void testNotExists(){
        SelectOp sql = create().select().all().from(TBL_SUPPLIER).as("s1").where().not()
                .exists(
                        create().select(PRODUCT_NAME_COL).from(TBL_PRODUCT).as("prd1")
                        .where(PRODUCT_SUPPLIERID_COL).eq(column("s1.id")).and(PRODUCT_UNIT_PRICE_COL).lt().values(set(20))
                );
        String result = sql.getSQL();
        String expected = "SELECT * FROM supplier AS s1  WHERE  NOT  EXISTS (SELECT product_name FROM product AS prd1  WHERE supplier_id=s1.id AND unit_price<? )";
        Assertions.assertEquals(result,expected);
        List<ColumnValue> params = sql.getValues();
        Assertions.assertEquals(1, params.size());
    }
    
    @Test()
    public void shouldFail() {
        Exception assertThrows = Assertions.assertThrows(RuntimeException.class, ()->{
            create().select().all().from(TBL_SUPPLIER).where().not()
                    .exists(
                            create().select(PRODUCT_NAME_COL).from(TBL_PRODUCT)
                                    .where(PRODUCT_SUPPLIERID_COL).eq(SUPPLIER_ID_COL).and(PRODUCT_UNIT_PRICE_COL).lt().values(set(new Person()))
                    );
        },"Invalid type of value passed");
        Assertions.assertTrue(assertThrows.getMessage().contains("Invalid type of value passed"));
    }

}

package com.umar.simply.jdbc.dml.nojdbc;

import com.umar.simply.jdbc.dao.Person;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.sample.schema.metadata.ExSchema;


import static com.umar.simply.jdbc.meta.Column.column;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static com.umar.simply.jdbc.dml.operations.SelectOp.create;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Order.TblOrder.*;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Person.TblPerson.*;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Product.TblProduct.*;

import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Customer.TblCustomer.*;
import static com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Supplier.TblSupplier.*;
import static java.util.Arrays.asList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SqlQueryTest {
    @Test
    public void selectDistinctColumn(){
        SelectOp select = create();
        select.select()
                .distinct(ExSchema.Person.TblPerson.country)
                .from("supplier")
                .orderBy("country")
                .asc();
        String result = select.getSQL();
        String expected = "SELECT DISTINCT country FROM supplier ORDER BY country ASC";
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void selectCountDistinctColumn(){
        SelectOp sql = create();
        sql.select().count(create().distinct("country")).from("supplier");
        String result = sql.getSQL();
        String expected = "SELECT COUNT(DISTINCT country) FROM supplier";
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void selectMySQLTop10() {
        SelectOp sql = create();
        sql.select().column(asList(c1_cuntry, c1_city)).from(c1).limit(10);
        String result = sql.getSQL();
        String expected = "SELECT c1.country,c1.city FROM customer c1 LIMIT ?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void selectMySQLOffset6To10() {
        SelectOp sql = create();
        sql.select().column(asList(c1_cuntry, c1_city)).from(c1).limit(5).offset(5);
        String result = sql.getSQL();
        String expected = "SELECT c1.country,c1.city FROM customer c1 LIMIT ? OFFSET ?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void selectUsingAlias() {

        SelectOp sql = create();
        sql.select().column(asList(c1_id, c1_cuntry, c1_city)).from(c1);
        String result = sql.getSQL();
        String expected = "SELECT c1.id,c1.country,c1.city FROM customer c1";
        Assertions.assertEquals(result, expected);
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
        sql.select().all().from(c1)
                .where().column(asList(c1_cuntry)).in(create().values(set("Germany"), set("US")));
        String result = sql.getSQL();
        //"SELECT * FROM customer c1 WHERE country IN ('Germany','US')";
        String expected = "SELECT * FROM customer c1 WHERE c1.country IN (?,?)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void inUsingSchemaVars() {
        SelectOp sql = create();
        sql.select().all().from(c1)
                .where().column(asList(c1_cuntry))
                .in(create().values(set("Germany"), set("US")));
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer c1 WHERE c1.country IN (?,?)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void whereInUsingExSchemaVars() {
        SelectOp sql = create();
        sql.select().all().from(c1).whereIn(c1_cuntry, asList(set("Germany"), set("US")));
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer c1 WHERE c1.country IN (?,?)";
        Assertions.assertEquals(result,expected);
    }

   @Test
    public void notIn() {
        SelectOp sql = create();
        sql.select().all().from(c1)
                .where().column(c1_cuntry).not().in(
                create().values(set("Germany"), set("US")));
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer c1 WHERE c1.country NOT  IN (?,?)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void notInSubQuery() {
        SelectOp sql = create();
        sql.select().all().from(c1)
                .where().column(c1_fname).not().in(
                create().select().column(p1_fname).from(p1));
        String result = sql.getSQL();
        String expected = "SELECT * FROM customer c1 WHERE c1.first_name NOT  IN (SELECT p1.firstname FROM person p1)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void avg() {
        SelectOp sql = create();
        sql.select().avg(unitprice).from(product);
        String result = sql.getSQL();
        String expected = "SELECT AVG(unit_price) FROM product";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void max() {
        SelectOp sql = create();
        sql.select().max(unitprice).from(prd1);
        String result = sql.getSQL();
        String expected = "SELECT MAX(unit_price) FROM product prd1";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void min() {
        SelectOp sql = create();
        sql.select().min(column("price")).from("product");
        String result = sql.getSQL();
        String expected = "SELECT MIN(price) FROM product";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void sum(){
        SelectOp sql = create();
        sql.select().sum(unitprice).from("product");
        String result = sql.getSQL();
        String expected = "SELECT SUM(unit_price) FROM product";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void asTest() {
        SelectOp sql = create();
        sql.select().column(p1_fname).as("NAME").from(p1);
        String result = sql.getSQL();
        String expected = "SELECT p1.firstname AS NAME  FROM person p1";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void likeStartingWith_a() {
        SelectOp sql = create();
        sql.select().all().from(p1).where().column(p1_fname).like("a%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person p1 WHERE p1.firstname LIKE ?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void likeEndingWith_a() {
        SelectOp sql = create();
        sql.select().all().from(p1).where().column(p1_fname).like("%a");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person p1 WHERE p1.firstname LIKE ?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void nameHaving_or() {
        SelectOp sql = create();
        sql.select().all().from(p1).where().column(p1_fname).like("%or%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person p1 WHERE p1.firstname LIKE ?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void nameHaving_r_InSecondPos() {
        SelectOp sql = create();
        sql.select().all().from(p1).where().column(p1_fname).like("_r%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person p1 WHERE p1.firstname LIKE ?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void nameStartingWith_a_AndAtleast3Chars() {
        SelectOp sql = create();
        sql.select().all().from(p1).where().column(p1_fname).like("a_%_%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person p1 WHERE p1.firstname LIKE ?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void nameStartsWith_a_AndEndsWith_o() {
        SelectOp sql = create();
        sql.select().all().from(p1).where().column(p1_fname).like("a%o");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person p1 WHERE p1.firstname LIKE ?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void notStartWith_a() {
        SelectOp sql = create();
        sql.select().all().from(p1).where().column(p1_fname).not().like("a%");
        String result = sql.getSQL();
        String expected = "SELECT * FROM person p1 WHERE p1.firstname NOT  LIKE ?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void betweenTextValues(){
        SelectOp sql = create().select().all().from("product").where().column(prd_name)
                .between(asList(set("Carnarvon Tigers"),set("Mozzarella di Giovanni")));
        String result = sql.getSQL();
        //SELECT * FROM product WHERE product_name BETWEEN 'Carnarvon Tigers' AND 'Mozzarella di Giovanni'
        String expected = "SELECT * FROM product WHERE product_name BETWEEN ? AND ?";
        Assertions.assertEquals(result,expected);
    }

   @Test
    public void betweenWithIn(){
        SelectOp sql = create().select().all().from("product").where()
                .beginComplex().column(unitprice).between(asList(set(10), set(20))).endComplex()
                .and().not().column(cat_id).in(create().values(set(1), set(2), set(3)));
        String result = sql.getSQL();
        //"SELECT * FROM product WHERE (unit_price BETWEEN '10' AND '20') AND  NOT category_id IN ('1','2','3')"
        String expected = "SELECT * FROM product WHERE (unit_price BETWEEN ? AND ?) AND  NOT category_id IN (?,?,?)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void betweenVariant(){
        SelectOp sql = create().select().all().from("product").where()
                .beginComplex().column(unitprice).between(asList(set(10), set(20), set(30))).endComplex()
                .and().not().column(cat_id).in(create().values(set(1), set(2), set(3)));
        String result = sql.getSQL();
        //"SELECT * FROM product WHERE (unit_price BETWEEN 10 AND 20 AND 30) AND  NOT category_id IN (1,2,3)";
        String expected = "SELECT * FROM product WHERE (unit_price BETWEEN ? AND ? AND ?) AND  NOT category_id IN (?,?,?)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void notBetween(){
        SelectOp sql = create().select().all().from("product")
                .where().column(unitprice).not().between(asList(set(10),set(20)));
        String result = sql.getSQL();
        String expected = "SELECT * FROM product WHERE unit_price NOT  BETWEEN ? AND ?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void isNull(){
        SelectOp sql = create().select().all().from("product")
                .where().column(prd_name).is().values(set(null));
        String result = sql.getSQL();
        String expected = "SELECT * FROM product WHERE product_name IS ?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void orderBy(){
        SelectOp sql = create().select().column(asList(productId, prd_name, unitprice))
                .from("product").where().column(unitprice).eq().values(set(20))
                .orderBy("product_name");
        String result = sql.getSQL();
        String expected = "SELECT id,product_name,unit_price FROM product WHERE unit_price=? ORDER BY product_name";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void groupBy(){
        SelectOp sql = create().select().count(c1_id)
                .with(c1_cuntry)
                .from("customer c1").groupBy(c1_cuntry);
        String result = sql.getSQL();
        String expected = "SELECT COUNT(c1.id), c1.country FROM customer c1 GROUP BY c1.country";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void groupByOrderByDesc(){
        SelectOp sql = create()
                .select().count(c1_id)
                .with(c1_cuntry)
                .from("customer c1")
                .groupBy(c1_cuntry)
                .orderBy(create().count(c1_id).getSQL()).desc();
        String result = sql.getSQL();
        String expected = "SELECT COUNT(c1.id), c1.country FROM customer c1 GROUP BY c1.country ORDER BY COUNT(c1.id) DESC";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void totalAmtOrderedForEachCustomer(){
        SelectOp sql = create().select().sum(o1_totalAmount).with(asList(c1_fname,c1_lname))
                .from(o1)
                .join().table(c1)
                .on().column(o1_customerId).eq().column(c1_id)
                .groupBy(asList(c1_fname,c1_lname))
                .orderBy(create().sum(o1_totalAmount).getSQL()).desc();
        String result = sql.getSQL();
        String expected = "SELECT SUM(o1.total_amount),c1.first_name,c1.last_name FROM order o1 JOIN customer c1 ON o1.customer_id=c1.id GROUP BY c1.first_name,c1.last_name ORDER BY SUM(o1.total_amount) DESC";
        Assertions.assertEquals(result,expected);
    }

    /*
       Tests for HAVING CLAUSE
     */
    @Test
    public void havingCount(){
        SelectOp sql = create()
                .select().count(c1_id).with(c1_cuntry)
                .from(c1)
                .groupBy(c1_cuntry)
                .having().count(c1_id).gt(set(10));
        String result = sql.getSQL();
        String expected = "SELECT COUNT(c1.id), c1.country FROM customer c1 GROUP BY c1.country HAVING COUNT(c1.id)>?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void listAllProductsStartingWithCha_or_Chan(){
        SelectOp sql = create().select().column(asList(productId, prd_name, unitprice))
                .from("product").where().column(prd_name).like("Cha_").or().column(prd_name).like("Chan_");
        String result = sql.getSQL();
        String expected = "SELECT id,product_name,unit_price FROM product WHERE product_name LIKE ? OR product_name LIKE ?";
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void selfJoin(){
        /**Match customers that are from the same city and country*/
        
        SelectOp sql = SelectOp.create().select()
                .column(asList(c1_fname, c1_lname,
                        c2_fname, c2_lname,
                        c2_city, c2_cuntry)).from(asList(c1,c2))
                .where(c1_id).ne(c2_id).and(c1_city).eq(c2_city)
                .and(c1_cuntry).eq(c2_cuntry).orderBy(c1_cuntry);
        String result = sql.getSQL();
        String expected = "SELECT c1.first_name,c1.last_name,c2.first_name,c2.last_name,"
                +"c2.city,c2.country FROM(customer c1,customer c2) WHERE c1.id<>c2.id AND c1.city=c2.city AND c1.country=c2.country ORDER BY c1.country";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void selfJoinUsingExSchema(){
        /*Match customers that are from the same city and country*/

        SelectOp sql = SelectOp.create().select()
                .column(asList(p2_fname, p2_lname, p1_fname, p1_lname, p2_city, p2_country)).from(asList(p1, p2))
                .where(p1_id).ne(p2_id).and(p1_city).eq(p2_city).and(p1_country).eq(p2_country).orderBy(p2_country);
        String result = sql.getSQL();
        String expected = "SELECT p2.firstname,p2.lastname,p1.firstname,p1.lastname,p2.city,p2.country FROM(person p1,person p2) WHERE p1.id<>p2.id AND p1.city=p2.city AND p1.country=p2.country ORDER BY p2.country";
        Assertions.assertEquals(result,expected);
    }


    @Test
    public void union(){
        /*List all suppliers and customers*/
        SelectOp sql = SelectOp.create().select().column("c.*").from("customer c")
                .union()
                .select().column("s.*").from("supplier s");
        String result = sql.getSQL();
        String expected = "SELECT c.* FROM customer c UNION SELECT s.* FROM supplier s";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void intersectUsingInnerJoin(){
        /*Simulate INTERSECT operator in MySQL using DISTINCT and INNER JOIN*/
        SelectOp sql = SelectOp.create().select().distinct().column("id").from("t1").inner().join().table("t2").using("id");
        String result = sql.getSQL();
        String expected = "SELECT DISTINCT id FROM t1 INNER JOIN t2 USING(id)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void insersectUsingInAndSubQuery(){
        /*Simulate INTERSECT operator in MySQL using IN and Subquery*/
        SelectOp sql = SelectOp.create().select().distinct().column("id").from("t1").where()
                .column("id").in(create().select().column("id").from("t2"));
        String result = sql.getSQL();
        String expected = "SELECT DISTINCT id FROM t1 WHERE id IN (SELECT id FROM t2)";
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
        SelectOp sql = SelectOp.create().select().count(email).with(email)
                .from(person).groupBy(email).having().count(email).gt().values(set(1));
        String result = sql.getSQL();
        String expected = "SELECT COUNT(email), email FROM person GROUP BY email HAVING COUNT(email)>?";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void crossJoin(){
        SelectOp sql = SelectOp.create().select().all().from(asList(prd1, o1));
        String result = sql.getSQL();
        String expected = "SELECT * FROM(product prd1,order o1)";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void testExists(){
        SelectOp sql = create().select().all().from(s1_supplier).where()
                .exists(
                        create().select(prd1_productname).from(prd1)
                        .where(prd1_suppid).eq(s1_Id).and(prd1_unitprice).lt().values(set(20))
                );
        String result = sql.getSQL();
        String expected = "SELECT * FROM supplier s1 WHERE EXISTS (SELECT prd1.product_name FROM product prd1 WHERE prd1.supplier_id=s1.id AND prd1.unit_price<? )";
        Assertions.assertEquals(result,expected);
    }

    @Test
    public void testNotExists(){
        SelectOp sql = create().select().all().from(s1_supplier).where().not()
                .exists(
                        create().select(prd1_productname).from(prd1)
                        .where(prd1_suppid).eq(s1_Id).and(prd1_unitprice).lt().values(set(20))
                );
        String result = sql.getSQL();
        String expected = "SELECT * FROM supplier s1 WHERE  NOT EXISTS (SELECT prd1.product_name FROM product prd1 WHERE prd1.supplier_id=s1.id AND prd1.unit_price<? )";
        Assertions.assertEquals(result,expected);
    }

    @Test()
    public void shouldFail() {
        Exception assertThrows = Assertions.assertThrows(RuntimeException.class, ()->{
            create().select().all().from(s1_supplier).where().not()
                    .exists(
                            create().select(prd1_productname).from(prd1)
                                    .where(prd1_suppid).eq(s1_Id).and(prd1_unitprice).lt().values(set(new Person()))
                    );
        },"Invalid type of value passed");
        Assertions.assertTrue(assertThrows.getMessage().contains("Invalid type of value passed"));
    }

}

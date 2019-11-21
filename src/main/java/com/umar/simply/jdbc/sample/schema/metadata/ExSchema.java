
package com.umar.simply.jdbc.sample.schema.metadata;

import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.Table;


import static com.umar.simply.jdbc.meta.Column.*;
import java.time.LocalDateTime;

/**
 * All table structures, views etc will be enclosed within Ex db Schema
 */
public class ExSchema {
    public static final String EX_SCHEMA = "ex";
    public interface Supplier{
        interface TblSupplier{
            Column<Integer> supplierId = column("id");
            Column<String> supplierName = column("supplier_name");
            Column<String> contactName = column("contact_name");
            Column<String> supplierAddr = column("supplier_address");
            Column<LocalDateTime> created = column("created");
            Column<LocalDateTime> updated = column("updated");
            Table supplier = Table.table("supplier", supplierId);

            Column<Long> s1_Id = as("id", "s1");
            Column<String> s1_supplierName = as("supplier_name", "s1");
            Column<String> s1_contactName = as("contact_name", "s1");
            Column<String> s1_suppplierAddr = as("supplier_address", "s1");
            Column<LocalDateTime> s1_created = as("created", "s1");
            Column<LocalDateTime> s1_updated = as("updated", "s1");
            Table s1_supplier = Table.as("supplier", "s1", supplierId);
        }

    }
    /**
     * The Product table as per db schema
     */
    public interface Product {
        /*Should be used while inserting/updating elements*/
        interface TblProduct{
            Column<Integer> productId = column("id");
            Column<String> prd_name = column("product_name");
            Column<Integer> suppid = column("supplier_id");
            Column<Double> unitprice = column("unit_price");
            Column<Boolean> discontinued = column("is_discontinued");
            Column<Integer> created = column("created");
            Column<LocalDateTime> updated = column("updated");
            Column<Integer> cat_id = column("category_id");
            
            Table product = Table.table("product", productId);

            Column<Integer> prd1_id = as("id", "prd1");
            Column<String> prd1_productname = as("product_name", "prd1");
            Column<Integer> prd1_suppid = as("supplier_id", "prd1");
            Column<Double> prd1_unitprice = as("unit_price", "prd1");
            Column<Boolean> prd1_discontinued = as("is_discontinued", "prd1");
            Column<Integer> prd1_cat_id = as("category_id", "prd1");
            Column<LocalDateTime> prd1_created = as("created", "prd1");
            Column<LocalDateTime> prd1_updated = as("updated", "prd1");
            Table prd1 = Table.as("product","prd1", productId);

            Column<Integer> prd2_id = as("id", "prd2");
            Column<String> prd2_productname = as("product_name", "prd2");
            Column<Integer> prd2_suppid = as("supplier_id", "prd2");
            Column<Double> prd2_unitprice = as("unit_price", "prd2");
            Column<Boolean> prd2_discontinued = as("is_discontinued", "prd2");
            Column<Integer> prd2_cat_id = as("category_id", "prd2");
            Column<LocalDateTime> prd2_created = as("created", "prd2");
            Column<LocalDateTime> prd2_updated = as("updated", "prd2");
            Table prd2 = Table.as("product","prd2", productId);
        }

    }

    public interface Person {
        /*Should be used while inserting/updating elements*/
        interface TblPerson {
            Column<Integer> personId = column("id");
            Column<String> fname = column("firstname");
            Column<String> lname = column("lastname");
            Column<String> email = column("email");
            Column<Boolean> adult = column("adult");
            Column<LocalDateTime> created = column("created");
            Column<LocalDateTime> updated = column("updated");
            Column<String> city = column("city");
            Column<String> country = column("country");
            Column<Integer> age = column("age");
            Table person = Table.table("person", personId);

            Column<Integer> p1_id = as("id", "p1");
            Column<String> p1_fname = as("firstname", "p1");
            Column<String> p1_lname = as("lastname", "p1");
            Column<String> p1_email = as("email", "p1");
            Column<Boolean> p1_adult = as("adult", "p1");
            Column<LocalDateTime> p1_created = as("created", "p1");
            Column<String> p1_city = as("city", "p1");
            Column<String> p1_country = as("country", "p1");
            Column<Integer> p1_age = as("age", "p1");
            Table p1 = Table.as("person","p1", personId);

            Column<Integer> p2_id = as("id", "p2");
            Column<String> p2_fname = as("firstname", "p2");
            Column<String> p2_lname = as("lastname", "p2");
            Column<String> p2_email = as("email", "p2");
            Column<Boolean> p2_adult = as("adult", "p2");
            Column<LocalDateTime> p2_created = as("created", "p2");
            Column<String> p2_city = as("city", "p2");
            Column<String> p2_country = as("country", "p2");
            Column<Integer> p2_age = as("age", "p2");
            Table p2 = Table.as("person","p2", personId);
        }
    }

    public interface Customer {
        /*Should be used while inserting/updating elements*/
        interface TblCustomer{
            Column<Integer> customerId = column("id");
            Column<String> country = column("country");
            Column<String> city = column("city");
            Column<String> fname = column("first_name");
            Column<String> lname = column("last_name");
            Column<LocalDateTime> created = column("created");
            Column<LocalDateTime> updated = column("updated");
            Table customer = Table.table("customer", customerId);

            Column<Integer> c1_id = as("id", "c1");
            Column<String> c1_cuntry = as("country", "c1");
            Column<String> c1_city = as("city", "c1");
            Column<String> c1_fname = as("first_name","c1");
            Column<String> c1_lname = as("last_name","c1");
            Column<LocalDateTime> c1_created = as("created", "c1");
            Column<LocalDateTime> c1_updated = as("updated", "c1");
            Table c1 = Table.as("customer","c1", customerId);

            Column<Integer> c2_id = as("id", "c2");
            Column<String> c2_cuntry = as("country", "c2");
            Column<String> c2_city = as("city", "c2");
            Column<String> c2_fname = as("first_name","c2");
            Column<String> c2_lname = as("last_name","c2");
            Column<LocalDateTime> c2_created = as("created", "c2");
            Column<LocalDateTime> c2_updated = as("updated", "c2");
            Table c2 = Table.as("customer","c2", customerId);
        }
    }

    public interface Order {
        interface TblOrder {
            Column<Integer> orderId = column("id");
            Column<LocalDateTime> orderDate = column("order_date");
            Column<Integer> orderNo = column("order_number");
            Column<Integer> customerId = column("customer_id");
            Column<Double> totalAmount = column("total_amount");
            Column<LocalDateTime> created = column("created");
            Column<LocalDateTime> updated = column("updated");
            Table order = Table.table("orders", orderId); //ORDER can't be a table identifier. Its a command type

            Column<Integer> o1_id = as("id", "o1");
            Column<LocalDateTime> o1_orderDate = as("order_date", "o1");
            Column<Long> o1_orderNo = as("order_number", "o1");
            Column<Integer> o1_customerId = as("customer_id", "o1");
            Column<Double> o1_totalAmount = as("total_amount", "o1");
            Column<LocalDateTime> o1_created = as("created", "o1");
            Column<LocalDateTime> o1_updated = as("updated", "o1");
            Table o1 = Table.as("order", "o1", orderId);

            Column<Integer> o2_id = as("id", "o2");
            Column<LocalDateTime> o2_orderDate = as("order_date", "o2");
            Column<Integer> o2_orderNo = as("order_number", "o2");
            Column<Integer> o2_customerId = as("customer_id", "o2");
            Column<Double> o2_totalAmount = as("total_amount", "o2");
            Column<LocalDateTime> o2_created = as("created", "o2");
            Column<LocalDateTime> o2_updated = as("updated", "o2");
            Table o2 = Table.as("order", "o2", orderId);
        }
    }

}


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
            Column<String> contactName = column("supplier_contact");
            Column<String> supplierAddr = column("supplier_address");
            Column<LocalDateTime> created = column("created");
            Column<LocalDateTime> updated = column("updated");
            Table supplier = Table.table("supplier", supplierId);

            Column<Long> s1_Id = as("s1", "id");
            Column<String> s1_supplierName = as("s1","supplier_name");
            Column<String> s1_contactName = as("s1","contact_name");
            Column<String> s1_suppplierAddr = as("s1","supplier_address");
            Column<LocalDateTime> s1_created = as("s1","created");
            Column<LocalDateTime> s1_updated = as("s1","updated");
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

            Column<Integer> prd1_id = as("prd1", "id");
            Column<String> prd1_productname = as("prd1", "product_name");
            Column<Integer> prd1_suppid = as("prd1","supplier_id");
            Column<Double> prd1_unitprice = as("prd1","unit_price");
            Column<Boolean> prd1_discontinued = as("prd1","is_discontinued");
            Column<Integer> prd1_cat_id = as("prd1","category_id");
            Column<LocalDateTime> prd1_created = as("prd1","created");
            Column<LocalDateTime> prd1_updated = as("prd1","updated");
            Table prd1 = Table.as("product","prd1", productId);

            Column<Integer> prd2_id = as("prd2","id");
            Column<String> prd2_productname = as("prd2","product_name");
            Column<Integer> prd2_suppid = as("prd2","supplier_id");
            Column<Double> prd2_unitprice = as("prd2","unit_price");
            Column<Boolean> prd2_discontinued = as("prd2","is_discontinued");
            Column<Integer> prd2_cat_id = as("prd2","category_id");
            Column<LocalDateTime> prd2_created = as("prd2","created");
            Column<LocalDateTime> prd2_updated = as("prd2","updated");
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

            Column<Integer> p1_id = as("p1", "id");
            Column<String> p1_fname = as("p1","firstname");
            Column<String> p1_lname = as("p1","lastname");
            Column<String> p1_email = as("p1","email");
            Column<Boolean> p1_adult = as("p1","adult");
            Column<LocalDateTime> p1_created = as("p1","created");
            Column<String> p1_city = as("p1","city");
            Column<String> p1_country = as("p1","country");
            Column<Integer> p1_age = as("p1","age");
            Table p1 = Table.as("person","p1", personId);

            Column<Integer> p2_id = as("p2", "id");
            Column<String> p2_fname = as("p2","firstname");
            Column<String> p2_lname = as("p2","lastname");
            Column<String> p2_email = as("p2","email");
            Column<Boolean> p2_adult = as("p2","adult");
            Column<LocalDateTime> p2_created = as("p2","created");
            Column<String> p2_city = as("p2","city");
            Column<String> p2_country = as("p2","country");
            Column<Integer> p2_age = as("p2","age");
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

            Column<Integer> c1_id = as("c1","id");
            Column<String> c1_cuntry = as("c1","country" );
            Column<String> c1_city = as("c1", "city");
            Column<String> c1_fname = as("c1", "first_name");
            Column<String> c1_lname = as("c1", "last_name");
            Column<LocalDateTime> c1_created = as("c1", "created");
            Column<LocalDateTime> c1_updated = as("c1", "updated");
            Table c1 = Table.as("customer","c1", customerId);

            Column<Integer> c2_id = as("c2", "id");
            Column<String> c2_cuntry = as("c2", "country");
            Column<String> c2_city = as("c2","city");
            Column<String> c2_fname = as("c2","first_name");
            Column<String> c2_lname = as("c2","last_name");
            Column<LocalDateTime> c2_created = as("c2","created");
            Column<LocalDateTime> c2_updated = as("c2","updated");
            Table c2 = Table.as("customer","c2", customerId);
        }
    }

    public interface Order {
        interface TblOrder {
            Column<Integer> orderId = column("id");
            Column<LocalDateTime> orderDate = column("order_date");
            Column<Integer> orderNo = column("order_number");
            Column<Integer> orderCustomerId = column("customer_id");
            Column<Double> totalAmount = column("total_amount");
            Column<LocalDateTime> created = column("created");
            Column<LocalDateTime> updated = column("updated");
            Table order = Table.table("orders", orderId); //ORDER can't be a table identifier. Its a command type

            Column<Integer> o1_id = as("o1", "id");
            Column<LocalDateTime> o1_orderDate = as("o1","order_date");
            Column<Long> o1_orderNo = as("o1","order_number");
            Column<Integer> o1_customerId = as("o1","customer_id");
            Column<Double> o1_totalAmount = as("o1","total_amount");
            Column<LocalDateTime> o1_created = as("o1","created");
            Column<LocalDateTime> o1_updated = as("o1","updated");
            Table o1 = Table.as("order", "o1", orderId);

            Column<Integer> o2_id = as("o2", "id");
            Column<LocalDateTime> o2_orderDate = as("o2","order_date");
            Column<Integer> o2_orderNo = as("o2","order_number");
            Column<Integer> o2_customerId = as("o2","customer_id");
            Column<Double> o2_totalAmount = as("o2","total_amount");
            Column<LocalDateTime> o2_created = as("o2","created");
            Column<LocalDateTime> o2_updated = as("o2","updated");
            Table o2 = Table.as("order", "o2", orderId);
        }
    }

}

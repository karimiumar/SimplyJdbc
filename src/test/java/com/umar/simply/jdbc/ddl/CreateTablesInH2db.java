package com.umar.simply.jdbc.ddl;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static com.umar.simply.jdbc.fluent.dao.person.Person.TblPerson.*;
import com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Product.TblProduct;
import com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Customer.TblCustomer;
import com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Order.TblOrder;
import com.umar.simply.jdbc.sample.schema.metadata.ExSchema.Supplier.TblSupplier;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateTablesInH2db {
    final static String EX_SCHEMA = "ex";
   
    @Test
    @Order(1)
    public void dropSchemaSchemaEx() {
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("DROP SCHEMA IF EXISTS ");
        ddlBuilder.append(EX_SCHEMA);
        ddlBuilder.append(" CASCADE ;");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);        
    }
    
    @Test
    @Order(2)
    public void createSchemaEx() {
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("CREATE SCHEMA IF NOT EXISTS ");
        ddlBuilder.append(EX_SCHEMA);
        ddlBuilder.append(" AUTHORIZATION  sa;");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);
    }
    
    @Test
    @Order(3)
    public void createPerson() {
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("create table ");
        ddlBuilder.append(EX_SCHEMA);
        ddlBuilder.append(".");
        ddlBuilder.append(person);
        ddlBuilder.append("(");
        ddlBuilder.append(personId);
        ddlBuilder.append("  bigint auto_increment primary key, ");
        ddlBuilder.append(fname);
        ddlBuilder.append("  varchar(20), ");
        ddlBuilder.append(lname);
        ddlBuilder.append("  varchar(20), " );
        ddlBuilder.append(adult);
        ddlBuilder.append("  tinyint(1), " );
        ddlBuilder.append(age);
        ddlBuilder.append("  int(3), " );
        ddlBuilder.append(email);
        ddlBuilder.append("  varchar(25), " );
        ddlBuilder.append(city);
        ddlBuilder.append("  varchar(15), " );
        ddlBuilder.append(country);
        ddlBuilder.append("  varchar(20), ");
        ddlBuilder.append(created);
        ddlBuilder.append("  timestamp,  " );
        ddlBuilder.append(updated);
        ddlBuilder.append("  timestamp  " );
        ddlBuilder.append(");");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);
    }
    
    @Test
    public void createSupplier() {
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("create table ");
        ddlBuilder.append(EX_SCHEMA);
        ddlBuilder.append(".");
        ddlBuilder.append(TblSupplier.supplier);
        ddlBuilder.append("(");
        ddlBuilder.append(TblSupplier.supplierId);
        ddlBuilder.append("  bigint auto_increment primary key, ");
        ddlBuilder.append(TblSupplier.supplierName);
        ddlBuilder.append("  varchar(20), ");
        ddlBuilder.append(TblSupplier.contactName);
        ddlBuilder.append("  varchar(20), " );
        ddlBuilder.append(TblSupplier.supplierAddr);
        ddlBuilder.append("  varchar(30), " );
        ddlBuilder.append(TblSupplier.created);
        ddlBuilder.append("  timestamp  , " );
        ddlBuilder.append(TblSupplier.updated);
        ddlBuilder.append(" timestamp  " );
        ddlBuilder.append(");");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);
    }
    
    @Test
    public void createCustomer() {
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("create table ");
        ddlBuilder.append(EX_SCHEMA);
        ddlBuilder.append(".");
        ddlBuilder.append(TblCustomer.customer);
        ddlBuilder.append("(");
        ddlBuilder.append(TblCustomer.customerId);
        ddlBuilder.append("  bigint auto_increment primary key, ");
        ddlBuilder.append(TblCustomer.fname);
        ddlBuilder.append("  varchar(20), ");
        ddlBuilder.append(TblCustomer.lname);
        ddlBuilder.append("  varchar(20), " );        
        ddlBuilder.append(TblCustomer.city);
        ddlBuilder.append("  varchar(15), " );
        ddlBuilder.append(TblCustomer.country);
        ddlBuilder.append("  varchar(20), ");
        ddlBuilder.append(TblCustomer.created);
        ddlBuilder.append("  timestamp  DEFAULT CURRENT_TIMESTAMP, " );
        ddlBuilder.append(TblCustomer.updated);
        ddlBuilder.append(" timestamp " );
        ddlBuilder.append(");");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);
    }
    
    @Test
    public void createProduct() {
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("create table ");
        ddlBuilder.append(EX_SCHEMA);
        ddlBuilder.append(".");
        ddlBuilder.append(TblProduct.product);
        ddlBuilder.append("(");
        ddlBuilder.append(TblProduct.productId);
        ddlBuilder.append("  bigint auto_increment primary key, ");
        ddlBuilder.append(TblProduct.prd_name);
        ddlBuilder.append("  varchar(20), ");
        ddlBuilder.append(TblProduct.suppid); //references Supplier.id
        ddlBuilder.append("  bigint, " ); 
        ddlBuilder.append(TblProduct.unitprice);
        ddlBuilder.append("  decimal, " );
        ddlBuilder.append(TblProduct.discontinued);
        ddlBuilder.append("  tinyint(1), " );
        ddlBuilder.append(TblProduct.cat_id); //references Category.id. Table not yet available
        ddlBuilder.append("  bigint, " ); 
        ddlBuilder.append(created);
        ddlBuilder.append("  timestamp  DEFAULT CURRENT_TIMESTAMP, " );
        ddlBuilder.append(TblSupplier.updated);
        ddlBuilder.append(" timestamp " );
        ddlBuilder.append(");");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);
    }
    
    @Test
    public void createOrder() {
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("create table ");
        ddlBuilder.append(EX_SCHEMA);
        ddlBuilder.append(".");
        ddlBuilder.append(TblOrder.order);
        ddlBuilder.append("(");
        ddlBuilder.append(TblOrder.orderId);
        ddlBuilder.append("  bigint auto_increment primary key, ");
        ddlBuilder.append(TblOrder.orderDate);
        ddlBuilder.append("  timestamp, ");
        ddlBuilder.append(TblOrder.orderNo);
        ddlBuilder.append("  bigint, " );
        ddlBuilder.append(TblOrder.customerId); // references Customer.id
        ddlBuilder.append("  bigint, " ); 
        ddlBuilder.append(TblOrder.totalAmount);
        ddlBuilder.append("  decimal, " );
        ddlBuilder.append(created);
        ddlBuilder.append("  timestamp  DEFAULT CURRENT_TIMESTAMP, " );
        ddlBuilder.append(TblSupplier.updated);
        ddlBuilder.append(" timestamp  " );
        ddlBuilder.append(");");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);
    }
    
    
    private void executeDDL(String ddl) {
        try (PreparedStatement ps = JdbcUtilService.getConnection().prepareStatement(ddl)) {
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CreateTablesInH2db.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

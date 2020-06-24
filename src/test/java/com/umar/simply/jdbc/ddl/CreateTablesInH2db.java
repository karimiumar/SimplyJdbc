package com.umar.simply.jdbc.ddl;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.CustomerTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.ProductTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.OrderTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.SupplierTable.*;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;


public class CreateTablesInH2db {
    private static final Logger logger = Logger.getLogger(CreateTablesInH2db.class.getName());
    public static void setup() {
        dropTablesInPublicSchema();
        createPerson();
        createSupplier();
        createCustomer();
        createProduct();
        createOrder();
    }

    public static void dropTablesInPublicSchema() {
        logger.log(Level.INFO, "Dropping Tables in Public Schema");
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("DROP TABLE IF EXISTS ");
        ddlBuilder.append(TBL_PERSON);
        ddlBuilder.append(";");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);  
        
        ddlBuilder = new StringBuilder();
        ddlBuilder.append("DROP TABLE IF EXISTS ");
        ddlBuilder.append(TBL_CUSTOMERS);
        ddlBuilder.append(";");
        ddl = ddlBuilder.toString();
        executeDDL(ddl);  
        
        ddlBuilder = new StringBuilder();
        ddlBuilder.append("DROP TABLE IF EXISTS ");
        ddlBuilder.append(TBL_PRODUCT);
        ddlBuilder.append(";");
        ddl = ddlBuilder.toString();
        executeDDL(ddl);  
        
        ddlBuilder = new StringBuilder();
        ddlBuilder.append("DROP TABLE IF EXISTS ");
        ddlBuilder.append(TBL_SUPPLIER);
        ddlBuilder.append(";");
        ddl = ddlBuilder.toString();
        executeDDL(ddl);  
        
        ddlBuilder = new StringBuilder();
        ddlBuilder.append("DROP TABLE IF EXISTS ");
        ddlBuilder.append(TBL_ORDERS);
        ddlBuilder.append(";");
        ddl = ddlBuilder.toString();
        executeDDL(ddl);  
    }

    public static void createPerson() {
        logger.log(Level.INFO, "Creating Person Table in Public Schema");
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("create table ");
        //ddlBuilder.append(EX_SCHEMA);
        //ddlBuilder.append(".");
        ddlBuilder.append(TBL_PERSON);
        ddlBuilder.append("(");
        ddlBuilder.append(PERSON_ID);
        ddlBuilder.append("  bigint auto_increment primary key, ");
        ddlBuilder.append(PERSON_FIRST_NAME);
        ddlBuilder.append("  varchar(20), ");
        ddlBuilder.append(PERSON_LAST_NAME);
        ddlBuilder.append("  varchar(20), " );
        ddlBuilder.append(PERSON_IS_ADULT);
        ddlBuilder.append("  tinyint(1), " );
        ddlBuilder.append(PERSON_AGE);
        ddlBuilder.append("  int(3), " );
        ddlBuilder.append(PERSON_EMAIL);
        ddlBuilder.append("  varchar(25), " );
        ddlBuilder.append(PERSON_CITY);
        ddlBuilder.append("  varchar(15), " );
        ddlBuilder.append(PERSON_COUNTRY);
        ddlBuilder.append("  varchar(20), ");
        ddlBuilder.append(PERSON_CREATED);
        ddlBuilder.append("  timestamp,  " );
        ddlBuilder.append(PERSON_UPDATED);
        ddlBuilder.append("  timestamp  " );
        ddlBuilder.append(");");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);
    }

    public static void createSupplier() {
        logger.log(Level.INFO, "Creating Supplier Table in Public Schema");
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("create table ");
        //ddlBuilder.append(EX_SCHEMA);
       // ddlBuilder.append(".");
        ddlBuilder.append(TBL_SUPPLIER);
        ddlBuilder.append("(");
        ddlBuilder.append(SUPPLIER_ID_COL);
        ddlBuilder.append("  bigint auto_increment primary key, ");
        ddlBuilder.append(SUPPLIER_NAME_COL);
        ddlBuilder.append("  varchar(20), ");
        ddlBuilder.append(SUPPLIER_CONTACT_COL);
        ddlBuilder.append("  varchar(20), " );
        ddlBuilder.append(SUPPLIER_ADDR_COL);
        ddlBuilder.append("  varchar(30), " );
        ddlBuilder.append(SUPPLIER_CREATED_COL);
        ddlBuilder.append("  timestamp  , " );
        ddlBuilder.append(SUPPLIER_UPDATED_COL);
        ddlBuilder.append(" timestamp  " );
        ddlBuilder.append(");");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);
    }

    public static void createCustomer() {
        logger.log(Level.INFO, "Creating Customer Table in Public Schema");
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("create table ");
        //ddlBuilder.append(EX_SCHEMA);
        //ddlBuilder.append(".");
        ddlBuilder.append(TBL_CUSTOMERS);
        ddlBuilder.append("(");
        ddlBuilder.append(CUSTOMER_ID);
        ddlBuilder.append("  bigint auto_increment primary key, ");
        ddlBuilder.append(CUSTOMER_FIRST_NAME);
        ddlBuilder.append("  varchar(20), ");
        ddlBuilder.append(CUSTOMER_LAST_NAME);
        ddlBuilder.append("  varchar(20), " );        
        ddlBuilder.append(CUSTOMER_CITY);
        ddlBuilder.append("  varchar(15), " );
        ddlBuilder.append(CUSTOMER_COUNTRY);
        ddlBuilder.append("  varchar(20), ");
        ddlBuilder.append(CUSTOMER_CREATED);
        ddlBuilder.append("  timestamp, " );
        ddlBuilder.append(CUSTOMER_UPDATED);
        ddlBuilder.append(" timestamp " );
        ddlBuilder.append(");");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);
    }

    public static void createProduct() {
        logger.log(Level.INFO, "Creating Product Table in Public Schema");
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("create table ");
        //ddlBuilder.append(EX_SCHEMA);
        //ddlBuilder.append(".");
        ddlBuilder.append(TBL_PRODUCT);
        ddlBuilder.append("(");
        ddlBuilder.append(PRODUCT_ID_COL);
        ddlBuilder.append("  bigint auto_increment primary key, ");
        ddlBuilder.append(PRODUCT_NAME_COL);
        ddlBuilder.append("  varchar(20), ");
        ddlBuilder.append(PRODUCT_SUPPLIERID_COL); //references Supplier.id
        ddlBuilder.append("  bigint, " ); 
        ddlBuilder.append(PRODUCT_UNIT_PRICE_COL);
        ddlBuilder.append("  decimal, " );
        ddlBuilder.append(PRODUCT_DISCONTINUED_COL);
        ddlBuilder.append("  tinyint(1), " );
        ddlBuilder.append(PRODUCT_CAT_ID_COL); //references Category.id. Table not yet available
        ddlBuilder.append("  bigint, " ); 
        ddlBuilder.append(PRODUCT_CREATED_COL);
        ddlBuilder.append("  timestamp, " );
        ddlBuilder.append(PRODUCT_UPDATED_COL);
        ddlBuilder.append(" timestamp " );
        ddlBuilder.append(");");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);
    }

    public static void createOrder() {
        logger.log(Level.INFO, "Creating Orders Table in Public Schema");
        StringBuilder ddlBuilder = new StringBuilder();
        ddlBuilder.append("create table ");
        //ddlBuilder.append(EX_SCHEMA);
        //ddlBuilder.append(".");
        ddlBuilder.append(TBL_ORDERS);
        ddlBuilder.append("(");
        ddlBuilder.append(ORDER_ID);
        ddlBuilder.append("  bigint auto_increment primary key, ");
        ddlBuilder.append(ORDER_DATE);
        ddlBuilder.append("  timestamp, ");
        ddlBuilder.append(ORDER_NO);
        ddlBuilder.append("  bigint, " );
        ddlBuilder.append(ORDER_CUSTOMERID); // references Customer.id
        ddlBuilder.append("  bigint, " ); 
        ddlBuilder.append(ORDER_TOTAL_AMT);
        ddlBuilder.append("  decimal, " );
        ddlBuilder.append(ORDER_CREATED);
        ddlBuilder.append("  timestamp, " );
        ddlBuilder.append(ORDER_UPDATED);
        ddlBuilder.append(" timestamp  " );
        ddlBuilder.append(");");
        String ddl = ddlBuilder.toString();
        executeDDL(ddl);
    }
    
    
    private static void executeDDL(String ddl) {
        try (PreparedStatement ps = JdbcUtilService.getConnection().prepareStatement(ddl)) {
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CreateTablesInH2db.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

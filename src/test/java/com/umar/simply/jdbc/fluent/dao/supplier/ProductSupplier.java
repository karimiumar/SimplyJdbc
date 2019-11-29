package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.RowMapper;
import static com.umar.simply.jdbc.fluent.dao.supplier.Product.TblProduct.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.Supplier.TblSupplier.*;
import com.umar.simply.jdbc.meta.Column;

/**
 * Resembles Supplier supplying Products/ Or Products of suppliers
 *
 */
public class ProductSupplier {

    private Supplier supplier;
    private Product product;
    
    public static final String PRODUCT = "PRD";
    public static final String PRODUCT_ID = "ID";
    public static final String PRODUCT_PRODUCT_ID = PRODUCT +"_"+PRODUCT_ID;
    public static final String NAME = "NAME";
    public static final String PRODUCT_PRODUCT_NAME = PRODUCT +"_"+ NAME;
    public static final String PRODUCT_UNIT_PRICE = PRODUCT +"_"+ "UNIT_PRICE";
    public static final String PRODUCT_CATEGORY_ID = PRODUCT +"_"+ "CAT_ID";
    public static final String PRODUCT_DISCONTINUED = PRODUCT + "_" + "DISCONTINUED";
    public static final String SUPPLIER_ID = "ID";
    public static final String SUPPLIER = "SUPPLIER";
    public static final String SUPPLIER_SUPPLIER_ID = SUPPLIER + "_" + SUPPLIER_ID;
    public static final String PRODUCT_SUPPLIER_ID = PRODUCT + "_" + SUPPLIER + "_"+ SUPPLIER_ID;
    public static final String SUPPLIER_SUPPLIER_NAME = SUPPLIER +"_"+ NAME;
    public static final String SUPPLIER_ADDRESS = SUPPLIER +"_"+ "ADDRESS";
    public static final String SUPPLIER_CONTACT = SUPPLIER +"_" + "CONTACT";
    
    public static final Column PRD_ID = Column.column(PRODUCT +"."+ PRODUCT_ID);
    public static final Column PRD_ID_ALIAS = Column.as(PRD_ID, PRODUCT_PRODUCT_ID);
    public static final Column SUPP_ID = Column.column("SUPPLIER" +"."+ SUPPLIER_ID);
    public static final Column SUPP_ID_ALIAS = Column.as(SUPP_ID, SUPPLIER_SUPPLIER_ID);
    
    public static final Column PRD_NAME_ALIAS = Column.as(tblProductName, PRODUCT_PRODUCT_NAME);
    public static final Column PRD_UNIT_PRICE_ALIAS = Column.as(tblProductUnitPrice, PRODUCT_UNIT_PRICE);
    public static final Column PRD_CAT_ID_ALIAS = Column.as(tblProductCategoryId, PRODUCT_CATEGORY_ID);
    public static final Column PRD_SUPP_ID_ALIAS = Column.as(tblProductSupplierId, PRODUCT_SUPPLIER_ID);
    public static final Column PRD_DISCONTINUED_ALIAS = Column.as(tblProductDiscontinued, PRODUCT_DISCONTINUED);
    
    public static final Column SUPP_NAME_ALIAS = Column.as(tblSupplierName, SUPPLIER_SUPPLIER_NAME);
    public static final Column SUPP_CONTACT_ALIAS = Column.as(tblContactName, SUPPLIER_CONTACT);
    public static final Column SUPP_ADDRESS_ALIAS = Column.as(tblSupplierAddr, SUPPLIER_ADDRESS);
    
    public static RowMapper<ProductSupplier> PRD_SUPP_ROW_MAPPER = (rs) -> {
        
        final ProductSupplier psRow = new ProductSupplier();
        /*ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int index = 1; index <= columnCount; index++) {
            String columnName = rsmd.getColumnLabel(index);
            String tableAlias = rsmd.getTableName(index);
            System.out.println(tableAlias + "." + columnName);
        }*/
        psRow.product = Product.emptyProduct();
        psRow.supplier = Supplier.emptySupplier();
        psRow.product.id = rs.getInt(PRODUCT_PRODUCT_ID);
        psRow.product.productName = rs.getString(PRODUCT_PRODUCT_NAME);
        psRow.product.supplierId = rs.getInt(PRODUCT_SUPPLIER_ID);
        psRow.product.pricePerUnit = rs.getDouble(PRODUCT_UNIT_PRICE);
        psRow.product.discontinued = rs.getBoolean(PRODUCT_DISCONTINUED);
        psRow.product.categoryId = rs.getInt(PRODUCT_CATEGORY_ID);
        psRow.supplier.id = rs.getInt(SUPPLIER_SUPPLIER_ID);
        psRow.supplier.supplierName = rs.getString(SUPPLIER_SUPPLIER_NAME);
        psRow.supplier.contactName = rs.getString(SUPPLIER_CONTACT);
        psRow.supplier.supplierAddress = rs.getString(SUPPLIER_ADDRESS);
        return psRow;
    };

    public Product getProduct() {
        return product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    @Override
    public String toString() {
        return String.format("%s %s", supplier, product);
    }
    
    
}

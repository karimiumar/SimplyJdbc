package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.RowMapper;
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

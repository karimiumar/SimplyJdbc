package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.RowMapper;
import static com.umar.simply.jdbc.fluent.dao.supplier.Product.TblProduct.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.Supplier.TblSupplier.*;

/**
 * Resembles Supplier supplying Products/ Or Products of suppliers
 *
 */
public class ProductSupplier {

    private Supplier supplier;
    private Product product;

    public static RowMapper<ProductSupplier> PRD_SUPP_ROW_MAPPER = (rs) -> {
        
        final ProductSupplier psRow = new ProductSupplier();
        /*ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int index = 1; index <= columnCount; index++) {
            String columnName = rsmd.getColumnLabel(index);
            String tableAlias = rsmd.getTableName(index);
            System.out.println(tableAlias + "." + columnName);
        }*/
        psRow.product = new Product("", 0, 0, false, 0);
        psRow.supplier = new Supplier("", "", "");
        psRow.product.id = rs.getInt(prd_id_rsmd.getColumnName());
        psRow.product.productName = rs.getString(prd_name_rsmd.getColumnName());
        psRow.product.supplierId = rs.getInt(prd_suppid_rsmd.getColumnName());
        psRow.product.pricePerUnit = rs.getDouble(prd_unitprice_rsmd.getColumnName());
        psRow.product.discontinued = rs.getBoolean(prd_discontinued_rsmd.getColumnName());
        psRow.product.categoryId = rs.getInt(prd_cat_id_rsmd.getColumnName());
        psRow.product.created = rs.getTimestamp(prd_created_rsmd.getColumnName()).toLocalDateTime();
        if (rs.getTimestamp(prd_updated_rsmd.getColumnName()) != null) {
            psRow.product.updated = rs.getTimestamp(prd_updated_rsmd.getColumnName()).toLocalDateTime();
        }
        psRow.supplier.id = rs.getInt(s_id_rsmd.getColumnName());
        psRow.supplier.supplierName = rs.getString(s_supplierName_rsmd.getColumnName());
        psRow.supplier.contactName = rs.getString(s_contactName_rsmd.getColumnName());
        psRow.supplier.supplierAddress = rs.getString(s_supplierAddr_rsmd.getColumnName());
        psRow.supplier.created = rs.getTimestamp(s_created_rsmd.getColumnName()).toLocalDateTime();
        if (rs.getTimestamp(s_updated_rsmd.getColumnName()) != null) {
            psRow.supplier.updated = rs.getTimestamp(s_updated_rsmd.getColumnName()).toLocalDateTime();
        }
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

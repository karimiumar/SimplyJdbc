package com.umar.simply.jdbc.fluent.dao.supplier.db.tables;

import com.umar.simply.jdbc.fluent.dao.supplier.Product;
import com.umar.simply.jdbc.fluent.dao.supplier.Supplier;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.SupplierTable.*;
import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.Table;
import java.time.LocalDateTime;

import static com.umar.simply.jdbc.meta.Column.column;
import com.umar.simply.jdbc.ResultSetMapper;
import com.umar.simply.jdbc.meta.ColumnValue;

/**
 * Represents database PRODUCT table
 *
 * @author umar
 */
public class ProductTable extends Table {
    
    public static Id productId() {
        return new Id("id");
    }
    
    public static Id setId(int val) {
        return new Id(val);
    }
    
    public static final class Id extends ColumnValue<Integer>{
        protected Id(String columnName) {
            super(column(columnName), 0);
        }
        
        protected Id(int val) {
            super(column("id"), val);
        }
    }

    public static final Column<Integer> PRODUCT_ID_COL = productId().getColumnName();
    public static final Column<String> PRODUCT_NAME_COL = column("product_name");
    public static final Column<Integer> PRODUCT_SUPPLIERID_COL = column("supplier_id");
    public static final Column<Double> PRODUCT_UNIT_PRICE_COL = column("unit_price");
    public static final Column<Boolean> PRODUCT_DISCONTINUED_COL = column("is_discontinued");
    public static final Column<Integer> PRODUCT_CAT_ID_COL = column("category_id");
    public static final Column<LocalDateTime> PRODUCT_CREATED_COL = column("created");
    public static final Column<LocalDateTime> PRODUCT_UPDATED_COL = column("updated");
    public static final Table TBL_PRODUCT = Table.table("product", PRODUCT_ID_COL);

    public static final ResultSetMapper<Product> PRODUCT_ROW_MAPPER = (rs) -> {
        final Product rowProduct = Product.emptyProduct();
        rowProduct.setId(rs.getInt(PRODUCT_ID_COL.getColumnName()));
        rowProduct.setProductName(rs.getString(PRODUCT_NAME_COL.getColumnName()));
        rowProduct.setSupplierId(rs.getInt(PRODUCT_SUPPLIERID_COL.getColumnName()));
        rowProduct.setPricePerUnit(rs.getDouble(PRODUCT_UNIT_PRICE_COL.getColumnName()));
        rowProduct.setDiscontinued(rs.getBoolean(PRODUCT_DISCONTINUED_COL.getColumnName()));
        rowProduct.setCategoryId(rs.getInt(PRODUCT_CAT_ID_COL.getColumnName()));
        rowProduct.setCreated(rs.getTimestamp(PRODUCT_CREATED_COL.getColumnName()).toLocalDateTime());
        if (rs.getTimestamp(PRODUCT_UPDATED_COL.getColumnName()) != null) {
            rowProduct.setUpdated(rs.getTimestamp(PRODUCT_UPDATED_COL.getColumnName()).toLocalDateTime());
        }
        return rowProduct;
    };
    
    public static final String PRODUCT = "PRD";
    public static final String ID = "ID";
    public static final String PRODUCT_PRODUCT_ID = PRODUCT +"_"+PRODUCT_ID_COL;
    public static final String NAME = "NAME";
    public static final String PRD_PRODUCT_NAME = PRODUCT +"_"+ NAME;
    public static final String PRD_UNIT_PRICE = PRODUCT +"_"+ "UNIT_PRICE";
    public static final String PRD_CATEGORY_ID = PRODUCT +"_"+ "CAT_ID";
    public static final String PRD_DISCONTINUED = PRODUCT + "_" + "DISCONTINUED";
    public static final String SUPPLIER_ID = "ID";
    public static final String SUPPLIER = "SUPPLIER";
    public static final String SUPPLIER_SUPPLIER_ID = SUPPLIER + "_" + SUPPLIER_ID;
    public static final String PRD_SUPPLIER_ID = PRODUCT + "_" + SUPPLIER + "_"+ SUPPLIER_ID;
    public static final String SUPPLIER_SUPPLIER_NAME = SUPPLIER +"_"+ NAME;
    public static final String SUPPLIER_ADDRESS = SUPPLIER +"_"+ "ADDRESS";
    public static final String SUPPLIER_CONTACT = SUPPLIER +"_" + "CONTACT";
    
    public static final Column PRD_ID = Column.column(PRODUCT +"."+ PRODUCT_ID_COL);
    public static final Column PRD_ID_ALIAS = Column.as(PRD_ID, PRODUCT_PRODUCT_ID);
    public static final Column SUPP_ID = Column.column("SUPPLIER" +"."+ SUPPLIER_ID);
    public static final Column SUPP_ID_ALIAS = Column.as(SUPP_ID, SUPPLIER_SUPPLIER_ID);
    
    public static final Column PRD_NAME_ALIAS = Column.as(PRODUCT_NAME_COL, PRD_PRODUCT_NAME);
    public static final Column PRD_UNIT_PRICE_ALIAS = Column.as(PRODUCT_UNIT_PRICE_COL, PRD_UNIT_PRICE);
    public static final Column PRD_CAT_ID_ALIAS = Column.as(PRODUCT_CAT_ID_COL, PRD_CATEGORY_ID);
    public static final Column PRD_SUPP_ID_ALIAS = Column.as(PRODUCT_SUPPLIERID_COL, PRD_SUPPLIER_ID);
    public static final Column PRD_DISCONTINUED_ALIAS = Column.as(PRODUCT_DISCONTINUED_COL, PRD_DISCONTINUED);
    
    public static final Column SUPP_NAME_ALIAS = Column.as(SUPPLIER_NAME_COL, SUPPLIER_SUPPLIER_NAME);//get this from SupplierTable
    public static final Column SUPP_CONTACT_ALIAS = Column.as(SUPPLIER_CONTACT_COL, SUPPLIER_CONTACT);
    public static final Column SUPP_ADDRESS_ALIAS = Column.as(SUPPLIER_ADDR_COL, SUPPLIER_ADDRESS);
    
    static public class ProductSupplier{
        Product product;
        Supplier supplier;

        public Product getProduct() {
            return product;
        }

        public Supplier getSupplier() {
            return supplier;
        }
    }
    
    public static final ResultSetMapper<ProductSupplier> PRD_SUPP_ROW_MAPPER = (rs) -> {
        
        final ProductSupplier productSupplierRecord = new ProductSupplier();
        /*ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int index = 1; index <= columnCount; index++) {
            String columnName = rsmd.getColumnLabel(index);
            String tableAlias = rsmd.getTableName(index);
            System.out.println(tableAlias + "." + columnName);
        }*/
        productSupplierRecord.product = Product.emptyProduct();
        productSupplierRecord.supplier = Supplier.emptySupplier();
        productSupplierRecord.product.setId(rs.getInt(PRODUCT_PRODUCT_ID));
        productSupplierRecord.product.setProductName(rs.getString(PRD_PRODUCT_NAME));
        productSupplierRecord.product.setSupplierId(rs.getInt(PRD_SUPPLIER_ID));
        productSupplierRecord.product.setPricePerUnit(rs.getDouble(PRD_UNIT_PRICE));
        productSupplierRecord.product.setDiscontinued(rs.getBoolean(PRD_DISCONTINUED));
        productSupplierRecord.product.setCategoryId(rs.getInt(PRD_CATEGORY_ID));
        productSupplierRecord.supplier.setId(rs.getInt(SUPPLIER_SUPPLIER_ID));
        productSupplierRecord.supplier.setSupplierName(rs.getString(SUPPLIER_SUPPLIER_NAME));
        productSupplierRecord.supplier.setContactName(rs.getString(SUPPLIER_CONTACT));
        productSupplierRecord.supplier.setSupplierAddress(rs.getString(SUPPLIER_ADDRESS));
        return productSupplierRecord;
    };
    
    

    public ProductTable(String tableName, Column<Integer> idColumn) {
        super(tableName, idColumn);
    }
    
    /*public static final ResultSetMapper<Supplier> SUPPLIER_ROW_MAPPER = (rs) -> {
            final Supplier rowSupplier = Supplier.emptySupplier();
            rowSupplier.setId(rs.getInt(SUPPLIER_ID_COL.getColumnName()));
            rowSupplier.setSupplierName(rs.getString(SUPPLIER_NAME_COL.getColumnName()));
            rowSupplier.setContactName(rs.getString(SUPPLIER_CONTACT_COL.getColumnName()));
            rowSupplier.setSupplierAddress(rs.getString(SUPPLIER_ADDR_COL.getColumnName()));
            rowSupplier.setCreated(rs.getTimestamp(SUPPLIER_CREATED_COL.getColumnName()).toLocalDateTime());
            if(rs.getTimestamp(SUPPLIER_UPDATED_COL.getColumnName()) != null) {
                rowSupplier.setUpdated(rs.getTimestamp(SUPPLIER_UPDATED_COL.getColumnName()).toLocalDateTime());
            }
            return rowSupplier;
        };*/
}

package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.RowMapper;
import static com.umar.simply.jdbc.fluent.dao.supplier.Supplier.TblSupplier.*;
import com.umar.simply.jdbc.meta.Column;
import com.umar.simply.jdbc.meta.Table;
import java.time.LocalDateTime;

import static com.umar.simply.jdbc.meta.Column.as;
import static com.umar.simply.jdbc.meta.Column.column;

/**
 * Represents database PRODUCT table
 *
 * @author umar
 */
public class ProductTable extends Table {

    public static final Column<Integer> PRODUCT_ID_COL = column("id");
    public static final Column<String> PRODUCT_NAME_COL = column("product_name");
    public static final Column<Integer> PRODUCT_SUPPLIERID_COL = column("supplier_id");
    public static final Column<Double> PRODUCT_UNIT_PRICE_COL = column("unit_price");
    public static final Column<Boolean> PRODUCT_DISCONTINUED_COL = column("is_discontinued");
    public static final Column<Integer> PRODUCT_CAT_ID_COL = column("category_id");
    public static final Column<LocalDateTime> PRODUCT_CREATED_COL = column("created");
    public static final Column<LocalDateTime> PRODUCT_UPDATED_COL = column("updated");
    public static final Table TBL_PRODUCT = Table.table("ex.product", PRODUCT_ID_COL);

    /*
        This is additionally need as the ResultSetMtaData removes all aliases from the
        ResultSet. Hence, mapping fails. The Mapping that is now needed takes the form
        of PRODUCT.ID, PRODUCT.PRODUCT_NAME, PRODUCT.UNIT_PRICE, PRODUCT.CATEGORY_ID, PRODUCT.SUPPLIER_ID
     */
    /**
     * If the returning SQL ResultSet consist of joins of two or more tables
     * then the given Mapping should be used by the RowMapper.map(ResultSet) as
     * the ResultSetMetaData only has information about actual table column
     * names and all the aliases created to are lost.
     */
    /*public static final Table prd_rsmd = Table.table("product", PRODUCT_ID);
    public static final Column<Integer> prd_id_rsmd = as(prd_rsmd.getTableName(), "id");
    public static final Column<String> prd_name_rsmd = as(prd_rsmd.getTableName(), "product_name");
    public static final Column<Integer> prd_suppid_rsmd = as(prd_rsmd.getTableName(), "supplier_id");
    public static final Column<Double> prd_unitprice_rsmd = as(prd_rsmd.getTableName(), "unit_price");
    public static final Column<Boolean> prd_discontinued_rsmd = as(prd_rsmd.getTableName(), "is_discontinued");
    public static final Column<Integer> prd_cat_id_rsmd = as(prd_rsmd.getTableName(), "category_id");
    public static final Column<LocalDateTime> prd_created_rsmd = as(prd_rsmd.getTableName(), "created");
    public static final Column<LocalDateTime> prd_updated_rsmd = as(prd_rsmd.getTableName(), "updated");*/

    public static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs) -> {
        final Product rowProduct = Product.emptyProduct();
        rowProduct.id = rs.getInt(PRODUCT_ID_COL.getColumnName());
        rowProduct.productName = rs.getString(PRODUCT_NAME_COL.getColumnName());
        rowProduct.supplierId = rs.getInt(PRODUCT_SUPPLIERID_COL.getColumnName());
        rowProduct.pricePerUnit = rs.getDouble(PRODUCT_UNIT_PRICE_COL.getColumnName());
        rowProduct.discontinued = rs.getBoolean(PRODUCT_DISCONTINUED_COL.getColumnName());
        rowProduct.categoryId = rs.getInt(PRODUCT_CAT_ID_COL.getColumnName());
        rowProduct.created = rs.getTimestamp(PRODUCT_CREATED_COL.getColumnName()).toLocalDateTime();
        if (rs.getTimestamp(PRODUCT_UPDATED_COL.getColumnName()) != null) {
            rowProduct.updated = rs.getTimestamp(PRODUCT_UPDATED_COL.getColumnName()).toLocalDateTime();
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
    }
    
    public static final RowMapper<ProductSupplier> PRD_SUPP_ROW_MAPPER = (rs) -> {
        
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
        productSupplierRecord.product.id = rs.getInt(PRODUCT_PRODUCT_ID);
        productSupplierRecord.product.productName = rs.getString(PRD_PRODUCT_NAME);
        productSupplierRecord.product.supplierId = rs.getInt(PRD_SUPPLIER_ID);
        productSupplierRecord.product.pricePerUnit = rs.getDouble(PRD_UNIT_PRICE);
        productSupplierRecord.product.discontinued = rs.getBoolean(PRD_DISCONTINUED);
        productSupplierRecord.product.categoryId = rs.getInt(PRD_CATEGORY_ID);
        productSupplierRecord.supplier.id = rs.getInt(SUPPLIER_SUPPLIER_ID);
        productSupplierRecord.supplier.supplierName = rs.getString(SUPPLIER_SUPPLIER_NAME);
        productSupplierRecord.supplier.contactName = rs.getString(SUPPLIER_CONTACT);
        productSupplierRecord.supplier.supplierAddress = rs.getString(SUPPLIER_ADDRESS);
        return productSupplierRecord;
    };
    
    

    public ProductTable(String tableName, Column<Integer> idColumn) {
        super(tableName, idColumn);
    }
    
    public static final RowMapper<Supplier> SUPPLIER_ROW_MAPPER = (rs) -> {
            final Supplier rowSupplier = Supplier.emptySupplier();
            rowSupplier.id = rs.getInt(SUPPLIER_ID_COL.getColumnName());
            rowSupplier.supplierName = rs.getString(SUPPLIER_NAME_COL.getColumnName());
            rowSupplier.contactName = rs.getString(SUPPLIER_CONTACT_COL.getColumnName());
            rowSupplier.supplierAddress = rs.getString(SUPPLIER_ADDR_COL.getColumnName());
            rowSupplier.created = rs.getTimestamp(SUPPLIER_CREATED_COL.getColumnName()).toLocalDateTime();
            if(rs.getTimestamp(SUPPLIER_UPDATED_COL.getColumnName()) != null) {
                rowSupplier.updated = rs.getTimestamp(SUPPLIER_UPDATED_COL.getColumnName()).toLocalDateTime();
            }
            return rowSupplier;
        };
}

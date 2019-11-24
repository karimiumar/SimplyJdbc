package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.RowMapper;
import com.umar.simply.jdbc.meta.Column;
import static com.umar.simply.jdbc.meta.Column.as;
import static com.umar.simply.jdbc.meta.Column.column;
import com.umar.simply.jdbc.meta.Table;
import java.time.LocalDateTime;
import java.util.Objects;

public class Product {

    int id;
    String productName;
    int supplierId;
    double pricePerUnit;
    boolean discontinued;
    int categoryId;
    LocalDateTime created;
    LocalDateTime updated;
    
    private Product() {
        
    }

    public Product(String productName, int supplierId, double pricePerUnit, boolean discontinued, int categoryId) {
        this.productName = productName;
        this.supplierId = supplierId;
        this.pricePerUnit = pricePerUnit;
        this.discontinued = discontinued;
        this.categoryId = categoryId;
    }
    
    public interface TblProduct {

        Column<Integer> productId = column("id");
        Column<String> prd_name = column("product_name");
        Column<Integer> productSupplierId = column("supplier_id");
        Column<Double> productUnitPrice = column("unit_price");
        Column<Boolean> discontinued = column("is_discontinued");
        Column<Integer> productCategoryId = column("category_id");
        Column<LocalDateTime> productCreatedOn = column("created");
        Column<LocalDateTime> productUpdatedOn = column("updated");
        Table product = Table.table("ex.product", productId);

        Column<Integer> prd1_id = as("id", "prd1");
        Column<String> prd1_productname = as("product_name", "prd1");
        Column<Integer> prd1_suppid = as("supplier_id", "prd1");
        Column<Double> prd1_unitprice = as("unit_price", "prd1");
        Column<Boolean> prd1_discontinued = as("is_discontinued", "prd1");
        Column<Integer> prd1_cat_id = as("category_id", "prd1");
        Column<LocalDateTime> prd1_created = as("created", "prd1");
        Column<LocalDateTime> prd1_updated = as("updated", "prd1");
        Table prd1 = Table.as("ex.product", "prd1", productId);
        
        /*
        This is additionally need as the ResultSetMtaData removes all aliases from the
        ResultSet. Hence, mapping fails. The Mapping that is now needed takes the form
        of PRODUCT.ID, PRODUCT.PRODUCT_NAME, PRODUCT.UNIT_PRICE, PRODUCT.CATEGORY_ID, PRODUCT.SUPPLIER_ID
        */
        /**
         * If the returning SQL ResultSet consist of joins of two or more
         * tables then the given Mapping should be used by the RowMapper.map(ResultSet) as the ResultSetMetaData
         * only has information about actual table column names and all the aliases
         * created to are lost. 
         */
        Table prd_rsmd = Table.table("product", productId);
        Column<Integer> prd_id_rsmd = as("id", prd_rsmd.getTableName());
        Column<String> prd_name_rsmd = as("product_name", prd_rsmd.getTableName());
        Column<Integer> prd_suppid_rsmd = as("supplier_id", prd_rsmd.getTableName());
        Column<Double> prd_unitprice_rsmd = as("unit_price", prd_rsmd.getTableName());
        Column<Boolean> prd_discontinued_rsmd = as("is_discontinued", prd_rsmd.getTableName());
        Column<Integer> prd_cat_id_rsmd = as("category_id", prd_rsmd.getTableName());
        Column<LocalDateTime> prd_created_rsmd = as("created", prd_rsmd.getTableName());
        Column<LocalDateTime> prd_updated_rsmd = as("updated", prd_rsmd.getTableName());
       
        Column<Integer> prd2_id = as("id", "prd2");
        Column<String> prd2_productname = as("product_name", "prd2");
        Column<Integer> prd2_suppid = as("supplier_id", "prd2");
        Column<Double> prd2_unitprice = as("unit_price", "prd2");
        Column<Boolean> prd2_discontinued = as("is_discontinued", "prd2");
        Column<Integer> prd2_cat_id = as("category_id", "prd2");
        Column<LocalDateTime> prd2_created = as("created", "prd2");
        Column<LocalDateTime> prd2_updated = as("updated", "prd2");
        Table prd2 = Table.as("ex.product", "prd2", productId);
        
        RowMapper<Product> PRODUCT_ROW_MAPPER = (rs) -> {
            final Product rowProduct = new Product();
            rowProduct.id = rs.getInt(productId.getColumnName());
            rowProduct.productName = rs.getString(prd_name.getColumnName());
            rowProduct.supplierId = rs.getInt(productSupplierId.getColumnName());
            rowProduct.pricePerUnit = rs.getDouble(productUnitPrice.getColumnName());
            rowProduct.discontinued = rs.getBoolean(discontinued.getColumnName());
            rowProduct.categoryId = rs.getInt(productCategoryId.getColumnName());
            rowProduct.created = rs.getTimestamp(productCreatedOn.getColumnName()).toLocalDateTime();
            if(rs.getTimestamp(productUpdatedOn.getColumnName()) != null) {
                rowProduct.updated = rs.getTimestamp(productUpdatedOn.getColumnName()).toLocalDateTime();
            }
            return rowProduct;
        };
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.productName);
        hash = 71 * hash + this.supplierId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.supplierId != other.supplierId) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", productName=" + productName + ", supplierId=" + supplierId + ", pricePerUnit=" + pricePerUnit + ", discontinued=" + discontinued + ", categoryId=" + categoryId + ", created=" + created + ", updated=" + updated + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}

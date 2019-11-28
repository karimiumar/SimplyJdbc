package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.fluent.dao.QueryService;

import static com.umar.simply.jdbc.fluent.dao.supplier.Supplier.TblSupplier.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.Product.TblProduct.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.ProductSupplier.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;

import java.sql.Connection;
import java.util.List;
import static java.util.Arrays.asList;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentProductSupplierQueryService;
import com.umar.simply.jdbc.meta.Column;

public class ProductSupplierQueryService extends QueryService implements FluentProductSupplierQueryService {
    
    public ProductSupplierQueryService(Connection connection) {
        super(connection);
    }

    @Override
    public List<Product> listProductsSuppliedBy(Supplier supplier) {
        List<Product> productsSupplied = select().all()
                .from(tblProduct).where()
                .column(tblProductSupplierId).eq(set(supplier.id))
                .using(PRODUCT_ROW_MAPPER)
                .execute();
        return productsSupplied;
    }

    @Override
    public List<ProductSupplier> listAllProductsOfSuppliers_Order_By_Supplier() {
        Column PRD_ID = Column.as(Column.column(PRODUCT +"."  + PRODUCT_ID), PRODUCT_PRODUCT_ID);
        Column SUPP_ID = Column.as(Column.column("SUPPLIER" +"."  + SUPPLIER_ID), SUPPLIER_SUPPLIER_ID);
        List<ProductSupplier> productSuppliers = 
                select(asList(
                        PRD_ID
                        ,Column.as(tblProductName, PRODUCT_PRODUCT_NAME)
                        ,Column.as(tblProductUnitPrice, PRODUCT_UNIT_PRICE)
                        ,Column.as(tblProductCategoryId, PRODUCT_CATEGORY_ID)
                        ,Column.as(tblProductSupplierId, PRODUCT_SUPPLIER_ID)
                        ,Column.as(tblProductDiscontinued, PRODUCT_DISCONTINUED)
                        ,SUPP_ID
                        ,Column.as(tblSupplierName, SUPPLIER_SUPPLIER_NAME)
                        ,Column.as(tblContactName, SUPPLIER_CONTACT)
                        ,Column.as(tblSupplierAddr, SUPPLIER_ADDRESS)))
                .from(tblProduct).as(PRODUCT)
                .join(tblSupplier).as(SUPPLIER)
                .on().column(Column.column(SUPPLIER+"."+SUPPLIER_ID)).eq(tblProductSupplierId)
                .groupBy(asList(Column.column(SUPPLIER+"."+SUPPLIER_ID), Column.column(PRODUCT +"."  + PRODUCT_ID)))
                .using(PRD_SUPP_ROW_MAPPER)
                .execute();
        return productSuppliers;
    }

    @Override
    public List<Supplier> listSuppliersOfProduct(Product queryProduct) {
        List<Supplier> suppliersOf = select().all()
                .from(tblSupplier)
                .join().table(tblProduct)
                .on().column(tblSupplierId).eq(set(queryProduct.supplierId))
                .using(SUPPLIER_ROW_MAPPER)
                .execute();
        return suppliersOf;
    }    
}

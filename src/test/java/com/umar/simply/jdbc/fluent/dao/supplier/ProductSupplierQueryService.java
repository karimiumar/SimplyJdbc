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

public class ProductSupplierQueryService extends QueryService implements FluentProductSupplierQueryService {
    
    public ProductSupplierQueryService(Connection connection) {
        super(connection);
    }

    @Override
    public List<Product> listProductsSuppliedBy(Supplier supplier) {
        List<Product> productsSupplied = select().all()
                .from(product).where()
                .column(productSupplierId).eq(set(supplier.id))
                .using(PRODUCT_ROW_MAPPER)
                .execute();
        return productsSupplied;
    }

    @Override
    public List<ProductSupplier> listAllProductsOfSuppliers_Order_By_Supplier() {
        List<ProductSupplier> productSuppliers = 
                select().column(asList(prd1_id, prd1_productname, prd1_unitprice, prd1_cat_id,prd1_suppid, prd1_created, prd1_updated, prd1_discontinued,
                        s1_id, s1_supplierName, s1_contactName, s1_created,s1_updated, s1_supplierAddr))
                .from(product).as("prd1")
                .join(supplier).as("s1")
                .on().column(s1_id).eq(prd1_suppid)
                .groupBy(asList(s1_id, prd1_id))
                .using(PRD_SUPP_ROW_MAPPER)
                .execute();
        return productSuppliers;
    }

    @Override
    public List<Supplier> listSuppliersOfProduct(Product queryProduct) {
        List<Supplier> suppliersOf = select().all()
                .from(supplier)
                .join().table(product)
                .on().column(supplierId).eq(set(queryProduct.supplierId))
                .using(SUPPLIER_ROW_MAPPER)
                .execute();
        return suppliersOf;
    }    
}

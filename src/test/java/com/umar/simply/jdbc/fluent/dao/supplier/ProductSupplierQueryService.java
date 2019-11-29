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
                .from(tblProduct).where()
                .column(tblProductSupplierId).eq(set(supplier.id))
                .using(PRODUCT_ROW_MAPPER)
                .execute();
        return productsSupplied;
    }

    @Override
    public List<ProductSupplier> listAllProductsOfSuppliers_Order_By_Supplier() {
        List<ProductSupplier> productSuppliers = 
                select(asList(
                        PRD_ID_ALIAS
                        ,PRD_NAME_ALIAS
                        ,PRD_UNIT_PRICE_ALIAS
                        ,PRD_CAT_ID_ALIAS
                        ,PRD_SUPP_ID_ALIAS
                        ,PRD_DISCONTINUED_ALIAS
                        ,SUPP_ID_ALIAS
                        ,SUPP_NAME_ALIAS
                        ,SUPP_CONTACT_ALIAS
                        ,SUPP_ADDRESS_ALIAS))
                .from(tblProduct).as(PRODUCT)
                .join(tblSupplier).as(SUPPLIER)
                .on(SUPP_ID).eq(tblProductSupplierId)
                .groupBy(asList(SUPP_ID, PRD_ID))
                .using(PRD_SUPP_ROW_MAPPER)
                .execute();
        return productSuppliers;
    }

    @Override
    public List<Supplier> listSuppliersOfProduct(Product queryProduct) {
        List<Supplier> suppliersOf = select().all()
                .from(tblSupplier)
                .join().table(tblProduct)
                .on(tblSupplierId).eq(set(queryProduct.supplierId))
                .using(SUPPLIER_ROW_MAPPER)
                .execute();
        return suppliersOf;
    }    
}

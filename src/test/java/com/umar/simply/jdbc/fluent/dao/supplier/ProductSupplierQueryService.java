package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.fluent.dao.supplier.db.tables.ProductTable;
import com.umar.simply.jdbc.fluent.dao.QueryService;

import java.sql.Connection;
import java.util.*;

import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentProductSupplierQueryService;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.SupplierTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.ProductTable.*;
import com.umar.simply.jdbc.fluent.dao.supplier.db.tables.SupplierTable;
import static com.umar.simply.jdbc.meta.ColumnValue.*;
import static java.util.Arrays.asList;

public class ProductSupplierQueryService extends QueryService implements FluentProductSupplierQueryService {
    
    public ProductSupplierQueryService(Connection connection) {
        super(connection);
    }

    @Override
    public List<Product> listProductsSuppliedBy(Supplier supplier) {
        return SELECT().ALL()
                .FROM(TBL_PRODUCT).WHERE()
                .COLUMN(PRODUCT_SUPPLIERID_COL).EQ(set(supplier.id))
                .using(PRODUCT_ROW_MAPPER)
                .execute();
    }

    @Override
    public List<Supplier> listSuppliersOfProduct(Product queryProduct) {
        return SELECT().ALL()
                .FROM(TBL_SUPPLIER)
                .JOIN().TABLE(TBL_PRODUCT)
                .ON(SUPP_ID).EQ(set(queryProduct.supplierId))
                .using(SUPPLIER_ROW_MAPPER)
                .execute();
    }    

    @Override
    public Map<Supplier, List<Product>> listProductsSupplierwise() {
        List<ProductTable.ProductSupplier> productSuppliers = listAllProductsOfSuppliers_Order_By_Supplier();
        SortedMap<Supplier, List<Product>> productsSupplierwiseMap = new TreeMap<>(Comparator.comparingInt((Supplier o) -> o.id));
        Set<Supplier> suppliers = new HashSet<>();
        for (ProductSupplier ps : productSuppliers) {
            Supplier supplier = ps.getSupplier();
            suppliers.add(supplier);
        }
        for (ProductSupplier ps : productSuppliers) {
            Product product = ps.getProduct();
            suppliers.forEach((supplier) -> {
                if (supplier.id == product.supplierId) {
                    supplier.add(product);
                }
            });
        }
        
        suppliers.forEach((supplier) -> {
            productsSupplierwiseMap.put(supplier, supplier.getSupplierOfProducts());
        });
        return productsSupplierwiseMap;
    }
    
    private List<ProductTable.ProductSupplier> listAllProductsOfSuppliers_Order_By_Supplier() {
        return SELECT(asList(
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
        .FROM(TBL_PRODUCT).AS(PRODUCT)
        .JOIN(TBL_SUPPLIER).AS(SUPPLIER)
        .ON(SUPP_ID).EQ(PRODUCT_SUPPLIERID_COL)
        .GROUP_BY(asList(SUPP_ID, PRD_ID))
        .ORDER_BY(SUPP_ID)
        .using(PRD_SUPP_ROW_MAPPER)
        .execute();
    }

    @Override
    public Optional<Supplier> findById(SupplierTable.Id id) {
        return findById(TBL_SUPPLIER, SUPPLIER_ROW_MAPPER, id);
    }

    @Override
    public Optional<Product> findById(ProductTable.Id id) {
        return findById(TBL_PRODUCT, PRODUCT_ROW_MAPPER, id);
    }
}

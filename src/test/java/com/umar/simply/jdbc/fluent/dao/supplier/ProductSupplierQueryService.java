package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.fluent.dao.supplier.db.tables.ProductTable;
import com.umar.simply.jdbc.fluent.dao.QueryService;

import java.sql.Connection;
import java.util.List;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentProductSupplierQueryService;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.SupplierTable.*;
import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.ProductTable.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static java.util.Arrays.asList;

public class ProductSupplierQueryService extends QueryService implements FluentProductSupplierQueryService {
    
    public ProductSupplierQueryService(Connection connection) {
        super(connection);
    }

    @Override
    public List<Product> listProductsSuppliedBy(Supplier supplier) {
        List<Product> productsSupplied = select().all()
                .from(TBL_PRODUCT).where()
                .column(PRODUCT_SUPPLIERID_COL).eq(set(supplier.id))
                .using(PRODUCT_ROW_MAPPER)
                .execute();
        return productsSupplied;
    }

    @Override
    public List<Supplier> listSuppliersOfProduct(Product queryProduct) {
        List<Supplier> suppliersOf = select().all()
                .from(TBL_SUPPLIER)
                .join().table(TBL_PRODUCT)
                .on(SUPP_ID).eq(set(queryProduct.supplierId))
                .using(SUPPLIER_ROW_MAPPER)
                .execute();
        return suppliersOf;
    }    

    @Override
    public Map<Supplier, List<Product>> listProductsSupplierwise() {
        List<ProductTable.ProductSupplier> productSuppliers = listAllProductsOfSuppliers_Order_By_Supplier();
        SortedMap<Supplier, List<Product>> productsSupplierwiseMap = new TreeMap<>((Supplier o1, Supplier o2) -> o1.id < o2.id ? -1 : (o1.id == o2.id) ? 0 :1);
        Set<Supplier> suppliers = new HashSet<>();
        for(int i=0; i<productSuppliers.size(); i++) {
            ProductTable.ProductSupplier ps = productSuppliers.get(i);
            Supplier supplier = ps.getSupplier();
            suppliers.add(supplier);
        }
        for(int i=0; i<productSuppliers.size(); i++) {
            ProductTable.ProductSupplier ps = productSuppliers.get(i);
            Product product = ps.getProduct();
            suppliers.forEach((supplier) ->  {
                if(supplier.id == product.supplierId) {
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
        List<ProductTable.ProductSupplier> productSuppliers = 
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
                .from(TBL_PRODUCT).as(PRODUCT)
                .join(TBL_SUPPLIER).as(SUPPLIER)
                .on(SUPP_ID).eq(PRODUCT_SUPPLIERID_COL)
                .groupBy(asList(SUPP_ID, PRD_ID))
                .orderBy(SUPP_ID)
                .using(PRD_SUPP_ROW_MAPPER)
                .execute();
        return productSuppliers;
    }
}

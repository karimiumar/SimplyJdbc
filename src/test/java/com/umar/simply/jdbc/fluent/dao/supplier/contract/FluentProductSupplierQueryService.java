package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.fluent.dao.supplier.Product;
import com.umar.simply.jdbc.fluent.dao.supplier.Supplier;
import com.umar.simply.jdbc.fluent.dao.supplier.db.tables.ProductTable;
import com.umar.simply.jdbc.fluent.dao.supplier.db.tables.SupplierTable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FluentProductSupplierQueryService {
    List<Product> listProductsSuppliedBy(Supplier supplier);
    Map<Supplier, List<Product>> listProductsSupplierwise();
    List<Supplier> listSuppliersOfProduct(Product product);
    Optional<Supplier> findById(SupplierTable.Id id);
    Optional<Product> findById(ProductTable.Id id);
}

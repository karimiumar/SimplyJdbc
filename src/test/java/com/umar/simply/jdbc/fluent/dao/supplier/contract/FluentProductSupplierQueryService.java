package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.fluent.dao.supplier.Product;
import com.umar.simply.jdbc.fluent.dao.supplier.Supplier;
import java.util.List;
import java.util.Map;

public interface FluentProductSupplierQueryService {
    List<Product> listProductsSuppliedBy(Supplier supplier);
    Map<Supplier, List<Product>> listProductsSupplierwise();
    List<Supplier> listSuppliersOfProduct(Product product);
}

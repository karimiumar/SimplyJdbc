package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.fluent.dao.supplier.Product;
import com.umar.simply.jdbc.fluent.dao.supplier.ProductSupplier;
import com.umar.simply.jdbc.fluent.dao.supplier.Supplier;
import java.util.List;

public interface FluentProductSupplierQueryService {
    List<Product> listProductsSuppliedBy(Supplier supplier);
    List<ProductSupplier> listAllProductsOfSuppliers_Order_By_Supplier();
    List<Supplier> listSuppliersOfProduct(Product product);
}

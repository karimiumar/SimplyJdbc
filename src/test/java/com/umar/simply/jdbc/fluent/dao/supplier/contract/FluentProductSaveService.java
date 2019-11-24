package com.umar.simply.jdbc.fluent.dao.supplier.contract;

import com.umar.simply.jdbc.fluent.dao.supplier.ProductSaveService;
import com.umar.simply.jdbc.fluent.dao.supplier.Product;

public interface FluentProductSaveService {
    ProductSaveService save(Product product);
    Product execute();
}

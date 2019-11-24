package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.SavePersistenceService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentProductSaveService;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.supplier.Product.TblProduct.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import java.time.LocalDateTime;
import static java.util.Arrays.asList;

public class ProductSaveService implements FluentProductSaveService {

    private List<ColumnValue> newValues;
    
    @Override
    public ProductSaveService save(Product product) {
        newValues = asList(
                set(prd_name, product.productName)
                ,set(productSupplierId, product.supplierId)
                ,set(productUnitPrice, product.pricePerUnit)
                ,set(discontinued, product.discontinued)
                ,set(productCategoryId, product.categoryId)
                ,set(productCreatedOn, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Product execute() {
        SavePersistenceService<Product> sps = new SavePersistenceService<>(JdbcUtilService.getConnection());
        Product saved = sps.save(product).withValues(newValues).using(PRODUCT_ROW_MAPPER).execute();
        return saved;
    }
}

package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.SavePersistenceService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentProductSaveService;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.List;
import java.time.LocalDateTime;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.ProductTable.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static java.util.Arrays.asList;

public class ProductSaveService implements FluentProductSaveService {

    private List<ColumnValue<?>> newValues;
    
    @Override
    public ProductSaveService save(Product product) {
        newValues = asList(
                set(PRODUCT_NAME_COL, product.productName)
                ,set(PRODUCT_SUPPLIERID_COL, product.supplierId)
                ,set(PRODUCT_UNIT_PRICE_COL, product.pricePerUnit)
                ,set(PRODUCT_DISCONTINUED_COL, product.discontinued)
                ,set(PRODUCT_CAT_ID_COL, product.categoryId)
                ,set(PRODUCT_CREATED_COL, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Product execute() {
        SavePersistenceService<Product> sps = new SavePersistenceService<>(JdbcUtilService.getConnection());
        Product saved = sps.save(TBL_PRODUCT).withValues(newValues).using(PRODUCT_ROW_MAPPER).execute();
        return saved;
    }
}

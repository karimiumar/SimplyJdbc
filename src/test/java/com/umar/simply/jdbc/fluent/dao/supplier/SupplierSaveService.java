package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.SavePersistenceService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentSupplierSaveService;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.time.LocalDateTime;
import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.SupplierTable.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static java.util.Arrays.asList;

public class SupplierSaveService implements FluentSupplierSaveService {

    private List<ColumnValue<?>> newValues;
    
    @Override
    public SupplierSaveService save(Supplier supplier) {
        
        newValues = asList(
                set(SUPPLIER_NAME_COL, supplier.supplierName)
                ,set(SUPPLIER_CONTACT_COL, supplier.contactName)
                ,set(SUPPLIER_ADDR_COL, supplier.supplierAddress)
                ,set(SUPPLIER_CREATED_COL, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Supplier execute() {
       SavePersistenceService<Supplier> sps = new SavePersistenceService<>(JdbcUtilService.getConnection());
        return sps.save(TBL_SUPPLIER).withValues(newValues).using(SUPPLIER_ROW_MAPPER).execute();
    }
    
}

package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.SavePersistenceService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentSupplierSaveService;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.time.LocalDateTime;
import java.util.List;

import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static com.umar.simply.jdbc.fluent.dao.supplier.Supplier.TblSupplier.*;
import static java.util.Arrays.asList;

public class SupplierSaveService implements FluentSupplierSaveService {

    private List<ColumnValue> newValues;
    
    @Override
    public SupplierSaveService save(Supplier supplier) {
        
        newValues = asList(
                set(supplierName, supplier.supplierName)
                ,set(contactName, supplier.contactName)
                ,set(supplierAddr, supplier.supplierAddress)
                ,set(supplierCreatedOn, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Supplier execute() {
       SavePersistenceService<Supplier> sps = new SavePersistenceService<>(JdbcUtilService.getConnection());
       Supplier saved = sps.save(supplier).withValues(newValues).using(SUPPLIER_ROW_MAPPER).execute();
       return saved;
    }
    
}

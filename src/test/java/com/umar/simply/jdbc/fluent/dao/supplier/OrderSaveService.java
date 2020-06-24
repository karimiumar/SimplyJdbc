package com.umar.simply.jdbc.fluent.dao.supplier;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.SavePersistenceService;
import com.umar.simply.jdbc.fluent.dao.supplier.contract.FluentOrderSaveService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;
import java.time.LocalDateTime;
import static java.util.Arrays.asList;

import static com.umar.simply.jdbc.fluent.dao.supplier.db.tables.OrderTable.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;

public class OrderSaveService implements FluentOrderSaveService {

    private List<ColumnValue<?>> values;
    
    @Override
    public OrderSaveService save(Order transientOrder) {
        values = asList(
                set(ORDER_DATE, transientOrder.orderDate)
                ,set(ORDER_CUSTOMERID, transientOrder.customerId)
                ,set(ORDER_NO, transientOrder.orderNo)
                ,set(ORDER_TOTAL_AMT, transientOrder.totalAmount)
                ,set(ORDER_CREATED, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Order execute() {
        SavePersistenceService<Order> sps = new SavePersistenceService<>(JdbcUtilService.getConnection());
        Order saved = sps.save(TBL_ORDERS).withValues(values).using(ORDER_ROW_MAPPER).execute();
        return saved;
    }
    
}

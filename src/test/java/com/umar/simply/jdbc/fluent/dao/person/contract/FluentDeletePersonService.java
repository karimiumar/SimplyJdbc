package com.umar.simply.jdbc.fluent.dao.person.contract;

import com.umar.simply.jdbc.dml.operations.api.SelectFunction;
import com.umar.simply.jdbc.fluent.dao.person.DeletePersonService;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.List;

public interface FluentDeletePersonService {
    DeletePersonService delete();
    DeletePersonService where();
    DeletePersonService where(SelectFunction op);
    DeletePersonService anyColumnValues(List<ColumnValue<?>> columnValues);
    void execute();
}

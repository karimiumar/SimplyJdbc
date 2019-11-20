package com.umar.simply.jdbc.dao;

import com.umar.simply.jdbc.dao.contract.FluentDeletePersonService;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.List;

import static com.umar.simply.jdbc.dao.Person.TblPerson.person;

public class DeletePersonService implements FluentDeletePersonService {
    DeletePersistenceService<Person> dps = new DeletePersistenceService<>();

    @Override
    public DeletePersonService delete() {
        dps.from(person);
        return this;
    }

    @Override
    public DeletePersonService where() {
        dps.where();
        return this;
    }

    @Override
    public DeletePersonService where(SelectOp op) {
        dps.where(op);
        return this;
    }

    @Override
    public DeletePersonService anyColumnValues(List<ColumnValue> columnValues) {
        dps.anyColumnValues(columnValues);
        return this;
    }

    @Override
    public void execute() {
        dps.execute();
    }
}

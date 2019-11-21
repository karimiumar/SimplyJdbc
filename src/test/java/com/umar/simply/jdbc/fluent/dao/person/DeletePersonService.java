package com.umar.simply.jdbc.fluent.dao.person;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.person.Person;
import com.umar.simply.jdbc.fluent.dao.person.contract.FluentDeletePersonService;
import com.umar.simply.jdbc.dml.operations.SelectOp;
import com.umar.simply.jdbc.fluent.dao.DeletePersistenceService;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.person.Person.TblPerson.person;

public class DeletePersonService implements FluentDeletePersonService {
    DeletePersistenceService<Person> dps = new DeletePersistenceService<>(JdbcUtilService.getConnection());

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

package com.umar.simply.jdbc.fluent.dao.person;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.SavePersistenceService;
import com.umar.simply.jdbc.fluent.dao.person.contract.FluentPersonSaveService;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.person.Person.TblPerson.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import java.time.LocalDateTime;
import static java.util.Arrays.asList;

public class PersonSaveService implements FluentPersonSaveService {
    private List<ColumnValue> newValues;

    @Override
    public PersonSaveService save(Person newPerson) {
        newValues = asList(
                set(fname, newPerson.getFirstName())
                , set(lname, newPerson.getLastName())
                , set(email, newPerson.getEmail())
                , set(adult, newPerson.getAdult())
                , set(city, newPerson.getCity())
                , set(country, newPerson.getCountry())
                , set(age, newPerson.getAge())
                , set(created, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Person execute() {
        SavePersistenceService<Person> sps = new SavePersistenceService<>(JdbcUtilService.getConnection());
        Person saved = sps.save(person).withValues(newValues).using(PERSON_ROW_MAPPER).execute();
        return saved;
    }
}
package com.umar.simply.jdbc.dao;

import com.umar.simply.jdbc.dao.contract.FluentPersonSaveService;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.List;
import java.util.Optional;

import static com.umar.simply.jdbc.dao.Person.TblPerson.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
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
                , set(created, newPerson.getCreated())
        );

        return this;
    }

    public Person execute() {
        SavePersistenceService<Person> sps = new SavePersistenceService<>();
        Person saved = sps.save(person).withValues(newValues).using(PERSON_ROW_MAPPER).execute();
        return saved;
    }
}
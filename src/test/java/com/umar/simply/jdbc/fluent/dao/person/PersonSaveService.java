package com.umar.simply.jdbc.fluent.dao.person;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.SavePersistenceService;
import com.umar.simply.jdbc.fluent.dao.person.contract.FluentPersonSaveService;
import com.umar.simply.jdbc.meta.ColumnValue;

import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import java.time.LocalDateTime;
import static java.util.Arrays.asList;

public class PersonSaveService implements FluentPersonSaveService {
    private List<ColumnValue<?>> newValues;

    @Override
    public PersonSaveService save(Person newPerson) {
        newValues = asList(
                set(PERSON_FIRST_NAME, newPerson.getFirstName())
                , set(PERSON_LAST_NAME, newPerson.getLastName())
                , set(PERSON_EMAIL, newPerson.getEmail())
                , set(PERSON_IS_ADULT, newPerson.getAdult())
                , set(PERSON_CITY, newPerson.getCity())
                , set(PERSON_COUNTRY, newPerson.getCountry())
                , set(PERSON_AGE, newPerson.getAge())
                , set(PERSON_CREATED, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Person execute() {
        SavePersistenceService<Person> sps = new SavePersistenceService<>(JdbcUtilService.getConnection());
        return sps.save(TBL_PERSON).withValues(newValues).using(PERSON_ROW_MAPPER).execute();
    }
}
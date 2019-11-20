package com.umar.simply.jdbc.dao;

import com.umar.simply.jdbc.dao.contract.FluentPersonUpdateService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;

import static com.umar.simply.jdbc.dao.Person.TblPerson.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static java.util.Arrays.asList;

public class PersonUpdateService implements FluentPersonUpdateService {

    private List<ColumnValue> existingVals;
    private List<ColumnValue> newVals;
    private Long id = -1L;
    UpdatePersistenceService<Person> ups = new UpdatePersistenceService<>();

    public PersonUpdateService update(Person existing) {
        existingVals = asList(
                //same logic as equals()
                set(personId, existing.getId())
                ,set(fname, existing.getFirstName())
                ,set(lname, existing.getLastName())
                ,set(email, existing.getEmail())
        );
        id = existing.getId();
        return this;
    }

    public PersonUpdateService with(Person newVal) {
        newVals = asList(
                set(fname, newVal.getFirstName())
                ,set(lname, newVal.getLastName())
                ,set(email, newVal.getEmail())
                ,set(adult, newVal.getAdult())
                ,set(city, newVal.getCity())
                ,set(country, newVal.getCountry())
                ,set(age, newVal.getAge())
        );
        return this;
    }

    public Person execute(){
        return (Person)ups.update(person).assignNewValues(newVals).where(existingVals).of(id).using(PERSON_ROW_MAPPER).execute();
    }
}

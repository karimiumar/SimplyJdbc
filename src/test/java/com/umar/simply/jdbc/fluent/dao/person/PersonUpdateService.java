package com.umar.simply.jdbc.fluent.dao.person;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.UpdatePersistenceService;
import com.umar.simply.jdbc.fluent.dao.person.contract.FluentPersonUpdateService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.person.Person.TblPerson.*;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import java.time.LocalDateTime;
import static java.util.Arrays.asList;
import java.util.Optional;

public class PersonUpdateService implements FluentPersonUpdateService {

    private List<ColumnValue> existingVals;
    private List<ColumnValue> newVals;
    private Integer id = -1;
    UpdatePersistenceService<Person> ups = new UpdatePersistenceService<>(JdbcUtilService.getConnection());

    @Override
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

    @Override
    public PersonUpdateService with(Person newVal) {
        newVals = asList(
                set(fname, newVal.getFirstName())
                ,set(lname, newVal.getLastName())
                ,set(email, newVal.getEmail())
                ,set(adult, newVal.getAdult())
                ,set(city, newVal.getCity())
                ,set(country, newVal.getCountry())
                ,set(age, newVal.getAge())
                ,set(updated, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Person execute(){
        Optional<Person> optionalResult = ups.update(person).assignNewValues(newVals).where(existingVals).of(id).using(PERSON_ROW_MAPPER).execute();
        if(optionalResult.isEmpty()){
            throw new RuntimeException(String.format("Could not find Person with id:%d in the database.", id));
        }
        return optionalResult.get();
    }
}

package com.umar.simply.jdbc.fluent.dao.person;

import com.umar.simply.jdbc.fluent.dao.JdbcUtilService;
import com.umar.simply.jdbc.fluent.dao.UpdatePersistenceService;
import com.umar.simply.jdbc.fluent.dao.person.contract.FluentPersonUpdateService;
import com.umar.simply.jdbc.meta.ColumnValue;
import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;
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
                set(PERSON_ID, existing.getId())
                ,set(FIRST_NAME, existing.getFirstName())
                ,set(LAST_NAME, existing.getLastName())
                ,set(EMAIL, existing.getEmail())
        );
        id = existing.getId();
        return this;
    }

    @Override
    public PersonUpdateService with(Person newVal) {
        newVals = asList(
                set(FIRST_NAME, newVal.getFirstName())
                ,set(LAST_NAME, newVal.getLastName())
                ,set(EMAIL, newVal.getEmail())
                ,set(ADULT, newVal.getAdult())
                ,set(CITY, newVal.getCity())
                ,set(COUNTRY, newVal.getCountry())
                ,set(AGE, newVal.getAge())
                ,set(UPDATED, LocalDateTime.now())
        );
        return this;
    }

    @Override
    public Person execute(){
        Optional<Person> optionalResult = ups.update(PERSON).assignNewValues(newVals).where(existingVals).of(id).using(PERSON_ROW_MAPPER).execute();
        if(!optionalResult.isPresent()){
            throw new RuntimeException(String.format("Could not find Person with id:%d in the database.", id));
        }
        return optionalResult.get();
    }
}

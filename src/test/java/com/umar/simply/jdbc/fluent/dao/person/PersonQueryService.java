package com.umar.simply.jdbc.fluent.dao.person;

import com.umar.simply.jdbc.fluent.dao.QueryService;
import java.util.List;
import static com.umar.simply.jdbc.meta.ColumnValue.set;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.PERSON_ROW_MAPPER;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;
import com.umar.simply.jdbc.fluent.dao.person.contract.FluentPersonQueryService;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class PersonQueryService extends QueryService implements FluentPersonQueryService {

    public PersonQueryService(Connection connection) {
        super(connection);
    }

    @Override
    public List<Person> people() {
        List<Person> people = SELECT().ALL().FROM(TBL_PERSON).using(PERSON_ROW_MAPPER).execute();
        return people;
    }
    
    @Override
    public Optional<Person> findById(PersonTable.Id id) {
        return findById(TBL_PERSON, PERSON_ROW_MAPPER, id);
    }
    
    @Override
    public List<Person> findByName(String name) {
        Objects.requireNonNull(name, "parameter <name> is required");
        List<Person> result = Arrays.asList();
        if(name.isBlank() || name.isEmpty()) return result;
        String [] names = name.trim().split("\\s");     
        if(names.length > 1) {
           return searchByLastOrFirstNames(names);
        } else {
            result = searchByName(result, name);
        }
        return result;
    }

    private List<Person> searchByName(List<Person> result, String name) {
        result = searchCriteria(name);
        if(result.isEmpty()) {
            result = searchCriteria(name);
        }
        return result;
    }

    private List<Person> searchCriteria(String name) {
        String nameCriteria = "%" + name + "%";
        List<Person> result = SELECT().ALL().FROM(TBL_PERSON).WHERE().COLUMN(PERSON_FIRST_NAME).LIKE(nameCriteria).using(PERSON_ROW_MAPPER).execute();
        if(result.isEmpty()) {
            result = SELECT().ALL().FROM(TBL_PERSON).WHERE().COLUMN(PERSON_LAST_NAME).LIKE(nameCriteria).using(PERSON_ROW_MAPPER).execute();
        }
        return result;
    }
    
    private List<Person> searchByLastOrFirstNames(String ... names) {
        List<Person> result = SELECT().ALL().FROM(TBL_PERSON).WHERE().COLUMN(PERSON_FIRST_NAME).LIKE("%"+names[0]+"%").OR().COLUMN(PERSON_LAST_NAME).LIKE("%"+names[1]+"%").using(PERSON_ROW_MAPPER).execute();
        return result;
    }
}

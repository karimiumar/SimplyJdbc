package com.umar.simply.jdbc.fluent.dao.person;

import com.umar.simply.jdbc.fluent.dao.QueryService;
import java.util.List;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.PERSON_ROW_MAPPER;
import static com.umar.simply.jdbc.fluent.dao.person.PersonTable.*;
import com.umar.simply.jdbc.fluent.dao.person.contract.FluentPersonQueryService;
import static com.umar.simply.jdbc.meta.ColumnValue.*;
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
        List<Person> people = SELECT().ALL().FROM(TBL_PERSON).WHERE().COLUMN_EQ(eq(PERSON_ID, id.getValue())).using(PERSON_ROW_MAPPER).execute();
        String sql = SELECT().ALL().FROM(TBL_PERSON).WHERE().COLUMN_EQ(eq(PERSON_ID, id.getValue())).getSQL().getSQL();
        if(people.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(people.get(0));
    }
    
    @Override
    public List<Person> findByName(String name) {
        Objects.requireNonNull(name, "parameter <name> is required");
        List<Person> result = Arrays.asList();
        if(name.equals(" ") || name.isEmpty()) return result;
        String [] names = name.trim().split("\\s");     
        if(names.length > 1) {
            String firstName = names[0];
            String lastName = names[1];
            result = searchByName(result, firstName, lastName);
        } else {
            result = searchByName(result, name, name);
        }
        return result;
    }

    private List<Person> searchByName(List<Person> result, String firstName, String lastName) {
        result = searchCriteria(firstName);
        if(result.isEmpty()) {
            result = searchCriteria(lastName);
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
}

package com.umar.simply.jdbc.fluent.dao.person;

import com.umar.simply.jdbc.fluent.dao.QueryService;
import java.util.List;

import static com.umar.simply.jdbc.fluent.dao.person.Person.TblPerson.PERSON_ROW_MAPPER;
import static com.umar.simply.jdbc.fluent.dao.person.Person.TblPerson.person;
import java.sql.Connection;

public class PersonQueryService<Person> extends QueryService {

    public PersonQueryService(Connection connection) {
        super(connection);
    }

    public List<Person> people(){
        List<Person> people = select().all().from(person).using(PERSON_ROW_MAPPER).execute();
        return people;
    }
}

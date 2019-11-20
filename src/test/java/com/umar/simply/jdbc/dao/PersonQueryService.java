package com.umar.simply.jdbc.dao;

import java.util.List;

import static com.umar.simply.jdbc.dao.Person.TblPerson.PERSON_ROW_MAPPER;
import static com.umar.simply.jdbc.dao.Person.TblPerson.person;

public class PersonQueryService<Person> extends QueryService {

    public List<Person> people(){
        List<Person> people = select().all().from(person).using(PERSON_ROW_MAPPER).execute();
        return people;
    }
}

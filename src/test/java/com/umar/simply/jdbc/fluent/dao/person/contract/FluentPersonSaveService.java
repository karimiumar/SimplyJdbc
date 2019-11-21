package com.umar.simply.jdbc.fluent.dao.person.contract;

import com.umar.simply.jdbc.fluent.dao.person.Person;
import com.umar.simply.jdbc.fluent.dao.person.PersonSaveService;

public interface FluentPersonSaveService {
    PersonSaveService save(Person newPerson);
    Person execute();
}

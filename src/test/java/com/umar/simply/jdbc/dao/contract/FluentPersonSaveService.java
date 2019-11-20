package com.umar.simply.jdbc.dao.contract;

import com.umar.simply.jdbc.dao.Person;
import com.umar.simply.jdbc.dao.PersonSaveService;

public interface FluentPersonSaveService {
    PersonSaveService save(Person newPerson);
    Person execute();
}

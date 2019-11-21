package com.umar.simply.jdbc.fluent.dao.person.contract;

import com.umar.simply.jdbc.fluent.dao.person.Person;
import com.umar.simply.jdbc.fluent.dao.person.PersonUpdateService;

public interface FluentPersonUpdateService {

    PersonUpdateService update(Person existing);
    PersonUpdateService with(Person newVals);
    Person execute();
}

package com.umar.simply.jdbc.dao.contract;

import com.umar.simply.jdbc.dao.Person;
import com.umar.simply.jdbc.dao.PersonUpdateService;

public interface FluentPersonUpdateService {

    PersonUpdateService update(Person existing);
    PersonUpdateService with(Person newVals);
    Person execute();
}

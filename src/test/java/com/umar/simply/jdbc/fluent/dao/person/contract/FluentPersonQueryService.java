package com.umar.simply.jdbc.fluent.dao.person.contract;

import com.umar.simply.jdbc.fluent.dao.person.Person;
import com.umar.simply.jdbc.fluent.dao.person.PersonTable;
import java.util.List;
import java.util.Optional;

public interface FluentPersonQueryService {
    List<Person> people();
    Optional<Person> findById(PersonTable.Id id);
    List<Person> findByName(String name);
}

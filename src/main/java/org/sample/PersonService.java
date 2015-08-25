package org.sample;

import java.util.HashMap;
import java.util.Map;

public class PersonService {
    private Map<Integer, Person> people = new HashMap<Integer, Person>();

    public Person add(Person person) {
        int id = people.keySet().stream().max(Integer::compare).orElse(0) + 1;
        person.setId(id);
        people.put(person.getId(), person);
        return person;
    }
}
package org.sample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PersonService {

	private static final Logger log = LogManager.getLogger(PersonService.class);

	private Map<Integer, Person> people = new HashMap<Integer, Person>();

	public Person add(Person person) {
		log.info("Person to be inserted {}", person);
		int id = people.keySet().stream().max(Integer::compare).orElse(0) + 1;
		person.setId(id);
		people.put(person.getId(), person);
		return person;
	}

	public Optional<Person> find(Integer id) {
		return people.values().stream().filter(p -> p.getId().equals(id)).findFirst();
	}

	public Collection<Person> list() {
		return people.values();
	}

	public boolean delete(Integer id) {
		if (people.containsKey(id)) {
			people.remove(id);
			return true;
		}
		return false;
	}

	public Person update(Person person) {
		if (people.containsKey(person.getId())) {
			people.put(person.getId(), person);
			return person;
		}
		return null;
	}
}
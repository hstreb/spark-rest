package org.sample;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Optional;

import org.junit.Test;

public class PersonServiceTest {

	@Test
	public void testAddFirstPersonWithIdEqualsOne() {
		PersonService service = new PersonService();
		Person personInserted = service.add(new Person("Hommer Simpson", 41));
		assertThat(personInserted.getId(), equalTo(1));
	}

	@Test
	public void testFindForAnExistentId() {
		PersonService service = new PersonService();
		service.add(new Person("Hommer Simpson", 41));
		Person personFound = service.find(1).get();
		assertThat(personFound.getName(), equalTo("Hommer Simpson"));
	}

	@Test
	public void testFindForAnInexistentIdReturnsEmpty() {
		PersonService service = new PersonService();
		service.add(new Person("Hommer Simpson", 41));
		Optional<Person> personFound = service.find(2);
		assertThat(personFound.isPresent(), equalTo(false));
	}

	@Test
	public void testListReturnsEmpty() {
		PersonService service = new PersonService();
		assertThat(service.list(), hasSize(0));
	}

	@Test
	public void testAddAPersonIncraseListSize() {
		PersonService service = new PersonService();
		service.add(new Person("Hommer Simpson", 41));
		assertThat(service.list(), hasSize(1));
		service.add(new Person("Margie Simpson", 38));
		assertThat(service.list(), hasSize(2));
	}

	@Test
	public void testDeleteAnInexistentPersonReturnsFalse() {
		PersonService service = new PersonService();
		service.add(new Person("Hommer Simpson", 41));
		assertThat(service.list(), hasSize(1));
		assertThat(service.delete(2), equalTo(false));
		assertThat(service.list(), hasSize(1));
	}

	@Test
	public void testDeleteAnExistentPersonReturnsTrue() {
		PersonService service = new PersonService();
		service.add(new Person("Hommer Simpson", 41));
		assertThat(service.list(), hasSize(1));
		assertThat(service.delete(1), equalTo(true));
		assertThat(service.list(), hasSize(0));
	}

	@Test
	public void testUpdadeAnExistentPerson() {
		PersonService service = new PersonService();
		Person personAdded = service.add(new Person("Hommer Simpson", 41));
		Person personToUpdate = new Person(personAdded.getId(), "Hommer Simpson", 42);
		Person personUpdated = service.update(personToUpdate);
		assertThat(personUpdated.getAge(), equalTo(personToUpdate.getAge()));
	}

	@Test
	public void testUpdadeAnInexistentPersonReturnsNull() {
		PersonService service = new PersonService();
		service.add(new Person("Hommer Simpson", 41));
		Person personToUpdate = new Person(2, "Hommer Simpson", 42);
		Person personUpdated = service.update(personToUpdate);
		assertThat(personUpdated, equalTo(null));
	}

}
package org.sample;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import org.junit.Test;

public class PersonServiceTest {

	@Test
	public void testAddFirstPersonWithIdEqualsOne() {
		PersonService service = new PersonService();
		Person personInserted = service.add(new Person("Hommer Simpson", 41));
		assertThat(personInserted.getId(), equalTo(1));
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

}
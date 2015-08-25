package org.sample;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;

import org.junit.Test;

public class PersonServiceTest {

    @Test
    public void testAddFirstPersonWithIdEqualsOne() {
        Person person = new Person("Hommer Simpson", 41); 
        assertThat(person.getId(), nullValue());
        PersonService service = new PersonService();
        Person personInserted = service.add(person);
        assertThat(personInserted.getId(), equalTo(1));
    }

}

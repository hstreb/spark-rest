package org.sample;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Test;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;

public class PersonControllerTest {

    @Test
    public void insertAPersonCorrect() {
        PersonService personServiceMock = mock(PersonService.class);
        Person person = new Person("Humberto", 33);
        person.setId(1);
        when(personServiceMock.add(any(Person.class))).thenReturn(person);

        Request requestMock = mock(Request.class);
        when(requestMock.queryParams("name")).thenReturn("Humberto");
        when(requestMock.queryParams("age")).thenReturn("33");

        Response responseMock = mock(Response.class);

        PersonController personController = new PersonController(personServiceMock);
        String personInserted = personController.insert(requestMock, responseMock);
        assertThat(personInserted, equalTo(new Gson().toJson(person)));
    }

    @Test
    public void insertAPersonWithoutAge() {
        PersonService personServiceMock = mock(PersonService.class);
        Person person = new Person("Humberto", 0);
        person.setId(1);
        when(personServiceMock.add(any(Person.class))).thenReturn(person);

        Request requestMock = mock(Request.class);
        when(requestMock.queryParams("name")).thenReturn("Humberto");

        Response responseMock = mock(Response.class);

        PersonController personController = new PersonController(personServiceMock);
        String personInserted = personController.insert(requestMock, responseMock);
        assertThat(personInserted, equalTo(new Gson().toJson(person)));
    }

    @Test
    public void dontInsertAPersonWhenItsNull() {
        PersonService personServiceMock = mock(PersonService.class);
        when(personServiceMock.add(null)).thenReturn(null);

        Request requestMock = mock(Request.class);
        Response responseMock = mock(Response.class);

        PersonController personController = new PersonController(personServiceMock);
        String personInserted = personController.insert(requestMock, responseMock);
        assertThat(personInserted, equalTo(new Gson().toJson(null)));
    }

}
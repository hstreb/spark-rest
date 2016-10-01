package org.sample;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Test;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;

import java.util.Optional;

public class PersonControllerTest {

    private Gson gson = new Gson();

    @Test
    public void insertAPersonCorrect() {
        Person person = new Person(1, "Humberto", 33);

        PersonService personServiceMock = mock(PersonService.class);
        when(personServiceMock.add(any(Person.class))).thenReturn(person);

        Request requestMock = mock(Request.class);
        when(requestMock.queryParams("name")).thenReturn("Humberto");
        when(requestMock.queryParams("age")).thenReturn("33");

        String personInserted = new PersonController(personServiceMock)
                .insert(requestMock, mock(Response.class));
        assertThat(personInserted, equalTo(gson.toJson(person)));
    }

    @Test
    public void insertAPersonWithoutAge() {
        Person person = new Person(1, "Humberto", 0);

        PersonService personServiceMock = mock(PersonService.class);
        when(personServiceMock.add(any(Person.class))).thenReturn(person);

        Request requestMock = mock(Request.class);
        when(requestMock.queryParams("name")).thenReturn("Humberto");

        String personInserted = new PersonController(personServiceMock)
                .insert(requestMock, mock(Response.class));
        assertThat(personInserted, equalTo(gson.toJson(person)));
    }

    @Test
    public void dontInsertAPersonWhenItsNull() {
        PersonService personServiceMock = mock(PersonService.class);
        when(personServiceMock.add(null)).thenReturn(null);

        String personInserted = new PersonController(personServiceMock)
                .insert(mock(Request.class), mock(Response.class));
        assertThat(personInserted, equalTo(gson.toJson(null)));
    }

    @Test
    public void dontUpdateAPersonWhenItsNull() {
        PersonService personServiceMock = mock(PersonService.class);
        when(personServiceMock.find(null)).thenReturn(null);

        Request requestMock = mock(Request.class);
        when(requestMock.params(":id")).thenReturn(null);

        String result = new PersonController(personServiceMock)
                .update(requestMock, mock(Response.class));
        assertThat(result, equalTo(PersonController.PERSON_NOT_FOUND));
    }

    @Test
    public void updateAPerson() {
        Person person = new Person(1, "Humberto", 33);
        Person personUpdated = new Person(1, "2berto", 33);

        PersonService personServiceMock = mock(PersonService.class);
        when(personServiceMock.find(1)).thenReturn(Optional.of(person));
        when(personServiceMock.update(any(Person.class))).thenReturn(personUpdated);

        Request requestMock = mock(Request.class);
        when(requestMock.queryParams("name")).thenReturn("Humberto");
        when(requestMock.queryParams("age")).thenReturn("33");
        when(requestMock.params(":id")).thenReturn("1");

        String result = new PersonController(personServiceMock)
                .update(requestMock, mock(Response.class));
        assertThat(result, equalTo(gson.toJson(personUpdated)));
    }

}
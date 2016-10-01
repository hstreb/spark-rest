package org.sample;

import static spark.Spark.*;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;

public class PersonController {

    private static final Logger log = LogManager.getLogger(PersonController.class);

    public static final int STATUS_NOT_FOUND = 404;
    public static final String PERSON_NOT_FOUND = "Person not found!";
    public static final String PERSON_DELETED = "Person deleted!";
    public static final String APPLICATION_JSON = "application/json";

    private PersonService personService;
    private Gson gson;

    public PersonController(PersonService personService) {
        this.personService = personService;
        this.gson = new Gson();
    }

    private Person parse(Request request) {
        String name = request.queryParams("name");
        Integer age = Integer.parseInt(request.queryParams("age") == null ? "0" : request.queryParams("age"));
        return new Person(name, age);
    }

    private Optional<Person> getPerson(Request request) {
        Integer id = Integer.parseInt(request.params(":id"));
        return personService.find(id);
    }

    public String insert(Request request, Response response) {
        return gson.toJson(personService.add(parse(request)));
    }

    public String update(Request request, Response response) {
        Optional<Person> person = getPerson(request);
        if (person.isPresent()) {
            return gson.toJson(personService.update(parse(request)));
        } else {
            response.status(STATUS_NOT_FOUND);
            return PERSON_NOT_FOUND;
        }
    }

    public String list(Request request, Response response) {
        String id = request.params(":id");
        if (id == null) {
            return gson.toJson(personService.list());
        } else {
            return find(request, response);
        }
    }

    private String find(Request request, Response response) {
        Optional<Person> person = getPerson(request);
        if (person.isPresent()) {
            return gson.toJson(person.get());
        } else {
            response.status(STATUS_NOT_FOUND);
            return PERSON_NOT_FOUND;
        }
    }

    public String remove(Request request, Response response) {
        Integer id = Integer.parseInt(request.params(":id"));
        if (personService.delete(id)) {
            return PERSON_DELETED;
        } else {
            response.status(STATUS_NOT_FOUND);
            return PERSON_NOT_FOUND;
        }
    }

    public void init() {
        get("/people", APPLICATION_JSON, this::list);
        get("/people/:id", APPLICATION_JSON, this::list);
        post("/people", this::insert);
        put("/people/:id", this::update);
        delete("/people/:id", this::remove);
        before((request, response) -> log.info(request.ip() + " " + request.requestMethod() + " " + request.pathInfo()));
    }

}

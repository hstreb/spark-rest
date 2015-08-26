package org.sample;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.Optional;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class App {

    private static final Logger log = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		Gson gson = new Gson();
		
		PersonService service = new PersonService();
		
		service.add(new Person("Hommer Simpson", 41));
		
		get("/", (request, response) -> "<form action=\"http://localhost:4567/people\" method=\"post\"><input type=\"text\" name=\"name\"><input type=\"text\" name=\"age\"><input type=\"submit\"></form>");
		
		get("/people", "application/json", (request, response) -> service.list(), gson::toJson);
		
		get("/people/:id", "application/json", (request, response) -> {
            Integer id = Integer.parseInt(request.params(":id"));
            Optional<Person> person = service.find(id);
            if (person.isPresent()) {
                return person.get();
            } else {
                response.status(404);
                return "Person not found!";
            }
        }, gson::toJson);

        post("/people", (request, response) -> {
            String name = request.queryParams("name");
            Integer age = Integer.parseInt(request.queryParams("age") == null ? "0" : request.queryParams("age"));
            return service.add(new Person(name, age));
        }, gson::toJson);

        put("/people/:id", (request, response) -> {
            Integer id = Integer.parseInt(request.params(":id"));
            Optional<Person> person = service.find(id);
            if (person.isPresent()) {
                String name = request.queryParams("name");
                Integer age = Integer.parseInt(request.queryParams("age") == null ? "0" : request.queryParams("age"));
                Person personToUpdate = new Person(id, name, age);
                return service.update(personToUpdate);
            } else {
                response.status(404);
                return "Person not found!";
            }
        }, gson::toJson);

        delete("/people/:id", (request, response) -> {
            Integer id = Integer.parseInt(request.params(":id"));
            if (service.delete(id)) {
                return "Person deleted!";
            } else {
                response.status(404);
                return "Person not found!";
            }
        }, gson::toJson);

        before((request, response) -> log.info(request.requestMethod() + " " + request.pathInfo()));
	}
}

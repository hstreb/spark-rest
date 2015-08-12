package org.sample;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class People {

    private static final Logger log = Logger.getLogger(People.class.getName());

    private static Map<Integer, Person> people = new HashMap<Integer, People.Person>();

    public static void main(String[] args) {

        people.put(1, new Person(1, "Hommer Simpson", 40));

        Gson gson = new Gson();

        get("/", (request, response) -> "<form action=\"http://localhost:4567/people\" method=\"post\"><input type=\"text\" name=\"name\"><input type=\"text\" name=\"age\"><input type=\"submit\"></form>");

        get("/people", "application/json", (request, response) -> people.values(), gson::toJson);

        get("/people/:id", "application/json", (request, response) -> {
            Integer id = Integer.parseInt(request.params(":id"));
            Person person = people.get(id);
            if (person != null) {
                return person;
            } else {
                response.status(404);
                return "Person not found!";
            }
        }, gson::toJson);

        post("/people", (request, response) -> {
            String name = request.queryParams("name");
            Integer age = Integer.parseInt(request.queryParams("age") == null ? "0" : request.queryParams("age"));
            return addPerson(new Person(name, age));
        }, gson::toJson);

        put("/people/:id", (request, response) -> {
            Integer id = Integer.parseInt(request.params(":id"));
            Person person = people.remove(id);
            if (person != null) {
                String name = request.queryParams("name");
                Integer age = Integer.parseInt(request.queryParams("age") == null ? "0" : request.queryParams("age"));
                Person personToUpdate = new Person(id, name, age);
                people.put(id, personToUpdate);
                return personToUpdate;
            } else {
                response.status(404);
                return "Person not found!";
            }
        }, gson::toJson);

        delete("/people/:id", (request, response) -> {
            Integer id = Integer.parseInt(request.params(":id"));
            Person person = people.remove(id);
            if (person != null) {
                return "Person deleted!";
            } else {
                response.status(404);
                return "Person not found!";
            }
        }, gson::toJson);

        before((request, response) -> log.info(request.requestMethod() + " " + request.pathInfo()));
    }

    private static Person addPerson(Person person) {
        person.setId(people.keySet().stream().max(Integer::compare).get() + 1);
        people.put(person.getId(), person);
        return person;
    }

    public static class Person {
        private Integer id;
        private String name;
        private Integer age;

        public Person() {
        }

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public Person(Integer id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person(" + id + ", " + name + ", " + age + ")";
        }
    }
}

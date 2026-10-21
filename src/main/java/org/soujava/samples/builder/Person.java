package org.soujava.samples.builder;


import java.time.LocalDate;
import java.util.Objects;

public final class Person {

    private final String name;
    private final String surname;
    private final String city;
    private final LocalDate birthday;

    Person(String name, String surname,
           String city, LocalDate birthday) {
        this.name = Objects.requireNonNull(name);
        this.surname = Objects.requireNonNull(surname);
        this.city = Objects.requireNonNull(city);
        this.birthday = Objects.requireNonNull(birthday);
    }

    public String name() {
        return name;
    }

    public String surname() {
        return surname;
    }

    public String city() {
        return city;
    }

    public LocalDate birthday() {
        return birthday;
    }
}

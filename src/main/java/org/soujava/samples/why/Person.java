package org.soujava.samples.why;

import java.time.LocalDate;

public class Person {

    private String name;

    private String surname;

    private String city;

    private LocalDate birthday;

    /**
     *
     * Avoid use the default constructor, use the xxx instead, only for frameworks that require a default constructor
     */
    Person() {
    }
}
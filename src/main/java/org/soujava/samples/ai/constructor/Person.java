package org.soujava.samples.ai.constructor;

import java.time.LocalDate;

public class Person {

    private String name;

    private String surname;

    private String city;

    private LocalDate birthday;

    /**
     *
     * @deprecated  avoid use the default constructor, use the xxx instead, only for frameworks that require a default constructor
     */
    Person() {
    }
}
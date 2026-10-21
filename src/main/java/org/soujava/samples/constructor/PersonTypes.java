package org.soujava.samples.constructor;

import java.util.Objects;

public class PersonTypes {

    //contains both name and surname
    private FullName fullName;

    private City city;

    private Birthday birthday;

    /**
     *
     * @deprecated  avoid use the default constructor, use the xxx instead, only for frameworks that require a default constructor
     */
    PersonTypes() {
    }

    public PersonTypes(FullName fullName, City city, Birthday birthday) {
        this.fullName = Objects.requireNonNull(fullName);
        this.city = Objects.requireNonNull(city);
        this.birthday = Objects.requireNonNull(birthday);
    }
}
package com.example.relazioneingdelsoft;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Objects;

/***
 * Questa è la classe utilizzata per descrivere gli employee e i client che vengono aggiunti o eliminate
 */
public class Person {
    ObjectProperty<Integer> idPerson;
    StringProperty lastName;
    StringProperty firstName;
    ObjectProperty<LocalDate>  birthday;

    public Person(Integer id, String lastName, String firstName, LocalDate birthday) {
        this.idPerson = new SimpleObjectProperty<>(id);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.birthday = new SimpleObjectProperty<>(birthday);
    }

    public Person(String lastName, String firstName) {
        this.idPerson = new SimpleObjectProperty<>(0);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.birthday = new SimpleObjectProperty<>(LocalDate.now());
    }

    public Person() {
        this.idPerson = new SimpleObjectProperty<>(0);
        this.lastName = new SimpleStringProperty("NULL");
        this.firstName = new SimpleStringProperty("NULL");
        this.birthday = new SimpleObjectProperty<>(LocalDate.now());
    }



    public Integer getIdPerson() {return this.idPerson.get();}
    public ObjectProperty<Integer> idPersonProperty() {return this.idPerson;}
    public void setIdPerson(Integer idPerson) {this.idPerson.set(idPerson);}



    public String getLastName() {
        return lastName.get();
    }
    public StringProperty lastNameProperty() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }






    public String getFirstName() {
        return firstName.get();
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }






    public LocalDate getBirthday() {
        return birthday.get();
    }
    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        return Objects.equals(lastName, person.lastName) && Objects.equals(firstName, person.firstName) && Objects.equals(birthday, person.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, birthday);
    }

    @Override
    public String toString() {
        return "Person{" + "lastName='" + lastName + '\'' + ", firstName='" + firstName + '\'' + ", birthday=" + birthday + '}';
    }
}

package com.example.relazioneingdelsoft;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

/***
 * Questa è la classe utilizzata per descrivere i tipi di lavoro possibili
 */
public class TypeOfJob {

    StringProperty name;
    StringProperty description;

    public TypeOfJob(String name, String description) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
    }

    public TypeOfJob(String name) {
        this.name = new SimpleStringProperty(name);;
        this.description = new SimpleStringProperty("");
    }

    public TypeOfJob() {
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
    }

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {this.name.set(name);}

    public String getDescription() {
        return description.get();
    }
    public StringProperty descriptionProperty() {
        return description;
    }
    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TypeOfJob typeOfJob = (TypeOfJob) o;
        return Objects.equals(name, typeOfJob.name) && Objects.equals(description, typeOfJob.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "TypeOfJob{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
}

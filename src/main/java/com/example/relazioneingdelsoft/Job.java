package com.example.relazioneingdelsoft;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Objects;

/***
 * Questa è la classe utilizzata per descrivere i lavori che vengono effettuati
 */
public class Job {
    ObjectProperty<Integer> idJob;
    StringProperty nameType;
    ObjectProperty<Integer> idClient;
    StringProperty firstNameClient;
    StringProperty lastNameClient;
    ObjectProperty<Integer> idEmployee;
    StringProperty firstNameEmployee;
    StringProperty lastNameEmployee;
    ObjectProperty<LocalDate> dateOfJob;
    ObjectProperty<Float> hours;
    ObjectProperty<Float> size;
    StringProperty address;

    public Job(Integer id, String nameType, Integer idClient, String firstNameClient, String lastNameClient,
               Integer idEmployee,
               String firstNameEmployee, String lastNameEmployee, LocalDate dateOfJob, Float hours, Float size,
               String address) {
        this.idJob = new SimpleObjectProperty<>(id);
        this.nameType = new SimpleStringProperty(nameType);
        this.idClient = new SimpleObjectProperty<>(idClient);
        this.firstNameClient = new SimpleStringProperty(firstNameClient);
        this.lastNameClient = new SimpleStringProperty(lastNameClient);
        this.idEmployee = new SimpleObjectProperty<>(idEmployee);
        this.firstNameEmployee = new SimpleStringProperty(firstNameEmployee);
        this.lastNameEmployee = new SimpleStringProperty(lastNameEmployee);
        this.dateOfJob = new SimpleObjectProperty<>(dateOfJob);
        this.hours = new SimpleObjectProperty<>(hours);
        this.size = new SimpleObjectProperty<>(size);
        this.address = new SimpleStringProperty(address);
    }
    public Job() {
        this.idJob = new SimpleObjectProperty<>(0);
        this.nameType = new SimpleStringProperty("");
        this.idClient = new SimpleObjectProperty<>(0);
        this.firstNameClient = new SimpleStringProperty("");
        this.lastNameClient = new SimpleStringProperty("");
        this.idEmployee = new SimpleObjectProperty<>(0);
        this.firstNameEmployee = new SimpleStringProperty("");
        this.lastNameEmployee = new SimpleStringProperty("");
        this.dateOfJob = new SimpleObjectProperty<>();
        this.hours = new SimpleObjectProperty<>(00.00F);
        this.size = new SimpleObjectProperty<>(0.0F);
        this.address = new SimpleStringProperty("");
    }

    public Integer getIdJob() {return this.idJob.get();}
    public ObjectProperty<Integer> idJobProperty() {return this.idJob;}
    public void setIdJob(Integer idJob) {this.idJob.set(idJob);}

    public String getNameType() {return nameType.get();}
    public StringProperty nameTypeProperty() {
        return nameType;
    }
    public void setNameType(String nameType) {
        this.nameType.set(nameType);
    }

    public Integer getIdClient() {return this.idClient.get();}
    public ObjectProperty<Integer> idClientProperty() {return this.idClient;}
    public void setIdClient(Integer idClient) {this.idClient.set(idClient);}

    public String getFirstNameClient() {return firstNameClient.get();}
    public StringProperty firstNameClientProperty() {
        return firstNameClient;
    }
    public void setFirstNameClient(String firstNameClient) {
        this.firstNameClient.set(firstNameClient);
    }

    public String getLastNameClient() {return lastNameClient.get();}
    public StringProperty lastNameClientProperty() {
        return lastNameClient;
    }
    public void setLastNameClient(String lastNameClient) {
        this.lastNameClient.set(lastNameClient);
    }

    public Integer getIdEmployee() {return this.idEmployee.get();}
    public ObjectProperty<Integer> idEmployeeProperty() {return this.idEmployee;}
    public void setIdEmployee(Integer idEmployee) {this.idEmployee.set(idEmployee);}

    public String getFirstNameEmployee() {return firstNameEmployee.get();}
    public StringProperty firstNameEmployeeProperty() {
        return firstNameEmployee;
    }
    public void setFirstNameEmployee(String firstNameEmployee) {
        this.firstNameEmployee.set(firstNameEmployee);
    }

    public String getLastNameEmployee() {return lastNameEmployee.get();}
    public StringProperty lastNameEmployeeProperty() {
        return lastNameEmployee;
    }
    public void setLastNameEmployee(String lastNameEmployee) {
        this.lastNameEmployee.set(lastNameEmployee);
    }

    public LocalDate getDateOfJob() {
        return dateOfJob.get();
    }
    public ObjectProperty<LocalDate> dateOfJobProperty() {
        return dateOfJob;
    }
    public void setDateOfJob(LocalDate dateOfJob) {
        this.dateOfJob.set(dateOfJob);
    }

    public Float getHours() {
        return hours.get();
    }
    public ObjectProperty<Float> hoursProperty() {
        return hours;
    }
    public void setHours(Float hours) {
        this.hours.set(hours);
    }

    public float getSize() {
        return size.get();
    }
    public ObjectProperty<Float> sizeProperty() {
        return size;
    }
    public void setSize(Float size) {
        this.size.set(size);
    }

    public String getAddress() {
        return address.get();
    }
    public StringProperty addressProperty() {
        return address;
    }
    public void setAddress(String address) {
        this.address.set(address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Job job = (Job) o;
        return Objects.equals(nameType, job.nameType) && Objects.equals(idClient, job.idClient) && Objects.equals(idEmployee, job.idEmployee) && Objects.equals(dateOfJob, job.dateOfJob) && Objects.equals(hours, job.hours) && Objects.equals(size, job.size) && Objects.equals(address, job.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameType, idClient, idEmployee, dateOfJob, hours, size, address);
    }

    @Override
    public String toString() {
        return "Job{" + "nameType=" + nameType + ", idClient=" + idClient + ", idEmployee=" + idEmployee + ", dateOfJob=" + dateOfJob + ", hours=" + hours + ", size=" + size + ", address=" + address + '}';
    }
}

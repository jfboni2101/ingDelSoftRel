package com.example.relazioneingdelsoft;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/***
 * Controller utilizzato per aggiungere employee e client
 */
public class NewPersonController implements Savable<Person>{

    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private DatePicker birthdayDatePicker;
    Person person;

    @FXML
    public void initialize() {
        firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> person.firstNameProperty().set(newValue));
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> person.lastNameProperty().set(newValue));
        birthdayDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> person.birthdayProperty().set(newValue));
    }

    @Override
    public void update() {
        firstNameTextField.textProperty().set(person.getFirstName());
        lastNameTextField.textProperty().set(person.getLastName());
        birthdayDatePicker.valueProperty().set(person.getBirthday());
    }

    @Override
    public Person getElement() {
        return person;
    }

    @Override
    public void setElement(Person person) {
        this.person = person;
        update();
    }

}

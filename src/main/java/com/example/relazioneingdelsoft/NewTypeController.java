package com.example.relazioneingdelsoft;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/***
 * Controller utilizzato per aggiungere nuovi tipi di lavoro possibili
 */
public class NewTypeController implements Savable<TypeOfJob>{

    @FXML private TextField nameTextField;
    @FXML private TextArea descriptionTextField;
    TypeOfJob type;

    @Override
    @FXML
    public void initialize() {
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> type.nameProperty().set(newValue));
        descriptionTextField.textProperty().addListener((observable, oldValue, newValue) -> type.descriptionProperty().set(newValue));
    }

    @Override
    public void update() {
        nameTextField.textProperty().set(type.getName());
        descriptionTextField.textProperty().set(type.getDescription());
    }

    @Override
    public TypeOfJob getElement() {
        return type;
    }

    @Override
    public void setElement(TypeOfJob type) {
        this.type = type;
        update();
    }

}

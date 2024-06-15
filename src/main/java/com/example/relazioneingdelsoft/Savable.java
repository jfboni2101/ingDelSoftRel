package com.example.relazioneingdelsoft;

import javafx.fxml.FXML;

public interface Savable<T> {
    @FXML
    public void initialize();

    void update();

    public T getElement();

    public void setElement(T t);
}

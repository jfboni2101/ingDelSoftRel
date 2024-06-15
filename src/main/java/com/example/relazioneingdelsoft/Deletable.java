package com.example.relazioneingdelsoft;

import javafx.fxml.FXML;

public interface Deletable<T> {
    @FXML
    public void initialize();

    void update();

    public T getElement();

    public void setElement(T t);
}

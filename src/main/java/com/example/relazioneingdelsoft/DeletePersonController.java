package com.example.relazioneingdelsoft;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.util.TimeZone;

public abstract class DeletePersonController implements Deletable<Person>{

    @FXML
    protected ComboBox<String> selectComboBox;

    protected static final String JDBC_Driver_MySQL = "com.mysql.cj.jdbc.Driver";
    protected static final String JDBC_URL_MySQL = "jdbc:mysql://localhost:3306/boni_srl?user=boniSrl&password=Magamago2101!&serverTimezone=" + TimeZone.getDefault().getID();

    protected Person person;

    @FXML
    public void initialize() {
        Connection c = null;
        PreparedStatement statement = null;
        try {
            Class.forName(JDBC_Driver_MySQL);
            c = DriverManager.getConnection(JDBC_URL_MySQL);
            statement = c.prepareStatement(getQuery());
            ResultSet rs = statement.executeQuery();
            String string;
            while(rs.next()) {
                string = getStringFromResultSet(rs);
                selectComboBox.getItems().add(string);
            }
            selectComboBox.getSelectionModel().select(getDefaultSelection());

            selectComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
                    person.idPersonProperty().set(Integer.valueOf(newValue.substring(0, newValue.indexOf('-')))));

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(c, statement);
        }
    }

    protected abstract String getQuery();

    protected abstract String getStringFromResultSet(ResultSet rs) throws SQLException;

    protected abstract String getDefaultSelection();

    private void closeResources(Connection c, PreparedStatement statement) {
        if (c != null) {
            try {
                if (statement != null) {
                    statement.close();
                }
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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

    @Override
    public void update() {
    }
}

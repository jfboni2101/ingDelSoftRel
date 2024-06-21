package com.example.relazioneingdelsoft;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.TimeZone;

/***
 * Controller utilizzato per aggiungere nuovi lavori effettuati
 */
public class NewJobController implements Savable<Job>{
    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<String> clientComboBox;
    @FXML private ComboBox<String> employeeComboBox;
    @FXML private DatePicker dateOfJobDatePicker;
    @FXML private TextField hourTextField;
    @FXML private TextField sizeTextField;
    @FXML private TextField addressTextField;
    public static final String JDBC_Driver_MySQL = "com.mysql.cj.jdbc.Driver";
    public static final String JDBC_URL_MySQL = "jdbc:mysql://localhost:3306/boni_srl?user=boniSrl&password" +
            "=Magamago2101!" + "&serverTimezone=" + TimeZone.getDefault().getID();
    Job job;

    @FXML
    public void initialize() {
        Connection c = null;
        PreparedStatement statement = null;
        try {
            Class.forName(JDBC_Driver_MySQL);
            c = DriverManager.getConnection(JDBC_URL_MySQL);
            statement = c.prepareStatement(   "SELECT C._id AS 'idClient',C.firstName AS 'firstName', C.lastName AS 'lastName'\n" +
                    "FROM Client AS C;");
            ResultSet rs = statement.executeQuery();
            String string;
            while(rs.next()) {
                string = "";
                string += rs.getInt("idClient");
                string += "-";
                string += rs.getString("firstName");
                string += " ";
                string += rs.getString("lastName");
                clientComboBox.getItems().add(string);
            }
            clientComboBox.getSelectionModel().select("SELECT CLIENT");

            statement = c.prepareStatement(   "SELECT E._id AS 'IdEmployee', E.firstName AS 'firstName',E.lastName AS 'lastName'\n" +
                    "FROM Employee AS E;");
            rs = statement.executeQuery();
            while(rs.next()) {
                string = "";
                string += rs.getInt("idEmployee");
                string += "-";
                string += rs.getString("firstName");
                string += " ";
                string += rs.getString("lastName");
                employeeComboBox.getItems().add(string);
            }
            employeeComboBox.getSelectionModel().select("SELECT EMPLOYEE");

            statement = c.prepareStatement(   "SELECT T.name AS 'name'\n" +
                    "FROM Type AS T;");
            rs = statement.executeQuery();
            while(rs.next()) {
                string = "";
                string += rs.getString("name");
                typeComboBox.getItems().add(string);
            }
            typeComboBox.getSelectionModel().select("SELECT TYPE OF JOB");

            addressTextField.textProperty().addListener((observable, oldValue, newValue) -> job.addressProperty().set(newValue));
            sizeTextField.textProperty().addListener((observable, oldValue, newValue) -> job.sizeProperty().set(Float.valueOf(newValue)));
            dateOfJobDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> job.dateOfJobProperty().set(newValue));
            hourTextField.textProperty().addListener((observable, oldValue, newValue) -> job.hoursProperty().set(Float.valueOf(newValue)));
            clientComboBox.valueProperty().addListener((observable, oldValue, newValue) -> job.idClientProperty().set(Integer.valueOf(newValue.substring(0, newValue.indexOf('-')))));
            employeeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> job.idEmployeeProperty().set(Integer.valueOf(newValue.substring(0, newValue.indexOf('-')))));
            typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> job.nameTypeProperty().set(newValue));

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeResources(c, statement);
        }
    }

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
    public void update() {
        addressTextField.textProperty().set(job.getAddress());
        sizeTextField.textProperty().set(String.valueOf(job.getSize()));
        dateOfJobDatePicker.valueProperty().set(LocalDate.now());
        hourTextField.textProperty().set(String.valueOf(0));
    }

    @Override
    public Job getElement() {
        return job;
    }

    @Override
    public void setElement(Job job) {
        this.job = job;
        update();
    }

}

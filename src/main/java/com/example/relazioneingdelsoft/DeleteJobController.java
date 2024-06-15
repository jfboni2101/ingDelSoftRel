package com.example.relazioneingdelsoft;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.util.TimeZone;

/***
 * Questo è il controller per eliminare i job
 */
public class DeleteJobController implements Deletable<Job>{
    @FXML
    private ComboBox<String> selectComboBox;

    public static final String JDBC_Driver_MySQL = "com.mysql.cj.jdbc.Driver";
    public static final String JDBC_URL_MySQL = "jdbc:mysql://localhost:3306/boni_srl?user=boniSrl&password" +
            "=Magamago2101!" + "&serverTimezone=" + TimeZone.getDefault().getID();

    Job job;

    @Override
    @FXML
    public void initialize() {
        Connection c = null;
        PreparedStatement statement = null;
        try {
            Class.forName(JDBC_Driver_MySQL);
            c = DriverManager.getConnection(JDBC_URL_MySQL);

            statement = c.prepareStatement(
                    "SELECT J._id AS 'id', T.name AS 'type', C.lastName AS 'lastNameClient', C.firstName AS " +
                            "'firstNameClient', E.lastName AS 'lastNameEmployee', E.firstName AS 'firstNameEmployee'," +
                            " J.dateOfJob AS 'date', J.hours AS 'hour',  J.size AS 'size', J.address AS 'address'\n" +
                    "FROM Job AS J\n" +
                    "JOIN Client AS C ON (C._id=J.idClient)\n" +
                    "JOIN Employee AS E ON (E._id=J.idEmployee)\n" +
                    "JOIN Type AS T ON (T.name=J.nameType)" +
                            "ORDER BY J._id;");
            ResultSet rs = statement.executeQuery();
            String string;
            while(rs.next()) {
                string = "";
                string += rs.getInt("id");
                string += "-";
                string += rs.getString("type");
                string += ", ";
                string += rs.getString("lastNameClient");
                string += " ";
                string += rs.getString("firstNameClient");
                string += ", ";
                string += rs.getString("lastNameEmployee");
                string += " ";
                string += rs.getString("firstNameEmployee");
                string += ", ";
                string += rs.getDate("date");
                string += ", ";
                string += rs.getFloat("hour");
                string += ", ";
                string += rs.getFloat("size");
                string += ", ";
                string += rs.getString("address");
                selectComboBox.getItems().add(string);
            }
            selectComboBox.getSelectionModel().select("SELECT JOB");

            selectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> job.idJobProperty().set(Integer.valueOf(newValue.substring(0, newValue.indexOf('-')))));

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (c != null) {
                try {
                    statement.close();
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    @Override
    public void update() {
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

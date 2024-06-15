package com.example.relazioneingdelsoft;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.TimeZone;
import java.sql.*;

//Pagina di login dell'applicazione, compare appena l'applicazione viene eseguita

public class LoginController {
    @FXML private PasswordField passwordTextField;
    @FXML private TextField usernameTextField;
    @FXML private Label provaLabel;

    /***
     * Creiamo le variabili per il collegamento al database interno del computer
     */
    public static final String JDBC_Driver_MySQL = "com.mysql.cj.jdbc.Driver";
    public static final String JDBC_URL_MySQL = "jdbc:mysql://localhost:3306/boni_srl?user=boniSrl&password" +
            "=Magamago2101!" + "&serverTimezone=" + TimeZone.getDefault().getID();



    @FXML
    public void initialize() {

    }

    /***
     * Funzione chiamata una volta cliccato il bottone Login per controllare se i dati inseriti sono tra quelli
     * contenuti nel database all'interno della tabella User.
     *
     */
    @FXML void handleLogin(ActionEvent event) {
        String username_text = usernameTextField.getText();
        String password_text = passwordTextField.getText();
        provaLabel.setText("INSERT USERNAME AND PASSWORD");
        Connection c = null;
        PreparedStatement statement = null;
        if(username_text.equals("") && password_text.equals("")) {
            provaLabel.setText("UNCORRECT LOGGED (username or password blank)");
        } else {
            try{
                Class.forName(JDBC_Driver_MySQL);
                c = DriverManager.getConnection(JDBC_URL_MySQL);
                statement = c.prepareStatement("SELECT * FROM Users AS U WHERE BINARY U.username=? " +
                        "and BINARY U.password=?;");
                statement.setString(1, username_text);
                statement.setString(2, password_text);

                ResultSet rs = statement.executeQuery();

                if(rs.next()) {
                    provaLabel.setTextFill(Color.GREEN);
                    provaLabel.setText("CORRECT LOGGED");

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Login Corretto");
                    alert1.setHeaderText("Benvenuto!!!");
                    alert1.setContentText("Ora avrai la possibilità di accedere al gestionale.");
                    alert1.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("first-page.fxml"));
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                        } else {
                            usernameTextField.setText("");
                            passwordTextField.setText("");
                            provaLabel.setTextFill(Color.BLACK);
                            provaLabel.setText("INSERT USERNAME AND PASSWORD");
                        }
                    });

                } else {
                    provaLabel.setTextFill(Color.RED);
                    provaLabel.setText("FAILED LOGGED");

                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Login Non Corretto");
                    alert2.setHeaderText("CAMPI ERRATI!!!");
                    alert2.setContentText("Username o password errati!!!.");
                    alert2.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            usernameTextField.setText("");
                            passwordTextField.setText("");
                            provaLabel.setTextFill(Color.BLACK);
                            provaLabel.setText("INSERT USERNAME AND PASSWORD");
                        } else {
                            usernameTextField.setText("");
                            passwordTextField.setText("");
                            provaLabel.setTextFill(Color.BLACK);
                            provaLabel.setText("INSERT USERNAME AND PASSWORD");
                        }
                    });
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException();
            } catch (SQLException e) {
                throw new RuntimeException();
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
    }


}
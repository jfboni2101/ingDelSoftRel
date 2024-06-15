package com.example.relazioneingdelsoft;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.TimeZone;
import java.sql.*;

/***
 * Questa è la classe controller della pagina iniziale, successiva al login
 */
public class FirstPageController {

    //Creiamo le variabili per il collegamento al database interno al computer
    public static final String JDBC_Driver_MySQL = "com.mysql.cj.jdbc.Driver";
    public static final String JDBC_URL_MySQL = "jdbc:mysql://localhost:3306/boni_srl?user=boniSrl&password" +
            "=Magamago2101!" + "&serverTimezone=" + TimeZone.getDefault().getID();

    /*
     * Qui sono presenti tutti i bottoni, label, tabelle, ecc. contenuti nella pagina principale dell'applicazione,
     * ovvero quella subito dopo il login
     */



    @FXML private BorderPane root;

    /*
     * Queste sono le variabili della tabella Employee
     */
    @FXML private TableView<Person> tableEmployee;
    @FXML private TableColumn<Person, String> lastNameEmployee;
    @FXML private TableColumn<Person, String> firstNameEmployee;
    @FXML private TableColumn<Person, LocalDate> birthdayEmployee;

    //ObservableList dei clienti
    private ObservableList<Person> employee;

    //Queste sono le variabili della tabella Client
    @FXML private TableView<Person> tableClient;
    @FXML private TableColumn<Person, String> lastNameClient;
    @FXML private TableColumn<Person, String> firstNameClient;
    @FXML private TableColumn<Person, String> birthdayClient;

    //ObservableList dei clienti
    private ObservableList<Person> client;


    //Queste sono le variabili della tabella TypeOfJob
    @FXML private TableView<TypeOfJob> tableTypeOfJob;
    @FXML private TableColumn<TypeOfJob, String> nameOfJob;
    @FXML private TableColumn<TypeOfJob, String> descriptionOfJob;

    //ObservableList dei tipi di lavoro
    private ObservableList<TypeOfJob> type;

    //Queste sono le variabili della tabella Job
    @FXML private TableView<Job> tableJob;
    @FXML private TableColumn<Job, Integer> idJob;
    @FXML private TableColumn<Job, String> lastNameClientJob;
    @FXML private TableColumn<Job, String> firstNameClientJob;
    @FXML private TableColumn<Job, String> lastNameEmployeeJob;
    @FXML private TableColumn<Job, String> firstNameEmployeeJob;
    @FXML private TableColumn<Job, LocalDate> dateOfJob;
    @FXML private TableColumn<Job, LocalTime> hoursOfJob;
    @FXML private TableColumn<Job, Integer> sizeOfJob;
    @FXML private TableColumn<Job, Integer> addressOfJob;
    @FXML private TableColumn<Job, String> typeOfJob;

    //ObservableList dei lavori effettuati
    private ObservableList<Job> job;

    /***
     * Funzione utilizzata per inizializzare tutte le tabelle del programma e inserire i dati presi dal database
     * attraverso getEmployeeData, getClientData, getTypeData, getJobData
     */
    @FXML
    public void initialize() {

        lastNameEmployee.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameEmployee.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        birthdayEmployee.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        tableEmployee.setItems(getEmployeeData());

        lastNameClient.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameClient.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        birthdayClient.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        tableClient.setItems(getClientsData());

        nameOfJob.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionOfJob.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableTypeOfJob.setItems(getTypeData());

        typeOfJob.setCellValueFactory(new PropertyValueFactory<>("nameType"));

        idJob.setCellValueFactory(new PropertyValueFactory<>("idJob"));
        lastNameClientJob.setCellValueFactory(new PropertyValueFactory<>("lastNameClient"));
        firstNameClientJob.setCellValueFactory(new PropertyValueFactory<>("firstNameClient"));
        lastNameEmployeeJob.setCellValueFactory(new PropertyValueFactory<>("lastNameEmployee"));
        firstNameEmployeeJob.setCellValueFactory(new PropertyValueFactory<>("firstNameEmployee"));
        dateOfJob.setCellValueFactory(new PropertyValueFactory<>("dateOfJob"));
        hoursOfJob.setCellValueFactory(new PropertyValueFactory<>("hours"));
        sizeOfJob.setCellValueFactory(new PropertyValueFactory<>("size"));
        addressOfJob.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableJob.setItems(getJobData());

    }

    /***
     * Funzione in grado di reperire i dati dal database degli Employee e inserirli in un ObservableList
     *
     * @return ObservableList di Person, contenente i dati del database
     */
    public ObservableList<Person> getEmployeeData() {
        employee = FXCollections.observableArrayList();
        Connection c = null;
        PreparedStatement statement = null;
        try {
            //Instauriamo il collegamento con il database
            Class.forName(JDBC_Driver_MySQL);
            c = DriverManager.getConnection(JDBC_URL_MySQL);
            statement = c.prepareStatement("SELECT E.* FROM Employee AS E");
            //Eseguiamo la query
            ResultSet rs = statement.executeQuery();
            String firstname, lastname;
            Integer id;
            LocalDate birthday;
            //Preleviamo i dati dalla query SQL e aggiungiamo a employee la persona letta
            while (rs.next()) {
                id = rs.getInt("_id");
                lastname = rs.getString("lastName");
                firstname = rs.getString("firstName");
                birthday = LocalDate.parse(rs.getString("birthday"));
                employee.add(new Person(id, lastname, firstname, birthday));
            }
        } catch (SQLException e) {
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("ERRORE!");
            alert3.setHeaderText("Errore SQL");
            alert3.setContentText("C'è stato un errore nell'SQL");
            alert3.showAndWait();
        } catch (ClassNotFoundException e) {
            Alert alert4 = new Alert(Alert.AlertType.ERROR);
            alert4.setTitle("ERRORE!");
            alert4.setHeaderText("Errore nella creazione Class.forName()");
            alert4.setContentText("C'è stato un errore nella creazione di Class.forName()");
            alert4.showAndWait();
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
        //Ritorniamo employee
        return employee;
    }

    /***
     * Funzione in grado di reperire i dati dal database dei Client e inserirli in un ObservableList
     *
     * @return ObservableList di Person, contenente i dati del database
     */
    public ObservableList<Person> getClientsData() {
        client = FXCollections.observableArrayList();
        Connection c = null;
        PreparedStatement statement = null;
        try {
            //Instauriamo il collegamento con il database
            Class.forName(JDBC_Driver_MySQL);
            c = DriverManager.getConnection(JDBC_URL_MySQL);
            statement = c.prepareStatement("SELECT C.* FROM Client AS C");
            //Eseguiamo la query
            ResultSet rs = statement.executeQuery();
            String firstname, lastname;
            Integer id;
            LocalDate birthday;
            //Preleviamo i dati dalla query SQL e aggiungiamo a client la persona letta
            while (rs.next()) {
                id = rs.getInt("_id");
                lastname = rs.getString("lastName");
                firstname = rs.getString("firstName");
                birthday = LocalDate.parse(rs.getString("birthday"));
                client.add(new Person(id, lastname, firstname, birthday));
            }
        } catch (SQLException e) {
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("ERRORE!");
            alert3.setHeaderText("Errore SQL");
            alert3.setContentText("C'è stato un errore nell'SQL");
            alert3.showAndWait();
        } catch (ClassNotFoundException e) {
            Alert alert4 = new Alert(Alert.AlertType.ERROR);
            alert4.setTitle("ERRORE!");
            alert4.setHeaderText("Errore nella creazione Class.forName()");
            alert4.setContentText("C'è stato un errore nella creazione di Class.forName()");
            alert4.showAndWait();
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
        //Ritorniamo client
        return client;
    }

    /***
     * Funzione in grado di reperire i dati dal database dei tipi di lavori e inserirli in un ObservableList
     *
     * @return ObservableList di Type, contenente i dati del database
     */
    public ObservableList<TypeOfJob> getTypeData() {
        type = FXCollections.observableArrayList();
        Connection c = null;
        PreparedStatement statement = null;
        try {
            //Instauriamo il collegamento con il database
            Class.forName(JDBC_Driver_MySQL);
            c = DriverManager.getConnection(JDBC_URL_MySQL);
            statement = c.prepareStatement("SELECT T.* FROM Type AS T");
            //Eseguiamo la query
            ResultSet rs = statement.executeQuery();
            String name;
            String description;
            //Preleviamo i dati dalla query SQL e aggiungiamo a type il tipo di lavoro letto
            while (rs.next()) {
                name = rs.getString("name");
                description = rs.getString("description");
                type.add(new TypeOfJob(name, description));
            }
        } catch (SQLException e) {
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("ERRORE!");
            alert3.setHeaderText("Errore SQL");
            alert3.setContentText("C'è stato un errore nell'SQL");
            alert3.showAndWait();
        } catch (ClassNotFoundException e) {
            Alert alert4 = new Alert(Alert.AlertType.ERROR);
            alert4.setTitle("ERRORE!");
            alert4.setHeaderText("Errore nella creazione Class.forName()");
            alert4.setContentText("C'è stato un errore nella creazione di Class.forName()");
            alert4.showAndWait();
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
        //Ritorniamo client
        return type;
    }

    /***
     * Funzione in grado di reperire i dati dal database dei lavori effettuati e inserirli in un ObservableList
     *
     * @return ObservableList di Job, contenente i dati del database
     */
    public ObservableList<Job> getJobData() {
        job = FXCollections.observableArrayList();
        Connection c = null;
        PreparedStatement statement = null;
        try {
            //Instauriamo il collegamento con il database
            Class.forName(JDBC_Driver_MySQL);
            c = DriverManager.getConnection(JDBC_URL_MySQL);

            statement = c.prepareStatement("""
                    SELECT J._id AS 'id', T.name AS 'name', C._id AS 'idClient',  C.lastName AS 'lastNameClient', C.firstName AS 'firstNameClient', E._id AS 'idEmployee', E.lastName AS 'lastNameEmployee', E.firstName AS 'firstNameEmployee', J.dateOfJob, J.hours,  J.size, J.address
                    FROM Job AS J
                    JOIN Client AS C ON (C._id=J.idClient)
                    JOIN Employee AS E ON (E._id=J.idEmployee)
                    JOIN Type AS T ON (T.name=J.nameType)
                    ORDER BY J._id;""");
            //Eseguiamo la query
            ResultSet rs = statement.executeQuery();
            Integer id;
            String nameType;
            Integer idClient;
            String firstNameClient;
            String lastNameClient;
            Integer idEmployee;
            String firstNameEmployee;
            String lastNameEmployee;
            LocalDate dateOfJob;
            Float hours;
            Float size;
            String address;
            //Preleviamo i dati dalla query SQL e aggiungiamo a job il lavoro letto
            while (rs.next()) {
                id = rs.getInt("id");
                nameType = rs.getString("name");
                idClient = rs.getInt("idClient");
                firstNameClient = rs.getString("firstNameClient");
                lastNameClient = rs.getString("lastNameClient");
                idEmployee = rs.getInt("idEmployee");
                firstNameEmployee = rs.getString("firstNameEmployee");
                lastNameEmployee = rs.getString("lastNameEmployee");
                dateOfJob = rs.getDate("dateOfJob").toLocalDate();
                hours = rs.getFloat("hours");
                size = rs.getFloat("size");
                address = rs.getString("address");
                job.add(new Job(id, nameType, idClient, firstNameClient, lastNameClient, idEmployee, firstNameEmployee,
                        lastNameEmployee, dateOfJob, hours,
                        size, address));
            }
        } catch (SQLException e) {
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("ERRORE!");
            alert3.setHeaderText("Errore SQL");
            alert3.setContentText("C'è stato un errore nell'SQL");
            alert3.showAndWait();
        } catch (ClassNotFoundException e) {
            Alert alert4 = new Alert(Alert.AlertType.ERROR);
            alert4.setTitle("ERRORE!");
            alert4.setHeaderText("Errore nella creazione Class.forName()");
            alert4.setContentText("C'è stato un errore nella creazione di Class.forName()");
            alert4.showAndWait();
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
        //Ritorniamo job
        return job;
    }

    /***
     * Funzione richiamata quando viene cliccato il pulsante 'Add Employee' la quale apre la finestra add-employe
     * .fxml e il suo relativo controller NewPersonController
     *
     * @param event
     */
    @FXML
    void handleAddEmployee(ActionEvent event) {
        Connection c = null;
        PreparedStatement statement = null;
        try {
            String firstName, lastName;
            LocalDate birthday;
            Integer id;
            //Carichiamo la pagina per aggiungere un nuovo employee(add-employee.fxml)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("add-employee.fxml"));
            DialogPane view = loader.load();
            NewPersonController controller = loader.getController();

            // Settiamo un Person vuoto nel controller
            controller.setElement(new Person());
            while(true) {
            // Creiamo il dialogo per aggiungere un employee
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("New Employee");
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.setDialogPane(view);

                // Mostriamo il dialogo e aspettiamo che l'utente clicchi un bottone
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    firstName = controller.getElement().getFirstName();
                    lastName = controller.getElement().getLastName();
                    birthday = controller.getElement().getBirthday();
                    //Controlliamo i valori inseriti
                    if (firstName.equals("NULL") || lastName.equals("NULL") || LocalDate.now().getYear() - birthday.getYear() < 18 || (LocalDate.now().getYear() - birthday.getYear() == 18 && LocalDate.now().getMonth().compareTo(birthday.getMonth()) < 0) || (LocalDate.now().getYear() - birthday.getYear() == 18 && LocalDate.now().getDayOfYear() - birthday.getDayOfYear() < 0)) {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Inserimento non corretto!");
                        alert2.setHeaderText("Inserimento non corretto");
                        alert2.setContentText("Hai sbagliato a scrivere il nome o il cognome o la data non è da maggiorenne");
                        alert2.showAndWait();
                    } else {
                        Person newEmployee = controller.getElement();

                        //Aggiungiamo il nuovo employee al database e all'ObservableList
                        try {
                            Class.forName(JDBC_Driver_MySQL);
                            c = DriverManager.getConnection(JDBC_URL_MySQL);
                            statement = c.prepareStatement("INSERT INTO Employee (lastName, firstName, birthday) VALUES (?,?,?);");
                            statement.setString(1, lastName);
                            statement.setString(2, firstName);
                            statement.setString(3, String.valueOf(birthday));
                            statement.executeUpdate();

                            statement = c.prepareStatement("SELECT _id  FROM Employee WHERE lastName = ? AND " +
                                    "firstName =" +
                                    " ? AND birthday = ?");
                            statement.setString(1, lastName);
                            statement.setString(2, firstName);
                            statement.setString(3, String.valueOf(birthday));
                            ResultSet rs = statement.executeQuery();
                            rs.next();
                            id = rs.getInt("_id");
                            newEmployee.setIdPerson(id);
                            employee.add(newEmployee);
                            break;
                        } catch (SQLException e) {
                            Alert alert3 = new Alert(Alert.AlertType.ERROR);
                            alert3.setTitle("ERRORE!");
                            alert3.setHeaderText("Errore SQL");
                            alert3.setContentText("C'è stato un errore nell'SQL");
                            alert3.showAndWait();
                        } catch (ClassNotFoundException e) {
                            Alert alert4 = new Alert(Alert.AlertType.ERROR);
                            alert4.setTitle("ERRORE!");
                            alert4.setHeaderText("Errore nella creazione Class.forName()");
                            alert4.setContentText("C'è stato un errore nella creazione di Class.forName()");
                            alert4.showAndWait();
                        }
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    /***
     * Funzione richiamata quando viene cliccato il pulsante 'Add Client' la quale apre la finestra add-client.fxml e
     * il suo relativo controller NewPersonController
     *
     * @param event
     */
    @FXML
    void handleAddClient(ActionEvent event) {
        Connection c = null;
        PreparedStatement statement = null;
        try {
            String firstName, lastName;
            LocalDate birthday;
            Integer id;
            //Carichiamo la pagina per aggiungere un nuovo client(add-client.fxml)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("add-client.fxml"));
            DialogPane view = loader.load();
            NewPersonController controller = loader.getController();

            // Settiamo un Person vuoto nel controller
            controller.setElement(new Person());
            while(true) {
                // Creiamo il dialogo per aggiungere un nuovo cliente
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("New Client");
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.setDialogPane(view);

                // Mostriamo il dialogo e aspettiamo che l'utente clicchi un bottone
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    firstName = controller.getElement().getFirstName();
                    lastName = controller.getElement().getLastName();
                    birthday = controller.getElement().getBirthday();
                    //Controlliamo i valori inseriti
                    if (firstName.equals("NULL") || lastName.equals("NULL") || LocalDate.now().getYear() - birthday.getYear() < 18 || (LocalDate.now().getYear() - birthday.getYear() == 18 && LocalDate.now().getMonth().compareTo(birthday.getMonth()) < 0) || (LocalDate.now().getYear() - birthday.getYear() == 18 && LocalDate.now().getDayOfYear() - birthday.getDayOfYear() < 0)) {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Inserimento non corretto!");
                        alert2.setHeaderText("Inserimento non corretto");
                        alert2.setContentText("Hai sbagliato a scrivere il nome o il cognome o la data non è da maggiorenne");
                        alert2.showAndWait();
                    } else {
                        Person newClient = controller.getElement();
                        //Aggiungiamo il nuovo client al database e all'ObservableList
                        try {
                            Class.forName(JDBC_Driver_MySQL);
                            c = DriverManager.getConnection(JDBC_URL_MySQL);
                            statement = c.prepareStatement("INSERT INTO Client (lastName, firstName, birthday) VALUES (?,?,?);");
                            statement.setString(1, lastName);
                            statement.setString(2, firstName);
                            statement.setString(3, String.valueOf(birthday));
                            statement.executeUpdate();

                            statement = c.prepareStatement("SELECT _id  FROM Client WHERE lastName = ? AND " +
                                    "firstName =" +
                                    " ? AND birthday = ?");
                            statement.setString(1, lastName);
                            statement.setString(2, firstName);
                            statement.setString(3, String.valueOf(birthday));
                            ResultSet rs = statement.executeQuery();
                            rs.next();
                            id = rs.getInt("_id");
                            newClient.setIdPerson(id);
                            client.add(newClient);


                            break;
                        } catch (SQLException e) {
                            Alert alert3 = new Alert(Alert.AlertType.ERROR);
                            alert3.setTitle("ERRORE!");
                            alert3.setHeaderText("Errore SQL");
                            alert3.setContentText("C'è stato un errore nell'SQL");
                            alert3.showAndWait();
                        } catch (ClassNotFoundException e) {
                            Alert alert4 = new Alert(Alert.AlertType.ERROR);
                            alert4.setTitle("ERRORE!");
                            alert4.setHeaderText("Errore nella creazione Class.forName()");
                            alert4.setContentText("C'è stato un errore nella creazione di Class.forName()");
                            alert4.showAndWait();
                        }
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    /***
     * Funzione richiamata quando viene cliccato il pulsante 'Add Type of Job' la quale apre la finestra add-type.fxml e
     * il suo relativo controller NewTypeController
     *
     * @param event
     */
    @FXML
    void handleAddType(ActionEvent event) {
        Connection c = null;
        PreparedStatement statement = null;
        try {
            String name, description;
            //Carichiamo la pagina per aggiungere un nuovo typeOfJob(add-type.fxml)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("add-type.fxml"));
            DialogPane view = loader.load();
            NewTypeController controller = loader.getController();

            // Settiamo un Type vuoto nel controller
            controller.setElement(new TypeOfJob());
            while(true) {
                //Creiamo il dialogo pe aggiungere un nuovo tipo di lavoro
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("New Type Of Job");
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.setDialogPane(view);

                // Mostriamo il dialogo e aspettiamo che l'utente clicchi un bottone
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    name = controller.getElement().getName();
                    description = controller.getElement().getDescription();
                    //Controlliamo i valori inseriti
                    if (name.equals("")) {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Inserimento non corretto!");
                        alert2.setHeaderText("Inserimento non corretto");
                        alert2.setContentText("Hai sbagliato a scrivere il nome del tipo di lavoro");
                        alert2.showAndWait();
                    } else {
                        TypeOfJob newType = controller.getElement();
                        type.add(newType);
                        //Aggiungiamo il nuovo tipo di lavoro nel database e nell'ObservableList
                        try {
                            Class.forName(JDBC_Driver_MySQL);
                            c = DriverManager.getConnection(JDBC_URL_MySQL);
                            statement = c.prepareStatement("INSERT INTO Type (name, description) VALUES (?,?);");
                            statement.setString(1, name);
                            statement.setString(2, description);
                            statement.executeUpdate();
                            break;
                        } catch (SQLException e) {
                            Alert alert3 = new Alert(Alert.AlertType.ERROR);
                            alert3.setTitle("ERRORE!");
                            alert3.setHeaderText("Errore SQL");
                            alert3.setContentText("C'è stato un errore nell'SQL");
                            alert3.showAndWait();
                        } catch (ClassNotFoundException e) {
                            Alert alert4 = new Alert(Alert.AlertType.ERROR);
                            alert4.setTitle("ERRORE!");
                            alert4.setHeaderText("Errore nella creazione Class.forName()");
                            alert4.setContentText("C'è stato un errore nella creazione di Class.forName()");
                            alert4.showAndWait();
                        }
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    /***
     * Funzione richiamata quando viene cliccato il pulsante 'Add Job' la quale apre la finestra add-job.fxml e
     * il suo relativo controller NewJobController
     *
     * @param event
     */
    @FXML
    void handleAddJob(ActionEvent event) {
        Connection c = null;
        PreparedStatement statement = null;
        try {
            String name, address, nameEmployee, lastnameEmployee, nameClient, lastnameClient;
            Integer idJob, idClient, idEmployee;
            Float size, hours;
            LocalDate dateOfJob;
            //Carichiamo la pagina per aggiungere un nuovo lavoro(add-job.fxml)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("add-job.fxml"));
            DialogPane view = loader.load();
            NewJobController controller = loader.getController();

            // Settiamo un Job vuoto nel controller
            controller.setElement(new Job());
            while(true) {
                // Creazione del dialogo per aggiungere un nuovo lavoro
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("New Job");
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.setDialogPane(view);

                // Mostriamo il dialogo e aspettiamo che l'utente clicchi un bottone
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    name = controller.getElement().getNameType();
                    idClient = controller.getElement().getIdClient();
                    idEmployee = controller.getElement().getIdEmployee();
                    address = controller.getElement().getAddress();
                    size = controller.getElement().getSize();
                    dateOfJob = controller.getElement().getDateOfJob();
                    hours = controller.getElement().getHours();
                    //Controlliamo i valori inseriti
                    if (name.equals("") || idClient == 0 || idEmployee == 0 || address.equals("") || size <= 0 || dateOfJob.equals(null) || hours <= 0) {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Inserimento non corretto!");
                        alert2.setHeaderText("Inserimento non corretto");
                        alert2.setContentText("Hai sbagliato a scrivere qualcosa");
                        alert2.showAndWait();
                    } else {

                        //Aggiungiamo il nuovo lavoro nel database e nell'ObservableList
                        try {
                            Class.forName(JDBC_Driver_MySQL);
                            c = DriverManager.getConnection(JDBC_URL_MySQL);
                            statement = c.prepareStatement(   "SELECT firstName AS 'firstName', " +
                                    "lastName AS 'lastName'  FROM Employee " +
                                    "WHERE _id = ?");
                            statement.setString(1, String.valueOf(idEmployee));
                            ResultSet rs = statement.executeQuery();
                            rs.next();
                            nameEmployee = rs.getString("firstName");
                            lastnameEmployee = rs.getString("lastName");

                            statement = c.prepareStatement(   "SELECT firstName AS 'firstName', " +
                                    "lastName AS 'lastName'  FROM Client " +
                                    "WHERE _id = ?");
                            statement.setString(1, String.valueOf(idClient));
                            rs = statement.executeQuery();
                            rs.next();
                            nameClient = rs.getString("firstName");
                            lastnameClient = rs.getString("lastName");

                            statement = c.prepareStatement("INSERT INTO Job (nameType, idClient, " +
                                    "idEmployee, size, address, dateOfJob, hours) VALUES (?,?,?,?,?,?,?);");
                            statement.setString(1, name);
                            statement.setInt(2, idClient);
                            statement.setInt(3, idEmployee);
                            statement.setFloat(4, size);
                            statement.setString(5, address);
                            statement.setDate(6, Date.valueOf(dateOfJob));
                            statement.setFloat(7, hours);
                            statement.executeUpdate();

                            System.out.println("sono qui1");

                            statement = c.prepareStatement(   "SELECT _id AS 'id' FROM Job AS J WHERE J.nameType = ? " +
                                    "AND J.idClient = ? AND J.idEmployee = ? AND J.size = ? AND J.address = ? AND J.dateOfJob = ? AND J.hours = ?;");
                            statement.setString(1, name);
                            statement.setInt(2, idClient);
                            statement.setInt(3, idEmployee);
                            statement.setFloat(4, size);
                            statement.setString(5, address);
                            statement.setDate(6, Date.valueOf(dateOfJob));
                            statement.setFloat(7, hours);
                            rs = statement.executeQuery();
                            if (rs.next()) {
                                idJob = rs.getInt("id");
                            }else {
                                idJob = -1;
                            }

                            System.out.println("sono qui2");


                            Job newJob = new Job(idJob, name, idClient, nameClient, lastnameClient, idEmployee,
                                    nameEmployee,
                                    lastnameEmployee, dateOfJob, hours, size, address);
                            job.add(newJob);
                            break;
                        } catch (SQLException e) {
                            Alert alert3 = new Alert(Alert.AlertType.ERROR);
                            alert3.setTitle("ERRORE!");
                            alert3.setHeaderText("Errore SQL");
                            alert3.setContentText("C'è stato un errore nell'SQL");
                            alert3.showAndWait();
                        } catch (ClassNotFoundException e) {
                            Alert alert4 = new Alert(Alert.AlertType.ERROR);
                            alert4.setTitle("ERRORE!");
                            alert4.setHeaderText("Errore nella creazione Class.forName()");
                            alert4.setContentText("C'è stato un errore nella creazione di Class.forName()");
                            alert4.showAndWait();
                        }
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    /***
     * Funzione richiamata quando viene cliccato il pulsante 'Delete Employee' la quale apre la finestra delete-employee
     * .fxml e il suo relativo controller DeleteEmployeeController
     *
     * @param event
     */
    @FXML
    void handleDeleteEmployee(ActionEvent event) {
        Connection c = null;
        PreparedStatement statement = null;
        try {
            Integer id;
            //Carichiamo la pagina per eliminare un employee(delete-employee.fxml)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("delete-employee.fxml"));
            DialogPane view = loader.load();
            DeleteEmployeeController controller = loader.getController();

            // Settiamo un Person vuoto nel controller
            controller.setElement(new Person());
            while(true) {
                // Creiamo il dialogo per eliminare un employee
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Delete Employee");
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.setDialogPane(view);

                // Mostriamo il dialogo e aspettiamo che l'utente clicchi un bottone
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    //Ritorniamo l'id della persona da eliminare
                    id = controller.getElement().getIdPerson();
                    //Controlliamo i valori inseriti
                    if (id.equals(0)) {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Inserimento non corretto!");
                        alert2.setHeaderText("Inserimento non corretto");
                        alert2.setContentText("Non hai selezionato un lavoratore, se non ne sono presenti è perché " +
                                "hanno effettuato un lavoro");
                        alert2.showAndWait();
                    } else {
                        Person newPerson = null;
                        for(Person p : employee) {
                            if(p.getIdPerson().equals(id)) {
                                newPerson = p;
                                break;
                            }
                        }
                        //Eliminiamo l'employee dal database e dall'ObservableList
                        employee.remove(newPerson);
                        try {
                            Class.forName(JDBC_Driver_MySQL);
                            c = DriverManager.getConnection(JDBC_URL_MySQL);
                            statement = c.prepareStatement("DELETE FROM Employee\n" + "WHERE _id = " +
                                    "?;");
                            statement.setString(1, String.valueOf(id));
                            statement.executeUpdate();
                            break;
                        } catch (SQLException e) {
                            Alert alert3 = new Alert(Alert.AlertType.ERROR);
                            alert3.setTitle("ERRORE!");
                            alert3.setHeaderText("Errore SQL");
                            alert3.setContentText("C'è stato un errore nell'SQL");
                            alert3.showAndWait();
                        } catch (ClassNotFoundException e) {
                            Alert alert4 = new Alert(Alert.AlertType.ERROR);
                            alert4.setTitle("ERRORE!");
                            alert4.setHeaderText("Errore nella creazione Class.forName()");
                            alert4.setContentText("C'è stato un errore nella creazione di Class.forName()");
                            alert4.showAndWait();
                        }
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    /***
     * Funzione richiamata quando viene cliccato il pulsante 'Delete Client' la quale apre la finestra delete-client
     * .fxml e il suo relativo controller DeleteClientController
     *
     * @param event
     */
    @FXML
    void handleDeleteClient(ActionEvent event) {
        Connection c = null;
        PreparedStatement statement = null;
        try {
            Integer id;
            //Carichiamo la pagina per eliminare un client(delete-client.fxml)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("delete-client.fxml"));
            DialogPane view = loader.load();
            DeleteClientController controller = loader.getController();

            // Settiamo un Person vuoto nel controller
            controller.setElement(new Person());
            while(true) {
                // Creiamo il dialogo per eliminare un client
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Delete Client");
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.setDialogPane(view);

                // Mostriamo il dialogo e aspettiamo che l'utente clicchi un bottone
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    id = controller.getElement().getIdPerson();
                    //Controlliamo i valori inseriti
                    if (id.equals(0)) {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Inserimento non corretto!");
                        alert2.setHeaderText("Inserimento non corretto");
                        alert2.setContentText("Non hai selezionato un cliente, se non ne sono presenti è perché gli è" +
                                " stata fatta una lavorazione");
                        alert2.showAndWait();
                    } else {
                        Person newPerson = null;
                        for(Person p : client) {
                            if(p.getIdPerson().equals(id)) {
                                newPerson = p;
                                break;
                            }
                        }
                        //Eliminiamo il client dal database e dall'ObservableList
                        client.remove(newPerson);
                        try {
                            Class.forName(JDBC_Driver_MySQL);
                            c = DriverManager.getConnection(JDBC_URL_MySQL);
                            statement = c.prepareStatement("DELETE FROM Client\n" + "WHERE _id = " +
                                    "?;");
                            statement.setString(1, String.valueOf(id));
                            statement.executeUpdate();
                            break;
                        } catch (SQLException e) {
                            Alert alert3 = new Alert(Alert.AlertType.ERROR);
                            alert3.setTitle("ERRORE!");
                            alert3.setHeaderText("Errore SQL");
                            alert3.setContentText("C'è stato un errore nell'SQL");
                            alert3.showAndWait();
                        } catch (ClassNotFoundException e) {
                            Alert alert4 = new Alert(Alert.AlertType.ERROR);
                            alert4.setTitle("ERRORE!");
                            alert4.setHeaderText("Errore nella creazione Class.forName()");
                            alert4.setContentText("C'è stato un errore nella creazione di Class.forName()");
                            alert4.showAndWait();
                        }
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    /***
     * Funzione richiamata quando viene cliccato il pulsante 'Delete Type of Job' la quale apre la finestra
     * delete-typr.fxml e il suo relativo controller DeleteTypeController
     *
     * @param event
     */
    @FXML
    void handleDeleteType(ActionEvent event) {
        Connection c = null;
        PreparedStatement statement = null;
        try {
            String name;
            //Carichiamo la pagina per eliminare un tipo di lavoro(delete-type.fxml)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("delete-type.fxml"));
            DialogPane view = loader.load();
            DeleteTypeController controller = loader.getController();

            // Settiamo un Type vuoto nel controller
            controller.setElement(new TypeOfJob());
            while(true) {
                // Creiamo il dialogo per eliminare un tipo di lavoro
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Delete Type");
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.setDialogPane(view);

                // Mostriamo il dialogo e aspettiamo che l'utente clicchi un bottone
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    name = controller.getElement().getName();
                    //Controlliamo i valori inseriti
                    if (name.equals("")) {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Inserimento non corretto!");
                        alert2.setHeaderText("Inserimento non corretto");
                        alert2.setContentText("Non hai selezionato un lavoro, se non ne sono presenti è perché sono " +
                                "stati effettuati ad un qualche cliente");
                        alert2.showAndWait();
                    } else {
                        TypeOfJob newType = null;
                        for(TypeOfJob t : type) {
                            if(t.getName().equals(name)) {
                                newType = t;
                                break;
                            }
                        }
                        //Eliminiamo il tipo di lavoro dal database e dall'ObservableList
                        type.remove(newType);
                        try {
                            Class.forName(JDBC_Driver_MySQL);
                            c = DriverManager.getConnection(JDBC_URL_MySQL);
                            statement = c.prepareStatement("DELETE FROM Type\n" + "WHERE name = " +
                                    "?;");
                            statement.setString(1, name);
                            statement.executeUpdate();
                            break;
                        } catch (SQLException e) {
                            Alert alert3 = new Alert(Alert.AlertType.ERROR);
                            alert3.setTitle("ERRORE!");
                            alert3.setHeaderText("Errore SQL");
                            alert3.setContentText("C'è stato un errore nell'SQL");
                            alert3.showAndWait();
                        } catch (ClassNotFoundException e) {
                            Alert alert4 = new Alert(Alert.AlertType.ERROR);
                            alert4.setTitle("ERRORE!");
                            alert4.setHeaderText("Errore nella creazione Class.forName()");
                            alert4.setContentText("C'è stato un errore nella creazione di Class.forName()");
                            alert4.showAndWait();
                        }
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    /***
     * Funzione richiamata quando viene cliccato il pulsante 'Delete Job' la quale apre la finestra delete-employee
     * .fxml e il suo relativo controller DeleteJobController
     *
     * @param event
     */
    @FXML
    void handleDeleteJob(ActionEvent event) {
        Connection c = null;
        PreparedStatement statement = null;
        try {
            Integer id;
            //Carichiamo la pagina per eliminare un lavoro(delete-job.fxml)
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("delete-job.fxml"));
            DialogPane view = loader.load();
            DeleteJobController controller = loader.getController();

            // Settiamo un Job vuoto nel controller
            controller.setElement(new Job());
            while(true) {
                // Creiamo il dialogo per eliminare un tipo di lavoro
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Delete Job");
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.setDialogPane(view);

                // Mostriamo il dialogo e aspettiamo che l'utente clicchi un bottone
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    id = controller.getElement().getIdJob();
                    System.out.println(id);
                    //Controlliamo i valori inseriti
                    if (id.equals(0)) {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Inserimento non corretto!");
                        alert2.setHeaderText("Inserimento non corretto");
                        alert2.setContentText("Non hai selezionato un lavoro");
                        alert2.showAndWait();
                    } else {
                        Job deleteJob = null;
                        for(Job j : job) {
                            if(j.getIdJob().equals(id)) {
                                deleteJob = j;
                                break;
                            }
                        }
                        //Eliminiamo il lavoro dal database e dall'ObservableList
                        job.remove(deleteJob);
                        try {
                            Class.forName(JDBC_Driver_MySQL);
                            c = DriverManager.getConnection(JDBC_URL_MySQL);
                            statement = c.prepareStatement("DELETE FROM Job\n" + "WHERE _id = " +
                                    "?;");
                            statement.setString(1, String.valueOf(id));
                            statement.executeUpdate();
                            break;
                        } catch (SQLException e) {
                            Alert alert3 = new Alert(Alert.AlertType.ERROR);
                            alert3.setTitle("ERRORE!");
                            alert3.setHeaderText("Errore SQL");
                            alert3.setContentText("C'è stato un errore nell'SQL");
                            alert3.showAndWait();
                        } catch (ClassNotFoundException e) {
                            Alert alert4 = new Alert(Alert.AlertType.ERROR);
                            alert4.setTitle("ERRORE!");
                            alert4.setHeaderText("Errore nella creazione Class.forName()");
                            alert4.setContentText("C'è stato un errore nella creazione di Class.forName()");
                            alert4.showAndWait();
                        }
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    /***
     * Funzione richiamata quando viene cliccato il pulsante 'About' la quale apre la finestra about.fxml
     *
     * @param event
     */
    @FXML
    void handleAbout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("about.fxml"));
            DialogPane view = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("About");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            // Show the dialog and wait until the user closes it
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            while(true) {
                if (clickedButton.orElse(ButtonType.CLOSE) == ButtonType.OK) {
                    break;
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Funzione richiamata quando viene cliccato il pulsante 'Quit' il quale effettua il logout dall'applicazione
     *
     * @param event
     */
    @FXML
    void handleQuit(ActionEvent event) {
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Logout");
        alert1.setHeaderText("Sicuro?");
        alert1.setContentText("Vuoi effettuare il logout?");
        alert1.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) root.getScene().getWindow();
                stage.close();
            }
        });
    }
}



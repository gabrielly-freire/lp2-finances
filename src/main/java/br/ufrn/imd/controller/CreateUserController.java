package br.ufrn.imd.controller;

import java.time.LocalDate;

import br.ufrn.imd.App;
import br.ufrn.imd.model.Person;
import br.ufrn.imd.model.User;
import br.ufrn.imd.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CreateUserController {
    @FXML
    private TextField addressField;

    @FXML
    private DatePicker birthDateField;

    @FXML
    private TextField cpfField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField password1Field;

    @FXML
    private PasswordField password2Field;

    @FXML
    private TextField phoneField;

    @FXML
    private Button submitButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Button cancelButton;

    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        submitButton.setOnAction(e -> create());
        cancelButton.setOnAction(e -> openLoginScreen());
    }

    @FXML
    public void create() {
        String address = addressField.getText();
        LocalDate birthDate = birthDateField.getValue();
        String cpf = cpfField.getText();
        String name = nameField.getText();
        String phone = phoneField.getText();
        String password1 = password1Field.getText();
        String password2 = password2Field.getText();
        String username = usernameField.getText();

        if (address.isEmpty() || birthDate == null || cpf.isEmpty() || name.isEmpty() || phone.isEmpty()
                || password1.isEmpty() || password2.isEmpty() || username.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Informação");
            alert.setHeaderText("Cadastro não realizado");
            alert.setContentText("Preencha todos os campos");
            alert.showAndWait();
            return;
            
        }

        if (!password1.equals(password2)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Informação");
            alert.setHeaderText("Cadastro não realizado");
            alert.setContentText("Senhas diferentes");
            alert.showAndWait();
            return;
        }

        if(userService.findByUsername(username) != null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Informação");
            alert.setHeaderText("Cadastro não realizado");
            alert.setContentText("Usuário já cadastrado");
            alert.showAndWait();
            return;
        }

        Person person = new Person(null, name, cpf, birthDate, address, phone);
        User user = new User(null, username, password2, null);

        userService.create(user, person);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText("Cadastro realizado");
        alert.setContentText("Usuário cadastrado com sucesso");
        alert.showAndWait();
        openLoginScreen();

    }

    private void openLoginScreen() {
        try {
            App.setRoot("login.fxml");
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar usuário");
            alert.setContentText("Detalhes: " + e.getMessage());
            alert.showAndWait();
        }
    }

}

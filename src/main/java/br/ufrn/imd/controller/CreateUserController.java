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

    private UserService userService = new UserService();

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

        if (!password1.equals(password2)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Informação");
            alert.setHeaderText("Cadastro não realizado");
            alert.setContentText("Senhas diferentes");
            alert.showAndWait();
            return;
        }

        Person person = new Person(null, name, cpf, birthDate, address, phone);
        User user = new User(null, username, password2, null);

        try {
            userService.create(user, person);
            App.setRoot("login/login.fxml");
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar usuário");
            alert.setContentText("Detalhes: " + ex.getMessage());
            alert.showAndWait();
        }
    }

}

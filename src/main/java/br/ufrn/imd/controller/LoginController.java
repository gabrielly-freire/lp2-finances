package br.ufrn.imd.controller;

import java.io.IOException;

import br.ufrn.imd.App;
import br.ufrn.imd.service.PasswordService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller class responsible for managing the login screen.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class LoginController {
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    private PasswordService passwordService = new PasswordService();

    @FXML
    public void login() {
       loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Informação");
                alert.setHeaderText("Login não realizado");
                alert.setContentText("Login ou senha não preenchido");
                alert.showAndWait();
                return;
            }
            
            if (passwordService.validateLogin(username, password)) {
                openListBillsScreen();
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Informação");
                alert.setHeaderText("Login não realizado");
                alert.setContentText("Login ou senha incorreta");
                alert.showAndWait();
            }

       });
    }

    @FXML
    private void openCreateUserScreen(ActionEvent event) {
        try {
            App.setRoot("createUser.fxml");

        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    @FXML
    private void openForgetPasswordScreen(ActionEvent event) {
        try {
            App.setRoot("forgetPassword.fxml");

        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    @FXML
    private void openListBillsScreen() {
        try {
            App.setRoot("listBill.fxml");
        } catch (IOException ex) {
            System.out.println("Erro ao carregar a próxima tela: " + ex.getMessage());
        }
    }
}

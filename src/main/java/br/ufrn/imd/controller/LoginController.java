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
 * Controlador responsável pela tela de login.
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

    /**
     * Método que executa o login do usuário.
     */
    @FXML
    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isInputValid(username, password)) {
            if (passwordService.validateLogin(username, password)) {
                openListBillsScreen();
            } else {
                showAlert("Login não realizado", "Login ou senha incorreta");
            }
        } else {
            showAlert("Login não realizado", "Login ou senha não preenchido");
        }
    }

    /**
     * Verifica se o nome de usuário e senha foram preenchidos.
     * 
     * @param username O nome de usuário.
     * @param password A senha.
     * @return true se ambos os campos estiverem preenchidos, caso contrário, false.
     */
    private boolean isInputValid(String username, String password) {
        return !(username.isEmpty() || password.isEmpty());
    }

    /**
     * Exibe um alerta com a mensagem fornecida.
     * 
     * @param header O título do alerta.
     * @param content O conteúdo do alerta.
     */
    private void showAlert(String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Navega para a tela de criação de usuário.
     * 
     * @param event O evento gerado ao clicar no botão de criar usuário.
     */
    @FXML
    private void openCreateUserScreen(ActionEvent event) {
        navigateToScreen("createUser.fxml");
    }

    /**
     * Navega para a tela de recuperação de senha.
     * 
     * @param event O evento gerado ao clicar no botão de esquecer a senha.
     */
    @FXML
    private void openForgetPasswordScreen(ActionEvent event) {
        navigateToScreen("forgetPassword.fxml");
    }

    /**
     * Navega para a tela de listagem de faturas.
     */
    @FXML
    private void openListBillsScreen() {
        navigateToScreen("listBill.fxml");
    }

    /**
     * Método genérico para navegação entre telas.
     * 
     * @param fxmlFile O arquivo FXML da tela de destino.
     */
    private void navigateToScreen(String fxmlFile) {
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela: " + e.getMessage());
        }
    }
}

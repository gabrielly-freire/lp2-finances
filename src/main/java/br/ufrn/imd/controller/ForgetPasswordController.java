package br.ufrn.imd.controller;

import br.ufrn.imd.App;
import br.ufrn.imd.model.User;
import br.ufrn.imd.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ForgetPasswordController {
    @FXML
    private PasswordField password1Field;

    @FXML
    private PasswordField password2Field;

    @FXML
    private Button sumitButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Button cancelButton;

    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        sumitButton.setOnAction(e -> changePassword());
        cancelButton.setOnAction(e -> openLoginScreen());
    }

    @FXML
    public void changePassword() {
        String password1 = password1Field.getText();
        String password2 = password2Field.getText();
        String username = usernameField.getText();

        if (!password1.equals(password2)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Informação");
            alert.setHeaderText("Erro ao alterar senha");
            alert.setContentText("As senhas não coincidem");
            alert.showAndWait();
            return;
        }

        if (password1.isEmpty() || password2.isEmpty() || username.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Informação");
            alert.setHeaderText("Erro ao alterar senha");
            alert.setContentText("Preencha todos os campos");
            alert.showAndWait();
            return;
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Informação");
            alert.setHeaderText("Erro ao alterar senha");
            alert.setContentText("Usuário não encontrado");
            alert.showAndWait();
            return;
        }

        user.setPassword(password1);
        userService.resetPassword(username, password2);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText("Senha alterada com sucesso");
        alert.setContentText("Sua senha foi alterada com sucesso");
        alert.showAndWait();

        openLoginScreen();
    }

    private void openLoginScreen() {
        try {
            App.setRoot("login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

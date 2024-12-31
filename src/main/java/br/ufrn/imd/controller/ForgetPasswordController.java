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

    private UserService userService = new UserService();

    @FXML
    public void changePassword() {
        sumitButton.setOnAction(e -> {
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

            try {
                App.setRoot("login/login.fxml");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
}

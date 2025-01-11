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

/**
 * Controlador responsável pela tela de esquecimento de senha.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
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

    /**
     * Inicializa a tela de esquecimento de senha e configura as ações dos botões.
     */
    @FXML
    public void initialize() {
        sumitButton.setOnAction(e -> changePassword());
        cancelButton.setOnAction(e -> openLoginScreen());
    }

    /**
     * Realiza a alteração da senha do usuário, validando os dados inseridos.
     */
    @FXML
    public void changePassword() {
        String password1 = password1Field.getText();
        String password2 = password2Field.getText();
        String username = usernameField.getText();

        if (isInputInvalid(password1, password2, username)) {
            return;
        }

        if (!password1.equals(password2)) {
            showAlert(AlertType.ERROR, "Erro ao alterar senha", "As senhas não coincidem");
            return;
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            showAlert(AlertType.ERROR, "Erro ao alterar senha", "Usuário não encontrado");
            return;
        }

        updatePassword(user, password1);
        showAlert(AlertType.INFORMATION, "Senha alterada com sucesso", "Sua senha foi alterada com sucesso");
        openLoginScreen();
    }

    /**
     * Valida se os campos de senha e nome de usuário estão preenchidos corretamente.
     * 
     * @param password1 A primeira senha inserida.
     * @param password2 A segunda senha inserida.
     * @param username O nome de usuário inserido.
     * @return Verdadeiro se algum campo estiver inválido, caso contrário falso.
     */
    private boolean isInputInvalid(String password1, String password2, String username) {
        if (password1.isEmpty() || password2.isEmpty() || username.isEmpty()) {
            showAlert(AlertType.ERROR, "Erro ao alterar senha", "Preencha todos os campos");
            return true;
        }
        return false;
    }

    /**
     * Exibe uma mensagem de alerta para o usuário.
     * 
     * @param type O tipo de alerta (informativo ou de erro).
     * @param header O cabeçalho da mensagem.
     * @param content O conteúdo da mensagem.
     */
    private void showAlert(AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle("Informação");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Atualiza a senha do usuário no sistema.
     * 
     * @param user O usuário cuja senha será alterada.
     * @param newPassword A nova senha a ser configurada.
     */
    private void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userService.resetPassword(user.getUsername(), newPassword);
    }

    /**
     * Navega para a tela de login.
     */
    private void openLoginScreen() {
        try {
            App.setRoot("login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

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

/**
 * Controlador responsável pela tela de criação de usuário.
 *
 * @author Gabrielly Freire
 * @version 1.2
 */
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

    /**
     * Inicializa os componentes da tela e define as ações dos botões.
     */
    @FXML
    public void initialize() {
        submitButton.setOnAction(e -> create());
        cancelButton.setOnAction(e -> openLoginScreen());
    }

    /**
     * Cria um novo usuário com base nas entradas do formulário.
     * Realiza validações nos campos e, em caso de sucesso, cria o usuário no banco de dados.
     */
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

        if (!areRequiredFieldsValid(address, birthDate, cpf, name, phone, password1, password2, username)) {
            showError("Cadastro não realizado", "Preencha todos os campos");
            return;
        }

        if (!isPasswordMatching(password1, password2)) {
            showError("Cadastro não realizado", "Senhas diferentes");
            return;
        }

        if (isUsernameAlreadyTaken(username)) {
            showError("Cadastro não realizado", "Usuário já cadastrado");
            return;
        }

        Person person = new Person(null, name, cpf, birthDate, address, phone);
        User user = new User(null, username, password2, null);

        userService.create(user, person);

        showInfo("Cadastro realizado", "Usuário cadastrado com sucesso");
        openLoginScreen();
    }

    /**
     * Valida os campos obrigatórios.
     * 
     * @param address Endereço do usuário.
     * @param birthDate Data de nascimento.
     * @param cpf CPF do usuário.
     * @param name Nome do usuário.
     * @param phone Telefone do usuário.
     * @param password1 Senha do usuário.
     * @param password2 Confirmação da senha.
     * @param username Nome de usuário.
     * @return Verdadeiro se todos os campos obrigatórios estiverem preenchidos, falso caso contrário.
     */
    private boolean areRequiredFieldsValid(String address, LocalDate birthDate, String cpf, String name, String phone, String password1, String password2, String username) {
        return !(address.isEmpty() || birthDate == null || cpf.isEmpty() || name.isEmpty() || phone.isEmpty()
                || password1.isEmpty() || password2.isEmpty() || username.isEmpty());
    }

    /**
     * Verifica se as senhas fornecidas coincidem.
     * 
     * @param password1 A primeira senha.
     * @param password2 A segunda senha.
     * @return Verdadeiro se as senhas coincidirem, falso caso contrário.
     */
    private boolean isPasswordMatching(String password1, String password2) {
        return password1.equals(password2);
    }

    /**
     * Verifica se o nome de usuário já está cadastrado no banco de dados.
     * 
     * @param username O nome de usuário a ser verificado.
     * @return Verdadeiro se o nome de usuário já estiver registrado, falso caso contrário.
     */
    private boolean isUsernameAlreadyTaken(String username) {
        return userService.findByUsername(username) != null;
    }

    /**
     * Exibe uma mensagem de erro para o usuário.
     * 
     * @param title O título do alerta.
     * @param content O conteúdo do alerta.
     */
    private void showError(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("Cadastro não realizado");
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Exibe uma mensagem de sucesso para o usuário.
     * 
     * @param title O título do alerta.
     * @param content O conteúdo do alerta.
     */
    private void showInfo(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Cadastro realizado");
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Navega para a tela de login.
     * 
     * @param e A exceção gerada ao tentar abrir a tela de login, caso ocorra algum erro.
     */
    private void openLoginScreen() {
        try {
            App.setRoot("login.fxml");
        } catch (Exception e) {
            showError("Erro ao criar usuário", "Detalhes: " + e.getMessage());
        }
    }
}

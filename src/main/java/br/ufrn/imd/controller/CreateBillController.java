package br.ufrn.imd.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import br.ufrn.imd.App;
import br.ufrn.imd.model.Bill;
import br.ufrn.imd.model.enums.Category;
import br.ufrn.imd.service.BillService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

/**
 * Controlador responsável pela tela de criação de faturas.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class CreateBillController {

    @FXML
    private ChoiceBox<Category> categoryChoiceBox;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker dueDataField;

    @FXML
    private ToggleButton isPagoToggleButton;

    @FXML
    private DatePicker paymentDataField;

    @FXML
    private Button submitButton;

    @FXML
    private TextField valueField;

    @FXML
    private Button cancelButton;

    private BillService billService = new BillService();

    /**
     * Inicializa os componentes da tela, incluindo a lista de categorias 
     * e o comportamento do botão de toggle de pagamento.
     */
    @FXML
    public void initialize() {
        categoryChoiceBox.getItems().setAll(Category.values());

        isPagoToggleButton.setOnAction(event -> updatePaymentStatusButton());

        submitButton.setOnAction(event -> createBill());
        cancelButton.setOnAction(event -> openListBillsScreen(true));
    }

    /**
     * Cria uma nova fatura com base nas entradas do usuário.
     * Valida os campos obrigatórios, converte o valor para número e 
     * chama o serviço para persistir a fatura.
     * 
     * Caso algum erro seja detectado (como campos obrigatórios vazios ou valor inválido),
     * exibe um alerta de erro.
     */
    @FXML
    public void createBill() {
        String description = descriptionField.getText();
        LocalDate dueData = dueDataField.getValue();
        LocalDate paymentData = paymentDataField.getValue();
        Category category = getCategoryFromChoiceBox();
        boolean isPago = isPagoToggleButton.isSelected();

        Double value = parseValueField();
        if (value == null) {
            showError("Valor inválido", "Por favor, insira um número válido para o campo 'Valor'.");
            return;
        }

        if (!areRequiredFieldsValid(description, dueData, category, value)) {
            showError("Campos obrigatórios", "Preencha todos os campos obrigatórios.");
            return;
        }

        Bill bill = new Bill(null, description, value, dueData, paymentData, isPago, category);

        billService.createBill(bill);
        openListBillsScreen(false);
    }

    /**
     * Valida os campos obrigatórios.
     * 
     * @param description Descrição da fatura.
     * @param dueData Data de vencimento.
     * @param category Categoria da fatura.
     * @param value Valor da fatura.
     * @return Verdadeiro se todos os campos obrigatórios estiverem preenchidos, falso caso contrário.
     */
    private boolean areRequiredFieldsValid(String description, LocalDate dueData, Category category, Double value) {
        return !(description.isEmpty() || dueData == null || category == null || value == null);
    }

    /**
     * Obtém a categoria selecionada na lista de categorias.
     * Lança uma exceção se nenhuma categoria for selecionada.
     * 
     * @return Categoria selecionada.
     */
    private Category getCategoryFromChoiceBox() {
        return Optional.ofNullable(categoryChoiceBox.getValue())
                .orElseThrow(() -> new RuntimeException("Categoria obrigatória"));
    }

    /**
     * Converte o valor do campo de texto para um número decimal (Double).
     * 
     * @return O valor convertido, ou null se o valor não for válido.
     */
    private Double parseValueField() {
        try {
            return Double.parseDouble(valueField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Exibe uma mensagem de erro para o usuário.
     * 
     * @param title O título do alerta.
     * @param content O conteúdo do alerta.
     */
    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Exibe uma mensagem de informação para o usuário.
     * 
     * @param title O título do alerta.
     * @param content O conteúdo do alerta.
     */
    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Navega para a tela de listagem de faturas.
     * 
     * @param isCancelButton Indica se a navegação é devido ao cancelamento da criação.
     */
    private void openListBillsScreen(boolean isCancelButton) {
        try {
            if (!isCancelButton)
                showInfo("Sucesso", "Fatura criada com sucesso!");
            App.setRoot("listBill.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza o texto do botão de toggle para refletir o estado do pagamento da fatura.
     * 
     * Se a fatura estiver paga, o botão mostra "Pago", caso contrário "Não Pago".
     */
    private void updatePaymentStatusButton() {
        String estado = isPagoToggleButton.isSelected() ? "Pago" : "Não Pago";
        isPagoToggleButton.setText(estado);
    }
}

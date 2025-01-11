package br.ufrn.imd.controller;

import java.io.IOException;

import br.ufrn.imd.App;
import br.ufrn.imd.model.Bill;
import br.ufrn.imd.model.enums.Category;
import br.ufrn.imd.service.BillService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

/**
 * Controlador responsável pela tela de atualização de faturas.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class UpdateBillController {

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField valueField;

    @FXML
    private DatePicker dueDataField;

    @FXML
    private DatePicker paymentDataField;

    @FXML
    private ToggleButton isPagoToggleButton;

    @FXML
    private ChoiceBox<Category> categoryChoiceBox;

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;

    private Bill bill;
    private final BillService billService = new BillService();

    /**
     * Inicializa os componentes da tela, configurando os valores iniciais.
     */
    @FXML
    public void initialize() {
        categoryChoiceBox.getItems().setAll(Category.values());
        
        isPagoToggleButton.setOnAction(event -> updatePaymentStatusButton());

        updateButton.setOnAction(event -> updateBill());
        cancelButton.setOnAction(event -> cancelUpdate());
    }

    /**
     * Configura a fatura que será editada, preenchendo os campos com os dados existentes.
     * 
     * @param bill A fatura a ser editada.
     */
    public void setBill(Bill bill) {
        this.bill = bill;
        populateFields();
    }

    /**
     * Preenche os campos da tela com os dados da fatura.
     */
    private void populateFields() {
        descriptionField.setText(bill.getDescription());
        valueField.setText(String.valueOf(bill.getValue()));
        dueDataField.setValue(bill.getDueDate());
        paymentDataField.setValue(bill.getPaymentDate());
        isPagoToggleButton.setText(String.valueOf(bill.getIsPaid()));
        isPagoToggleButton.setSelected(bill.getIsPaid());
        categoryChoiceBox.setValue(bill.getCategory());
    }

    /**
     * Atualiza a fatura no sistema com os dados fornecidos nos campos da tela.
     */
    public void updateBill() {
        try {
            updateBillData();
            billService.updateBill(bill.getId(), bill);
            closeWindow();
            cancelUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Erro ao atualizar a fatura", e.getMessage());
        }
    }

    /**
     * Atualiza os dados da fatura com os valores dos campos de entrada.
     */
    private void updateBillData() {
        bill.setDescription(descriptionField.getText());
        bill.setValue(Double.parseDouble(valueField.getText()));
        bill.setDueDate(dueDataField.getValue());
        bill.setPaymentDate(paymentDataField.getValue());
        bill.setIsPaid(isPagoToggleButton.isSelected());
        bill.setCategory(categoryChoiceBox.getValue());
    }


    /**
     * Exibe um alerta de erro com o título e conteúdo fornecidos.
     * 
     * @param title   O título do alerta.
     * @param content O conteúdo do alerta.
     */
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Fecha a janela atual.
     */
    private void closeWindow() {
        Stage stage = (Stage) updateButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Cancela a atualização da fatura e retorna para a tela de listagem de faturas.
     */
    public void cancelUpdate() {
        try {
            App.setRoot("listBill.fxml");
            closeWindow();
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

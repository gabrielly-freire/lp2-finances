package br.ufrn.imd.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import br.ufrn.imd.App;
import br.ufrn.imd.model.Bill;
import br.ufrn.imd.model.enums.Category;
import br.ufrn.imd.service.BillService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Controlador responsável pela tela de listagem de faturas.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class ListBillsController {

    @FXML
    private TableView<Bill> billsTable;

    @FXML
    private TableColumn<Bill, String> descriptionColumn;

    @FXML
    private TableColumn<Bill, Double> valueColumn;

    @FXML
    private TableColumn<Bill, LocalDate> dueDateColumn;

    @FXML
    private TableColumn<Bill, LocalDate> paymentDateColumn;

    @FXML
    private TableColumn<Bill, Boolean> isPaidColumn;

    @FXML
    private TableColumn<Bill, Category> categoryColumn;

    @FXML
    private TableColumn<Bill, Void> actionsColumn;

    @FXML
    private Button registerBillButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button dashboardButton;

    private final BillService billService = new BillService();

    /**
     * Inicializa a tela de listagem de faturas e configura as colunas da tabela.
     */
    @FXML
    public void initialize() {
        configureTableColumns();
        configureActionsColumn();
        refreshTable();

        registerBillButton.setOnAction(event -> openCreateBillScreen());
        logoutButton.setOnAction(event -> openLoginScreen());
        dashboardButton.setOnAction(event -> openDashboardScreen());
    }

    /**
     * Configura as colunas da tabela com os valores correspondentes.
     */
    private void configureTableColumns() {
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        isPaidColumn.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    /**
     * Configura a coluna de ações, incluindo os botões de atualização e exclusão.
     */
    private void configureActionsColumn() {
        actionsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Bill, Void> call(final TableColumn<Bill, Void> param) {
                return new TableCell<>() {
                    private final Button updateButton = new Button("Update");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox actionButtons = new HBox(10, updateButton, deleteButton);
    
                    {
                        updateButton.getStyleClass().add("update-button");
                        deleteButton.getStyleClass().add("delete-button");
    
                        updateButton.setOnAction(event -> openEditBillScreen(getTableView().getItems().get(getIndex())));
                        deleteButton.setOnAction(event -> deleteBill(getTableView().getItems().get(getIndex())));
                    }
    
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : actionButtons);
                    }
                };
            }
        });
    }
    
    /**
     * Atualiza a tabela com os dados das faturas.
     */
    private void refreshTable() {
        List<Bill> billsList = billService.findAllBills();
        ObservableList<Bill> billsObservable = FXCollections.observableArrayList(billsList);
        billsTable.setItems(billsObservable);
    }

    /**
     * Exclui a fatura selecionada.
     * 
     * @param bill A fatura a ser excluída.
     */
    private void deleteBill(Bill bill) {
        if (bill != null) {
            billService.deleteBill(bill.getId());
            refreshTable();
        }
    }

    /**
     * Navega para a tela de dashboard.
     */
    private void openDashboardScreen() {
        navigateToScreen("dashboard.fxml");
    }

    /**
     * Navega para a tela de criação de fatura.
     */
    private void openCreateBillScreen() {
        navigateToScreen("createBill.fxml");
    }

    /**
     * Navega para a tela de login.
     */
    private void openLoginScreen() {
        navigateToScreen("login.fxml");
    }

    /**
     * Navega para a tela de edição de fatura com as informações da fatura selecionada.
     * 
     * @param bill A fatura a ser editada.
     */
    private void openEditBillScreen(Bill bill) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("updateBill.fxml"));
            Parent root = loader.load();
            UpdateBillController updateBillController = loader.getController();
            updateBillController.setBill(bill);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Fatura");
            stage.showAndWait();

            refreshTable();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navega para uma tela especificada.
     * 
     * @param fxmlFile O arquivo FXML da tela de destino.
     */
    private void navigateToScreen(String fxmlFile) {
        try {
            App.setRoot(fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

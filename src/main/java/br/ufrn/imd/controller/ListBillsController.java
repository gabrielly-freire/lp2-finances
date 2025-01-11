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

    @FXML
    public void initialize() {
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        isPaidColumn.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        actionsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Bill, Void> call(final TableColumn<Bill, Void> param) {
                return new TableCell<>() {
                    private final Button updateButton = new Button("Update");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox actionButtons = new HBox(10, updateButton, deleteButton);

                    {
                        updateButton.setOnAction(event -> {
                            Bill bill = getTableView().getItems().get(getIndex());
                            openEditBillScreen(bill);
                        });

                        deleteButton.setOnAction(event -> {
                            Bill bill = getTableView().getItems().get(getIndex());
                            if (bill != null) {
                                billService.deleteBill(bill.getId());
                                refreshTable();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(actionButtons);
                        }
                    }
                };
            }
        });

        refreshTable();

        registerBillButton.setOnAction(event -> openCreateBillScreen());
        logoutButton.setOnAction(event -> openLoginScreen());
        dashboardButton.setOnAction(event -> openDashboardScreen());
    }

    private void refreshTable() {
        List<Bill> billsList = billService.findAllBills();
        ObservableList<Bill> billsObservable = FXCollections.observableArrayList(billsList);
        billsTable.setItems(billsObservable);
    }

    private void openDashboardScreen() {
        try {
            App.setRoot("dashboard.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openCreateBillScreen() {
        try {
            App.setRoot("createBill.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openLoginScreen() {
        try {
            App.setRoot("login.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
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
}

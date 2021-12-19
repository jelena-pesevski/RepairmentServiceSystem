package controllers;

import data.mysql.IndividualniDataAccess;
import data.mysql.RacunDataAccess;
import data.mysql.RacunStavkaDataAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Individualni;
import models.RacunStavka;

import java.net.URL;
import java.util.ResourceBundle;

public class RacunView implements Initializable {

    private static Integer billId;

    public static Integer getBillId() {
        return billId;
    }

    public static void setBillId(Integer billId) {
        RacunView.billId = billId;
    }

    @FXML
    private Button addBtn;

    @FXML
    private TableView<RacunStavka> table;

    @FXML
    private TableColumn<RacunStavka, String> nameCol;

    @FXML
    private TableColumn<RacunStavka, Double> priceCol;

    @FXML
    private TableColumn<RacunStavka, Integer> amountCol;

    @FXML
    private TableColumn<RacunStavka, Double> totalCol;

    @FXML
    private Label total;

    @FXML
    void ok(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    ObservableList<RacunStavka> list= FXCollections.observableArrayList();

    private void loadData(){
        refreshTable();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("cijenaUkupno"));

        total.setText(RacunDataAccess.getTotal(billId).toString());
    }

    private void refreshTable(){
        list.clear();
        list= RacunStavkaDataAccess.getAll(billId);
        table.setItems(list);
    }

}

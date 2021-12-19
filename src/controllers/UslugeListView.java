package controllers;

import data.mysql.PopravkaUslugaDataAccess;
import data.mysql.UslugeDataAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Usluga;

import java.net.URL;
import java.util.ResourceBundle;

public class UslugeListView implements Initializable {
    public static Usluga selected;

    @FXML
    private Button addBtn;

    @FXML
    private TableView<Usluga> table;

    @FXML
    private TableColumn<Usluga, String> nameCol;

    @FXML
    private TableColumn<Usluga, Double> priceCol;

    @FXML
    private TextField amount;

    @FXML
    void add(ActionEvent event) {
        selected=table.getSelectionModel().getSelectedItem();

        if(selected==null){
            MainPage.showError("Nije odabrana popravka.");
            return;
        }

        String amountInput=amount.getText();
        int k=1;

        if(!amountInput.isEmpty()){
            k=Integer.parseInt(amountInput);
        }

        PopravkaUslugaDataAccess.insert(PopravkeMajstorPage.selected,selected, k);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    ObservableList<Usluga> list= FXCollections.observableArrayList();

    private void loadData(){
        refreshTable();
        nameCol.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("cijena"));
    }

    private void refreshTable(){
        list.clear();
        table.setItems(UslugeDataAccess.getAll("%"));
    }

}

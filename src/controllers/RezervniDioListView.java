package controllers;

import data.mysql.PopravkaRezervniDioDataAccess;
import data.mysql.PopravkaUslugaDataAccess;
import data.mysql.RezervniDioDataAccess;
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
import models.RezervniDio;
import models.Usluga;

import java.net.URL;
import java.util.ResourceBundle;

public class RezervniDioListView implements Initializable {

    public static RezervniDio selected;

    @FXML
    private Button addBtn;

    @FXML
    private TableView<RezervniDio> table;

    @FXML
    private TableColumn<RezervniDio, String> nameCol;

    @FXML
    private TableColumn<RezervniDio, Double> priceCol;

    @FXML
    private TableColumn<RezervniDio, Integer> amountCol;

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

            if(k>selected.getKolicina()){
                MainPage.showError("Ne postoji dovoljno rezervnih dijelova na stanju.");
                return;
            }
        }
        PopravkaRezervniDioDataAccess.insert(PopravkeMajstorPage.selected, selected, k);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    ObservableList<RezervniDio> list= FXCollections.observableArrayList();

    private void loadData(){
        refreshTable();
        nameCol.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
    }

    private void refreshTable(){
        list.clear();
        table.setItems(RezervniDioDataAccess.getAll("%"));
    }
}


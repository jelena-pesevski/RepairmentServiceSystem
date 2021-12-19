package controllers;

import data.mysql.IndividualniDataAccess;
import data.mysql.UslugeDataAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Usluga;
import utils.DBUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UslugePage implements Initializable {

    public static Usluga selected=null;

    @FXML
    private Button individualniBtn;

    @FXML
    private TableView<Usluga> table;

    @FXML
    private TableColumn<Usluga, String> nameCol;

    @FXML
    private TableColumn<Usluga, Double> priceCol;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField pattern;

    @FXML
    private Button showAllBtn;

    @FXML
    private Button searchBtn;

    @FXML
    void addNew(ActionEvent event) {
        try{
            Parent parent= FXMLLoader.load(getClass().getResource("/layouts/addUsluga.fxml"));
            Scene scene=new Scene(parent);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void delete(ActionEvent event) {
        selected=table.getSelectionModel().getSelectedItem();
        if(selected==null){
           MainPage.showError("Usluga nije odabrana.");
            return;
        }

        boolean successful=UslugeDataAccess.deleteUsluga(selected.getIdUsluge());
        if(!successful){
            MainPage.showError("Brisanje neuspjesno, usluga je korištena u popravkama.");
        }else{
            MainPage.showInfo("Usluga uspješno obrisana.");
            refreshTable();
        }
    }

    @FXML
    void nameSearch(ActionEvent event) {
        if(DBUtil.isSearchPatternValid(pattern.getText())){
            refreshTable();
        }else{
            MainPage.showError("Naziv neispravno popunjen.");
        }
    }

    @FXML
    void update(ActionEvent event) {
        selected=table.getSelectionModel().getSelectedItem();
        if(selected==null){
            //poruka da selektuje
            return;
        }

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/layouts/addUsluga.fxml"));
        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddUsluga addUslugaController=loader.getController();
        addUslugaController.setUpdate(true);
        addUslugaController.setTextField(selected.getNaziv(), selected.getCijena().toString());

        Parent parent=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
        refreshTable();
    }

    @FXML
    void showAll(ActionEvent event) {
        pattern.setText("*");
        refreshTable();
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
        table.setItems(UslugeDataAccess.getAll(pattern.getText()));
    }


}

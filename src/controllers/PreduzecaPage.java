package controllers;

import data.mysql.IndividualniDataAccess;
import data.mysql.PreduzecaDataAccess;
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
import models.Operater;
import models.Preduzece;
import utils.DBUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PreduzecaPage implements Initializable {

    public static final String ADD_PRIJAVA_PAGE="/layouts/addPrijavaKvara.fxml";
    public static final String ADD_PREDUZECE_PAGE="/layouts/addPreduzece.fxml";

    public static Preduzece selected=null;

    @FXML
    private Button addPreduzeceBtn;

    @FXML
    private TableView<Preduzece> table;

    @FXML
    private TableColumn<Preduzece, Integer> idCol;

    @FXML
    private TableColumn<Preduzece, String> nazivCol;

    @FXML
    private TableColumn<Preduzece, String> adressCol;

    @FXML
    private TableColumn<Preduzece, String> phoneCol;

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
    private Button addPrijavaBtn;

    @FXML
    void addNew(ActionEvent event) {
        try{
            Parent parent= FXMLLoader.load(getClass().getResource(ADD_PREDUZECE_PAGE));
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
    void addPrijavaKvara(ActionEvent event) {
        if(!(LoginPage.currentUser instanceof Operater)){
            MainPage.showError("Samo operater može dodati prijavu kvara.");
            return;
        }

        selected=table.getSelectionModel().getSelectedItem();
        if(selected==null){
            MainPage.showError("Nije odabran klijent.");
            return;
        }

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(ADD_PRIJAVA_PAGE));
        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddPrijavaKvara addPrijavaKvaraController=loader.getController();
        addPrijavaKvaraController.setCurrentClient(selected);

        Parent parent=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
        refreshTable();
    }

    @FXML
    void delete(ActionEvent event) {
        selected=table.getSelectionModel().getSelectedItem();
        if(selected==null){
            MainPage.showError("Nije selektovan klijent.");
            return;
        }
        boolean success=PreduzecaDataAccess.deletePreduzece(selected.getIdKlijenta());
        if(success){
            MainPage.showInfo("Preduzeće uspješno obrisano.");
            refreshTable();
        }else{
            MainPage.showError("Preduzeće ima prijavljene kvarove, brisanje nije moguće.");
        }
    }

    @FXML
    void nazivSearch(ActionEvent event) {
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
            MainPage.showError("Nije odabran klijent.");
            return;
        }

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/layouts/addPreduzece.fxml"));
        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddPreduzece addPreduzeceController=loader.getController();
        addPreduzeceController.setUpdate(true);
        addPreduzeceController.setTextField(selected.getNaziv(), selected.getAdresa(), selected.getBrojTelefona());

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

    ObservableList<Preduzece> list= FXCollections.observableArrayList();

    private void loadData(){
        refreshTable();

        idCol.setCellValueFactory(new PropertyValueFactory<>("idKlijenta"));
        nazivCol.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        adressCol.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("brojTelefona"));


    }

    private void refreshTable(){
        list.clear();
        table.setItems(PreduzecaDataAccess.getAllPreduzeca(pattern.getText()));
    }


}

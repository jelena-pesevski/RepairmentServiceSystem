package controllers;

import data.mysql.IndividualniDataAccess;
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
import models.Individualni;
import models.Operater;
import utils.DBUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndividualniKlijentiPage implements Initializable {

    public static final String ADD_PRIJAVA_PAGE="/layouts/addPrijavaKvara.fxml";
    public static final String ADD_INDIVIDUALNI_PAGE="/layouts/addIndividualni.fxml";

    public static Individualni selected=null;

    @FXML
    private TableView<Individualni> table;

  @FXML
    private TableColumn<Individualni, Integer> idCol;

    @FXML
    private TableColumn<Individualni, String> firstNameCol;

    @FXML
    private TableColumn<Individualni, String> lastNameCol;

    @FXML
    private TableColumn<Individualni, String> adressCol;

    @FXML
    private TableColumn<Individualni, String> phoneCol;

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
          Parent parent=FXMLLoader.load(getClass().getResource(ADD_INDIVIDUALNI_PAGE));
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
            MainPage.showError("Nije selektovan klijent.");
            return;
        }
        boolean success=IndividualniDataAccess.deleteIndividualni(selected.getIdKlijenta());
        if(success){
            MainPage.showInfo("Klijent uspješno obrisano.");
            refreshTable();
        }else{
            MainPage.showError("Klijent ima prijavljene kvarove, brisanje nije moguće.");
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
    }

    @FXML
    void update(ActionEvent event) {
        selected=table.getSelectionModel().getSelectedItem();
        if(selected==null){
            MainPage.showError("Nije odabran klijent.");
            return;
        }

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(ADD_INDIVIDUALNI_PAGE));
        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddIndividualni addIndividualniController=loader.getController();
        addIndividualniController.setUpdate(true);
        addIndividualniController.setTextField(selected.getIme(), selected.getPrezime(), selected.getAdresa(), selected.getBrojTelefona());

        Parent parent=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
        refreshTable();
    }

    @FXML
    void lastnameSearch(ActionEvent event) {
        if(DBUtil.isSearchPatternValid(pattern.getText())){
          refreshTable();
        }else{
          MainPage.showError("Prezime neispravno popunjeno.");
        }
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

    ObservableList<Individualni> list= FXCollections.observableArrayList();

    private void loadData(){
        refreshTable();

        idCol.setCellValueFactory(new PropertyValueFactory<>("idKlijenta"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("ime"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        adressCol.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("brojTelefona"));
    }

    private void refreshTable(){
        list.clear();
        list=IndividualniDataAccess.getAllIndividualni(pattern.getText());
        table.setItems(list);
    }

}

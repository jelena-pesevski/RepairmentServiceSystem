package controllers;

import data.mysql.IndividualniDataAccess;
import data.mysql.PopravkaDataAccess;
import data.mysql.RacunDataAccess;
import data.mysql.RacunStavkaDataAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Individualni;
import models.Popravka;
import models.Racun;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PopravkeMajstorPage implements Initializable {

    private static final String RACUN_VIEW="/layouts/racunView.fxml";

    public static Popravka selected=null;

    @FXML
    private TableView<Popravka> repairmentsTable;

    @FXML
    private TableColumn<String, Integer> repairCol;

    @FXML
    private TableColumn<String, Integer> reportCol;

    @FXML
    private TableColumn<Popravka, String> descriptionCol;

    @FXML
    private TableColumn<Popravka, String> startDateCol;

    @FXML
    private Button endBtn;

    @FXML
    private Button serviceBtn;

    @FXML
    private Button partBtn;

    @FXML
    void addBackUpPart(ActionEvent event) {
        selected=repairmentsTable.getSelectionModel().getSelectedItem();

        if(selected==null){
            MainPage.showError("Nije odabrana popravka.");
            return;
        }

        try{
            Parent parent= FXMLLoader.load(getClass().getResource("/layouts/rezervniDioList.fxml"));
            Scene scene=new Scene(parent);
            Stage stage=new Stage();
            stage.setTitle("Dodaj rezervni dio");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addService(ActionEvent event) {
        selected=repairmentsTable.getSelectionModel().getSelectedItem();

        if(selected==null){
            MainPage.showError("Nije odabrana popravka.");
            return;
        }

        try{
            Parent parent= FXMLLoader.load(getClass().getResource("/layouts/uslugeListaView.fxml"));
            Scene scene=new Scene(parent);
            Stage stage=new Stage();
            stage.setTitle("Dodaj uslugu");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void endRepairment(ActionEvent event) {
        selected=repairmentsTable.getSelectionModel().getSelectedItem();

        if(selected==null){
            MainPage.showError("Nije odabrana popravka.");
            return;
        }

        //kreiraj racun
        Racun racun=RacunDataAccess.insertRacun(selected.getIdPopravke());
        RacunStavkaDataAccess.insertRacunStavka(racun.getIdRacuna(), selected.getIdPopravke());
        PopravkaDataAccess.endPopravka(selected.getIdPopravke());

        RacunView.setBillId(racun.getIdRacuna());

        //prikazi racun
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(RACUN_VIEW));
        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent parent=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Račun");
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();

        refreshTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    ObservableList<Popravka> list= FXCollections.observableArrayList();

    private void loadData(){
        refreshTable();

        repairCol.setCellValueFactory(new PropertyValueFactory<>("idPopravke"));
        reportCol.setCellValueFactory(new PropertyValueFactory<>("idPrijave"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("opis"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("pocetak"));
    }

    private void refreshTable(){
        list.clear();
        repairmentsTable.setItems(PopravkaDataAccess.getAll(LoginPage.currentUser.getIdZaposlenog()));
    }

}

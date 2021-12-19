package controllers;

import data.mysql.IndividualniDataAccess;
import data.mysql.PopravkaDataAccess;
import data.mysql.PreduzecaDataAccess;
import data.mysql.PrijavaKvaraDataAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Individualni;
import models.Preduzece;
import models.PrijavaKvara;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrijaveKvaraMajstor implements Initializable {

    public static PrijavaKvara selectedIndividualni=null;
    public static PrijavaKvara selectedPreduzece=null;
    private static final String INDIVIDUALNI_INFO="/layouts/individualniInfo.fxml";
    private static final String PREDUZECE_INFO="/layouts/preduzeceInfo.fxml";

    @FXML
    private TableView<PrijavaKvara> individualniTable;

    @FXML
    private TableColumn<PrijavaKvara, Integer> iOperaterCol;

    @FXML
    private TableColumn<PrijavaKvara, String> IdescriptionCol;

    @FXML
    private TableColumn<PrijavaKvara, String> IdateCol;

    @FXML
    private TableView<PrijavaKvara> preduzeceTable;

    @FXML
    private TableColumn<PrijavaKvara, Integer> pOperaterCol;

    @FXML
    private TableColumn<PrijavaKvara, String> PdescriptionCol;

    @FXML
    private TableColumn<PrijavaKvara, String> PdateCol;


    ObservableList<PrijavaKvara> listIndividualni= FXCollections.observableArrayList();
    ObservableList<PrijavaKvara> listPreduzeca= FXCollections.observableArrayList();

    @FXML
    void showInfoIndividualni(ActionEvent event) {
        selectedIndividualni=individualniTable.getSelectionModel().getSelectedItem();
        if(selectedIndividualni==null){
            MainPage.showError("Nije odabran klijent.");
            return;
        }

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(INDIVIDUALNI_INFO));
        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Individualni klijent=IndividualniDataAccess.getIndividualniBasedOnPrijava(selectedIndividualni.getIdPrijave());
        if(klijent==null){  //nece se desiti
            return;
        }
        IndividualniInfo individualniInfo=loader.getController();
        individualniInfo.setTextField(klijent.getIme(), klijent.getPrezime(), klijent.getAdresa(), klijent.getBrojTelefona());

        Parent parent=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Podaci o klijentu");
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
    }

    @FXML
    void showInfoPreduzece(ActionEvent event) {
        selectedPreduzece=preduzeceTable.getSelectionModel().getSelectedItem();
        if(selectedPreduzece==null){
            MainPage.showError("Nije odabran klijent.");
            return;
        }

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(PREDUZECE_INFO));
        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Preduzece klijent= PreduzecaDataAccess.getPreduzeceBasedOnPrijava(selectedPreduzece.getIdPrijave());
        if(klijent==null){  //nece se desiti
            return;
        }
        PreduzeceInfo preduzeceInfo=loader.getController();
        preduzeceInfo.setTextField(klijent.getNaziv(), klijent.getAdresa(), klijent.getBrojTelefona());

        Parent parent=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Podaci o klijentu");
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
    }

    @FXML
    void iStartRepairment(ActionEvent event) {
        selectedIndividualni=individualniTable.getSelectionModel().getSelectedItem();
        if(startRepairment(selectedIndividualni)){
            refreshTableIndividual();
        }
    }

    @FXML
    void pStartRepairment(ActionEvent event) {
        selectedPreduzece=preduzeceTable.getSelectionModel().getSelectedItem();
        if(startRepairment(selectedPreduzece)){
            refreshTableFirms();
        }

    }

    private boolean startRepairment(PrijavaKvara selected) {
      /*  PrijavaKvara selected;
        if (individualniTable.getScene().focusOwnerProperty().get() instanceof TableView) {
            TableView<PrijavaKvara> focused = (TableView) individualniTable.getScene().focusOwnerProperty().get();
            selected=focused.getSelectionModel().getSelectedItem();
            System.out.println(selected);
        }*/
        if(selected==null){
            MainPage.showError("Nije odabrana prijava kvara.");
            return false;
        }
        if("u toku".equals(selected.getStatus())){
            MainPage.showError("Popravka je vec zapoceta.");
            return false;
        }
        boolean success=
            PopravkaDataAccess.insertPopravka(selected.getIdPrijave(), LoginPage.currentUser.getIdZaposlenog());
        if(success){
            PrijavaKvaraDataAccess.updateState(selected.getIdPrijave(), "u toku");
            MainPage.showInfo("Popravka uspjesno zapoceta!");
        }
        return success;
    }

    private void loadDataIndividualni(){
        refreshTableIndividual();

        IdescriptionCol.setCellValueFactory(new PropertyValueFactory<>("opis"));
        IdateCol.setCellValueFactory(new PropertyValueFactory<>("datumPrijave"));
        iOperaterCol.setCellValueFactory(new PropertyValueFactory<>("idZaposlenogOperater"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDataIndividualni();
        loadDataPreduzeca();
    }

    private void loadDataPreduzeca() {
        refreshTableFirms();

        PdescriptionCol.setCellValueFactory(new PropertyValueFactory<>("opis"));
        PdateCol.setCellValueFactory(new PropertyValueFactory<>("datumPrijave"));
        pOperaterCol.setCellValueFactory(new PropertyValueFactory<>("idZaposlenogOperater"));
    }

    private void refreshTableIndividual(){
        listIndividualni.clear();
        individualniTable.setItems(PrijavaKvaraDataAccess.getPrijavaKvaraIndividualni(LoginPage.currentUser.getIdZaposlenog()));
    }

    private void refreshTableFirms(){
        listPreduzeca.clear();
        preduzeceTable.setItems(PrijavaKvaraDataAccess.getPrijavaKvaraPreduzeca(LoginPage.currentUser.getIdZaposlenog()));
    }
}

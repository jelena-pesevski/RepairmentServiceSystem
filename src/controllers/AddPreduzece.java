package controllers;

import data.mysql.IndividualniDataAccess;
import data.mysql.PreduzecaDataAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPreduzece implements Initializable {

    private  boolean update=false;

    public  void setUpdate(boolean update) {
        this.update = update;
    }

    public void setTextField(String nameF, String addressF, String phoneF){
        name.setText(nameF);
        address.setText(addressF);
        phone.setText(phoneF);
    }

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField phone;


    @FXML
    void save(ActionEvent event) {
        String naziv=name.getText();
        String adresa=address.getText();
        String telefon=phone.getText();

        if(naziv.isEmpty()  || adresa.isEmpty() || telefon.isEmpty() ){
            MainPage.showError("Nisu popunjena sva polja.");
        }else{
            if(!update){
                PreduzecaDataAccess.insertPreduzece(naziv, adresa, telefon);
            }else{
                PreduzecaDataAccess.updatePreduzece(PreduzecaPage.selected.getIdKlijenta(),naziv, adresa, telefon);
                update=false;
            }

            Node source = (Node)  event.getSource();
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

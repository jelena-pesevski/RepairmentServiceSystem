package controllers;

import data.mysql.IndividualniDataAccess;
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

public class AddIndividualni implements Initializable {

    private  boolean update=false;

    public  void setUpdate(boolean update) {
        this.update = update;
    }

    public void setTextField(String nameF, String lastnameF, String addressF, String phoneF){
        firstname.setText(nameF);
        lastname.setText(lastnameF);
        address.setText(addressF);
        phone.setText(phoneF);
    }

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField address;

    @FXML
    private TextField phone;

    @FXML
    private Button saveBtn;

    @FXML
    void save(ActionEvent event) {
        String ime=firstname.getText();
        String prezime=lastname.getText();
        String adresa=address.getText();
        String telefon=phone.getText();

        if(ime.isEmpty() || prezime.isEmpty() || adresa.isEmpty() || telefon.isEmpty() ){
            MainPage.showError("Nisu popunjena sva polja.");
        }else{
            if(!update){
                IndividualniDataAccess.insertIndividualni(ime, prezime, adresa, telefon);
            }else{
                IndividualniDataAccess.updateIndividualni(IndividualniKlijentiPage.selected.getIdKlijenta(),ime, prezime, adresa, telefon);
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
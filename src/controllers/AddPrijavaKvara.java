package controllers;

import data.mysql.PreduzecaDataAccess;
import data.mysql.PrijavaKvaraDataAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import models.Klijent;

public class AddPrijavaKvara {

    private Klijent currentClient;

    @FXML
    private TextArea descriptionInput;

    public void setCurrentClient(Klijent k){
        currentClient=k;
    }

    @FXML
    void save(ActionEvent event) {
        String description=descriptionInput.getText();

        boolean success=
        PrijavaKvaraDataAccess.insertPrijavaKvara(description, currentClient.getIdKlijenta(), LoginPage.currentUser.getIdZaposlenog());

        if(success){
            MainPage.showInfo("Prijava kvara je uspjesno evidentirana!");
        }

        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();

    }

}

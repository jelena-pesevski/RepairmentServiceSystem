package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MainPage {

    private static final String I_KLIJENTI_PAGE= "/layouts/individualniKlijentiPage.fxml";
    private static final String P_KLIJENTI_PAGE="/layouts/preduzecaPage.fxml";
    private static final String USLUGE_PAGE="/layouts/uslugePage.fxml";
    private static final String PRIJAVE_PAGE="/layouts/prijaveKvaraMajstorPage.fxml";
    private static final String POPRAVKE_PAGE="/layouts/popravkeMajstorPage.fxml";

    public static Stage secondStage=new Stage();

    @FXML
    void individualniKlijentiClicked(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource(I_KLIJENTI_PAGE));
            secondStage.setTitle("Individualni");
            secondStage.setScene(new Scene(root));
            secondStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void popravkeClicked(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource(POPRAVKE_PAGE));
            secondStage.setTitle("Popravke");
            secondStage.setScene(new Scene(root));
            secondStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void preduzecaClicked(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource(P_KLIJENTI_PAGE));
            secondStage.setTitle("Preduzeća");
            secondStage.setScene(new Scene(root));
            secondStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void uslugeClicked(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource(USLUGE_PAGE));
            secondStage.setTitle("Usluge");
            secondStage.setScene(new Scene(root));
            secondStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void prijaveKvarovaClicked(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource(PRIJAVE_PAGE));
            secondStage.setTitle("Prijave kvarova");
            secondStage.setScene(new Scene(root));
            secondStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showError(String msg){
        Alert alert = new Alert(Alert.AlertType
                .ERROR);
        alert.setTitle("Greska");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void showInfo(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
}

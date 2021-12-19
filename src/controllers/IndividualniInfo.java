package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IndividualniInfo {

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField address;

    @FXML
    private TextField phone;

    @FXML
    private Button okBtn;

    @FXML
    void okClicked(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setTextField(String nameF, String lastnameF, String addressF, String phoneF){
        firstname.setText(nameF);
        lastname.setText(lastnameF);
        address.setText(addressF);
        phone.setText(phoneF);
    }

}
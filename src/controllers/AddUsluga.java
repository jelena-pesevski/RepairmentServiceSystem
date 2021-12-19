package controllers;

import data.mysql.PreduzecaDataAccess;
import data.mysql.UslugeDataAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddUsluga {

    private  boolean update=false;

    public  void setUpdate(boolean update) {
        this.update = update;
    }

    public void setTextField(String nameF, String priceF){
        name.setText(nameF);
        price.setText(priceF);
    }

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private Button saveBtn;

    @FXML
    void save(ActionEvent event) {
        String naziv=name.getText();
        String cijena=price.getText();

        if(naziv.isEmpty()  || cijena.isEmpty() ){
            MainPage.showError("Nisu popunjena sva polja.");
        }else{
            if(!update){
                UslugeDataAccess.insertUsluga(naziv, Double.valueOf(cijena));
            }else{
                UslugeDataAccess.updateUsluga(UslugePage.selected.getIdUsluge(),naziv, Double.valueOf(cijena));
                update=false;
            }
            Node source = (Node)  event.getSource();
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        }

    }

}

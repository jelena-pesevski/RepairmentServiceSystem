package controllers;


import data.mysql.ZaposleniDataAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import models.Majstor;
import models.Operater;
import models.Zaposleni;
import utils.ConnectionPool;


public class LoginPage {

    public static Zaposleni currentUser;
    private static final String MAIN_PAGE="/layouts/mainPage.fxml";

    @FXML
    private Label usernameTxt;

    @FXML
    private Label passwordTxt;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button signInBtn;

    @FXML
    void signIn(ActionEvent event) {
        String uname = username.getText();
        String pass = password.getText();

        currentUser= ZaposleniDataAccess.selectZaposleniUsernamePass(uname, pass);
        if(currentUser!=null){
            //otvori novi prozor
            try {
                System.out.println(currentUser);
                Main.window.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_PAGE));
                Parent root = loader.load();
                Scene newScene = new Scene(root);
                Main.window.setScene(newScene);
                Main.window.setTitle("Servis");
                Main.window.show();

            } catch (Exception e) {
                   e.printStackTrace();
            }
        }else{
            MainPage.showError("Neispravno korisnicko ime ili lozinka");
        }
    }

}
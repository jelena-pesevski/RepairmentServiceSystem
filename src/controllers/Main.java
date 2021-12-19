package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final String LOGIN_PAGE="/layouts/LoginPage.fxml";
    public static Stage window;
    //private static Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception{
        window=primaryStage;
        Parent root= FXMLLoader.load(getClass().getResource(LOGIN_PAGE));
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Uloguj se");
        window.setResizable(false);
        window.show();
     /*   scene=new Scene(loadFXML(LOGIN_PAGE));
        primaryStage.setScene(scene);
        primaryStage.show();*/

    }

    public static void main(String[] args) {
        launch(args);
    }

    static void setRoot(String fxml) throws IOException{
        //scene.setRoot(loadFXML(fxml));
    }


    private static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader loader=new FXMLLoader(Main.class.getResource(fxml));
        return loader.load();
    }

}

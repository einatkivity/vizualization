package View;

import Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class AView {

    static Controller controller;
    static Stage MainStage;
    Stage stage;

    public void setMainStage(Stage stage) {
        this.MainStage = stage;
    }

    public void setStage(Stage stage){
        this.stage=stage;
    }

    public static void setController(Controller controller){
        AView.controller = controller;
    }

    public void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }


    public void closeWindow(){
        stage.close();
    }
}

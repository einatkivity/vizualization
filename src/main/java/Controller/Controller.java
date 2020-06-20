package Controller;

import Model.Model;
import View.AView;
import View.MainView;
import View.MapView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    Model model;
    View.AView view;

    public Controller(MainView website) {
        this.model = new Model(this);
        this.view = website;
    }

    public Model getModel() {
        return model;
    }

    public void openwindow(String fxmlfile, Object Parameter) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getClassLoader().getResource(fxmlfile).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage newStage = new Stage();
        Scene scene = new Scene(root, 1200, 660);
        newStage.setScene(scene);
        newStage.setTitle("Visualization of MSPF project");

        MapView NewWindow = fxmlLoader.getController();
        NewWindow.setController(this);
        NewWindow.setStage(newStage);
        //
        NewWindow.setImageFileNameEmpty("src\\main\\resources\\empty.png");
        NewWindow.setImageFileNameWall("src\\main\\resources\\wall.jpg");
        NewWindow.setImageFileNameTree("src\\main\\resources\\tree.png");
        NewWindow.setImageFileNameArrow("src\\main\\resources\\arrow.png");
        NewWindow.initial();
        newStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        newStage.show();
    }

    public boolean pathsValidation(String mapsFile, String agentsFile){
        //maybe will change but good for now
        return model.pathsValidation(mapsFile,agentsFile);
    }

    public void readFiles(String mapsFile, String agentsFile){
        model.createReaders(mapsFile,agentsFile);
        model.createMap();
        model.createAgentsList();
    }

}

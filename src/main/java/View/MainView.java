package View;

import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MainView extends AView{
    public javafx.scene.control.Button btn_show;
    public javafx.scene.control.Button btn_map;
    public javafx.scene.control.Button btn_agents;
    public TextField txt_map_file;
    public TextField txt_agents_file;
    FileChooser fileChooser= new FileChooser();


    public MainView() {
        String defaultDirectory = "src\\main\\resources\\solutions";
        File path = new File(defaultDirectory);
        fileChooser.setInitialDirectory(path);
    }

    public void showMap() {
        if(controller.pathsValidation(txt_map_file.getText(), txt_agents_file.getText())) {
            controller.readFiles(txt_map_file.getText(),txt_agents_file.getText());
            controller.openwindow("map.fxml", null);
            //closeWindow();
        }
        else {
            showAlert("The map and list of agents you have selected do not match. \n Please try again");
        }
    }

    public void browseButtonForMap (){
        Stage stage = new Stage();
        stage.setTitle("File Chooser");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            txt_map_file.setText(file.getPath());
        }
    }

    public void browseButtonForAgents (){
        Stage stage = new Stage();
        stage.setTitle("File Chooser");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            txt_agents_file.setText(file.getPath());
        }
    }
}
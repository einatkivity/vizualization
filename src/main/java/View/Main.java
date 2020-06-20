package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.Controller;

public class Main extends Application {

    private MainView website;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("mainPage.fxml").openStream());
        primaryStage.setTitle("Visualization of MSPF project");
        Scene scene = new Scene(root, 700, 600);
        primaryStage.setScene(scene);
        website = fxmlLoader.getController();
        website.setMainStage(primaryStage);
        Controller con = new Controller(website);
        AView.setController(con);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

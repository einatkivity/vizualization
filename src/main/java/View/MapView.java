package View;

import Controller.Controller;
import Model.Agents;
import Model.Location;
import Model.Map;
import Model.Point;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class MapView extends AView{

    static Controller controller;
    Stage stage;
    public TextField txt_time;
    public javafx.scene.control.Button btn_specific_agent;
    public javafx.scene.control.Button btn_close;
    public javafx.scene.control.Button btn_specific_time;
    public javafx.scene.control.Button btn_problem;
    public MenuButton agents_list;
    public ImageView img_next_time;
    public ImageView img_prev_time;
    @FXML
    private Canvas canvas;
    private Map map;
    private Agents agents;
    private String empty="";
    private String wall="";
    private String tree="";
    private int agent_id=0;

    public void setStage(Stage stage){
        this.stage=stage;
    }

    public static void setController(Controller controller){
        MapView.controller = controller;
    };

    public void close(){
        closeWindow();
    }

    public void closeWindow(){
        stage.close();
    }


    public void showSpecificTime() {
        initial();
        int time= Integer.valueOf(txt_time.getText());
        img_prev_time.visibleProperty().setValue(true);
        img_next_time.visibleProperty().setValue(true);

        if(time==0){
            img_prev_time.visibleProperty().setValue(false);
        }
        if(time<0) {
            showAlert("The minimum point of time is zero,\n please choose number again");
            return;
        }
        if(time+1==agents.getLargestPathOfAgent()){
            img_next_time.visibleProperty().setValue(false);
        }
        else if(time+1>=agents.getLargestPathOfAgent()) {
            showAlert("At this point of time all of the agents arrive to their destination,\n please choose number again");
            return;
        }
        for (int i = 1; i <= agents.getAgents().size(); i++) {
            HashMap<Integer,Location> specificAgent= agents.getAgents().get(i);
            if(time<specificAgent.size()){
                Location location=specificAgent.get(time);
                drawTime(location,i,""+i);
            }
            else {
                Location location=specificAgent.get(specificAgent.size()-1);
                //Location location2=specificAgent.get(specificAgent.size()-1);
                drawTime(location,i,""+i);
            }
        }
    }

    public void showNextTime(){
        initial();
        img_prev_time.visibleProperty().setValue(true);
        int time= Integer.valueOf(txt_time.getText())+1;
        if(time==agents.getLargestPathOfAgent()) {
            img_next_time.visibleProperty().setValue(false);
            return;
        }
        txt_time.setText(String.valueOf(time));
        showSpecificTime();
    }

    public void showPrevTime(){
        initial();
        img_next_time.visibleProperty().setValue(true);
        int time= Integer.valueOf(txt_time.getText())-1;
        if(time<0) {
            img_prev_time.visibleProperty().setValue(false);
            return;
        }
        txt_time.setText(String.valueOf(time));
        showSpecificTime();
    }

    public void drawTime(Location location, int agentIndex, String state){
        double canvasHeight = canvas.getHeight();
        double canvasWidth = canvas.getWidth();
        double cellHeight = canvasHeight /(map.getHeight()+1);
        double cellWidth = canvasWidth / (map.getWidth()+1);
        double offset_y = cellWidth;
        double offset_x = cellHeight;
        double r;
        double g;
        double b;
        GraphicsContext gc= canvas.getGraphicsContext2D();
        if(agents.getColors().get(agentIndex)!=null){
            r= agents.getColors().get(agentIndex).get(0);
            g=agents.getColors().get(agentIndex).get(1);
            b=agents.getColors().get(agentIndex).get(2);
        }
        else {
            Random random = new Random();
            r = random.nextFloat();
            g = random.nextFloat();
            b = random.nextFloat();
            while ((r==0 && g==0 && b==0)|| (r==255 && g==255 && b==255)){ //make sure that the color is not black or white
                r = random.nextFloat();
                g = random.nextFloat();
                b = random.nextFloat();
            }
            double[] oneColor=new double[3];
            oneColor[0]=r;
            oneColor[1]=g;
            oneColor[2]=b;
            agents.setColors(oneColor);
        }
        ArrayList<Point> points= location.getAllCells();
        for (int i = 0; i < points.size() ; i++) {
            Point point= points.get(i);
            int x= point.getX();
            int y= point.getY();
            gc.setFill(Color.color(r, g, b));
            gc.fillRect(offset_y + y * cellWidth, offset_x + x * cellHeight, cellWidth, cellHeight);
            gc.setFill(Color.BLACK);
            gc.fillText(""+state,offset_y + y * cellWidth+12, offset_x + x * cellHeight+15);
        }
    }

    EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            String agentName= ((MenuItem)e.getSource()).getText();
            agents_list.setText(agentName);
            agent_id= Integer.valueOf(agentName.split("Agent ")[1]);
        }
    };


    public void showSpecificAgent() {
        initial();
        int agent_idx= agent_id;
        if(agent_idx==0) {
            agent_idx=1;
            agent_id=1;
            agents_list.setText("Agent 1");
        }
        HashMap<Integer,Location> specificAgent= agents.getAgents().get(agent_idx);
        drawAgent(specificAgent);
    }

    public void drawAgent(HashMap<Integer,Location> specificAgent ){
        double canvasHeight = canvas.getHeight();
        double canvasWidth = canvas.getWidth();
        double cellHeight = canvasHeight /(map.getHeight()+1);
        double cellWidth = canvasWidth / (map.getWidth()+1);
        GraphicsContext gc= canvas.getGraphicsContext2D();
        Random random=new Random();
        double r= random.nextFloat();
        double g= random.nextFloat();
        double b= random.nextFloat();

        double offset_y = cellWidth;
        double offset_x = cellHeight;

        for (int i = 0; i < specificAgent.size(); i++) {
            Location location = specificAgent.get(i);
            ArrayList<Point> points= location.getAllCells();
            for (int j = 0; j < points.size() ; j++) {
                Point point= points.get(j);
                int x= point.getX();
                int y= point.getY();
                gc.setFill(Color.color(r, g, b));
                gc.fillRect(offset_y + y * cellWidth, offset_x + x * cellHeight, cellWidth, cellHeight);
            }
        }
        for (int i = 0; i <specificAgent.size() ; i++) {
            Location location = specificAgent.get(i);
            ArrayList<Point> points= location.getAllCells();
            for (int j = 0; j < points.size() ; j++) {
                Point point= points.get(j);
                int x= point.getX();
                int y= point.getY();
                if(i==0){
                    gc.setFill(Color.BLACK);
                    gc.fillText("S",offset_y + y * cellWidth+12, offset_x + x * cellHeight+15);
                }
                else if(i==specificAgent.size()-1){
                    gc.setFill(Color.BLACK);
                    gc.fillText("E",offset_y + y * cellWidth+12, x * cellHeight + 34);

                }
            }
        }
    }

    public void initial() {
        this.map=controller.getModel().getMap();
        this.agents= controller.getModel().getAgents();
        double canvasHeight = canvas.getHeight();
        double canvasWidth = canvas.getWidth();
        double cellHeight = canvasHeight / (map.getHeight()+1);
        double cellWidth = canvasWidth / (map.getWidth()+1);
        GraphicsContext gc= canvas.getGraphicsContext2D();
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());

        if(txt_time.getText().equals("")) {
            txt_time.setText("0");
        }
        if(txt_time.getText().equals("0")){
            img_prev_time.visibleProperty().setValue(false);
        }
        try {
            gc.setFill(Color.BLACK);
            Image emptyImg = new Image(new FileInputStream(empty));
            Image wallImg = new Image(new FileInputStream(wall));
            Image treeImg = new Image(new FileInputStream(tree));

            HashMap<Integer,HashMap<Integer,Location>> allAgents =controller.getModel().getAgents().getAgents();

            agents_list.getItems().clear();
            for (int i = 0; i <allAgents.size() ; i++) {
                int counter= i+1;
                MenuItem choice= new MenuItem("Agent "+counter);
                agents_list.getItems().add(choice);
                choice.setOnAction(event1);
            }
            double offset_Y = cellWidth;
            double offset_X = 10;

            for (int i = 0; i < map.getWidth() ; i++) {
                String idx = String.valueOf(i);
                if(i < 10 ){
                    idx = " " + idx;
                }
                // Draw a Text
                gc.strokeText("   " + idx + " ", offset_Y + i*cellWidth, offset_X,cellWidth);
            }
            offset_X = cellHeight;

            for (int X_Value = 0; X_Value < map.getHeight(); X_Value++) {
                // write index
                String idx = String.valueOf(X_Value);
                if(X_Value < 10 ){
                    idx = " " + idx;
                }
                // Draw a Text
                gc.strokeText( "   " + idx + " ", 0, 20 + offset_X + X_Value*cellHeight,cellWidth);

                for (int Y_Value = 0; Y_Value < map.getWidth() ; Y_Value++) {
                    // add images
                    Image img = emptyImg;
                    if (map.getMap()[X_Value].charAt(Y_Value) == '@') {
                        img = wallImg;
                    }else if(map.getMap()[X_Value].charAt(Y_Value) == 'T'){
                        img = treeImg;
                    }
                    gc.drawImage(img, offset_Y + Y_Value * cellWidth, offset_X + X_Value*cellHeight, cellWidth, cellHeight);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showProblem(){
        initial();
        for (int i = 1; i <= agents.getAgents().size(); i++) {
            HashMap<Integer,Location> specificAgent= agents.getAgents().get(i);
            Location startLocation=specificAgent.get(0);
            drawTime(startLocation,i,"s");

            Location endLocation=specificAgent.get(specificAgent.size()-1);
            drawTime(endLocation,i,"e");
        }
    }


    public void setImageFileNameEmpty(String imageFileNameEmpty) {
        //this.ImageFileNameEmpty.set(imageFileNameEmpty);
        this.empty= imageFileNameEmpty;
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        //this.ImageFileNameWall.set(imageFileNameWall);
        this.wall=imageFileNameWall;
    }

    public void setImageFileNameTree(String imageFileNameTree) {
        //this.ImageFileNameTree.set(imageFileNameTree);
        this.tree=imageFileNameTree;
    }

    public void setImageFileNameArrow(String imageFileNameArrow) {
        //this.ImageFileNameArrow.set(imageFileNameArrow);
    }
}

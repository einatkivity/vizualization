package Model;

import java.sql.*;
import java.util.ArrayList;
import Controller.Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Model {

    Controller controller;
    Map map;
    Reader readerMap;
    Reader readerAgents;
    Agents agents;

    public Model(Controller controller) {
        this.controller = controller;
    }

    public Map getMap() {
        return map;
    }

    public Agents getAgents() {
        return agents;
    }

    public void createReaders(String pathMap,String pathAgents){
        readerMap=new Reader();
        readerMap.openFile(pathMap);

        readerAgents=new Reader();
        readerAgents.openFile(pathAgents);
    }

    public void createMap(){
        String map="";
        int skiprows=4;

        while (skiprows>0){
            readerMap.getNextLine();
            skiprows--;
        }

        while (readerMap.getScanner().hasNextLine()){
            map=map+readerMap.getNextLine()+"\n";
        }
        this.map=new Map(map);
    }

    public void createAgentsList(){
        HashMap<Integer,HashMap<Integer,Location>> agentsList=new HashMap<>();
        HashMap<Integer,Location> specificAgent=new HashMap<>();
        int counterOfAgents=0;
        int largestPath=0;
        int width=0;
        int height=0;

        while (readerAgents.getScanner().hasNextLine()){
            String line=readerAgents.getNextLine();
            if(line.contains("Agents")){
                continue;
            }
            else{
                counterOfAgents++;
                String[]splitLine= line.split("\\|");
                for (int i = 0; i <splitLine.length ; i++) {
                    Location location=createLocation(splitLine[i]);
                    specificAgent.put(i,location);
                }
                if(largestPath<splitLine.length){
                    largestPath=splitLine.length;
                }
            }
            agentsList.put(counterOfAgents,specificAgent);
            specificAgent=new HashMap<>();
        }

        this.agents=new Agents(agentsList, largestPath,width,height);
    }

    private Location createLocation(String line) {

        ArrayList<Point> points=new ArrayList<>();

        String[] splitedLine= line.split(";");
        for (int i = 0; i <splitedLine.length ; i++) {
            String[] splitLocation= splitedLine[i].split(",");
            Point point=new Point(Integer.parseInt(splitLocation[0]), Integer.parseInt(splitLocation[1]));
            points.add(point);
        }
        Location location=new Location(points);
        return location;
    }

    public boolean pathsValidation(String mapsFile, String agentsFile){
        //maybe will change but good for now
        if(!(mapsFile.contains("map")) || !(agentsFile.contains("sol")))
            return false;

        String [] splitedPath= mapsFile.split("\\\\");
        if(agentsFile.contains(splitedPath[splitedPath.length-1]))
            return true;

        return false;

    }
}

package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Agents {

    private HashMap<Integer,HashMap<Integer,Location>> agents;
    private int largestPathOfAgent;
    private int agentsWidth;
    private int agentsHeight;
    private HashMap<Integer,ArrayList<Double>> colors;

    public Agents(HashMap<Integer, HashMap<Integer, Location>> agents, int largestPathOfAgent, int agentsWidth, int agentsHeight) {
        this.agents = agents;
        this.largestPathOfAgent = largestPathOfAgent;
        this.agentsWidth = agentsWidth;
        this.agentsHeight = agentsHeight;
        this.colors=new HashMap<>();

    }

    public HashMap<Integer, HashMap<Integer, Location>> getAgents() {
        return agents;
    }


    public int getAgentsWidth() {
        return agentsWidth;
    }

    public int getAgentsHeight() {
        return agentsHeight;
    }


    public int getLargestPathOfAgent() {
        return largestPathOfAgent;
    }

    public void setColors(double[] colors) {
        int index = this.colors.size()+1; //the id of agents start from 1 and not from 0
        ArrayList<Double> agentColor = new ArrayList<>();
        for (int i = 0; i <colors.length ; i++) {
            agentColor.add(colors[i]);
        }
        this.colors.put(index, agentColor);
    }

    public HashMap<Integer, ArrayList<Double>> getColors() {
        return colors;
    }
}

package Model;

import java.util.ArrayList;

public class Location {

    ArrayList<Point> allCells;

    public Location(ArrayList<Point> allCells) {
        this.allCells=allCells;
    }

    public ArrayList<Point> getAllCells() {
        return allCells;
    }

}

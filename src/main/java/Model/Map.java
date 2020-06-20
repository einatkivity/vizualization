package Model;

public class Map {

    private String[] map;
    private int width;
    private int height;

    public Map(String map) {
        String[] splitMap= map.split("\n");
        this.map = splitMap;
        this.width = splitMap[0].length();
        this.height = splitMap.length;
    }

    public String[] getMap() {
        return map;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

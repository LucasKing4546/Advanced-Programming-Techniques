package domain;

public class Courier {
    private String name;
    private String streets;
    private int x_coord;
    private int y_coord;
    private int radius;

    public Courier(String name, String streets, int x_coord, int y_coord, int radius) {
        this.name = name;
        this.streets = streets;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.radius = radius;
    }
    public String getName() {
        return name;
    }
    public String getStreets() {
        return streets;
    }
    public int getX_coord() {
        return x_coord;
    }
    public int getY_coord() {
        return y_coord;
    }
    public int getRadius() {
        return radius;
    }

}

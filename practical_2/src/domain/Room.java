package domain;

public class Room {
    private int number;
    private String type;
    private int price;
    private String description;

    public Room(int number, String type, int price, String description) {
        this.number = number;
        this.type = type;
        this.price = price;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}

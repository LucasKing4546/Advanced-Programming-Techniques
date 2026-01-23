package domain;

public class Car {
    private  int id;
    private String model;
    private String category;
    private int price;
    private boolean status;

    public Car(int id, String model, String category, int price, boolean status) {
        this.id = id;
        this.model = model;
        this.category = category;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}

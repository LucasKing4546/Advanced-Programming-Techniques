package domain;

public class Entity {
    private String category;
    private String name;
    private String sideEffects;

    public Entity(String category, String name, String sideEffects) {
        this.category = category;
        this.name = name;
        this.sideEffects = sideEffects;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", sideEffects='" + sideEffects + '\'' +
                '}';
    }
}

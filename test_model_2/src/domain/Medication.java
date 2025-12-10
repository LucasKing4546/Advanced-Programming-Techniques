package domain;

public class Medication {
    private String category;
    private String name;
    private String sideEffects;

    public Medication(String category, String name, String sideEffects) {
        this.category = category;
        this.name = name;
        this.sideEffects = sideEffects;
    }

    public String getCategory() {
        return category;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", sideEffects='" + sideEffects + '\'' +
                '}';
    }
}

package domain;

public class Entity {
    private int start_time;
    private int end_time;
    private String name;
    private int intensity;
    private String description;

    public Entity(int start_time, int end_time, String name, int intensity, String description) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.name = name;
        this.intensity = intensity;
        this.description = description;
    }

    public int getStart_time() {
        return start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public String getName() {
        return name;
    }

    public int getIntensity() {
        return intensity;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Session {" +
                "start_time = " + start_time +
                ", end_time = " + end_time +
                ", name = '" + name + '\'' +
                ", intensity = " + intensity +
                ", description = '" + description + '\'' +
                '}';
    }
}

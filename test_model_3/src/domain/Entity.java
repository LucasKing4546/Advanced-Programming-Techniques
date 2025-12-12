package domain;

public class Entity {
    private String number;
    private String destination;
    private String airline;
    private int duration;
    private int status;

    public Entity(String number, String destination, String airline, int duration, int status) {
        this.number = number;
        this.destination = destination;
        this.airline = airline;
        this.duration = duration;
        this.status = status;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getNumber() {
        return number;
    }

    public String getDestination() {
        return destination;
    }

    public String getAirline() {
        return airline;
    }

    public int getDuration() {
        return duration;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusFormat(){
        if (status == 0){
            return "Delayed";
        }
        else if (status == 1){
            return "On-time";
        }
        return "Canceled";
    }

    public String minutesToHours(){
        int hours = duration / 60;
        int minutes = duration % 60;
        return hours + "h" + minutes + "m";
    }
}

package domain;

public class Flight {
    private int id;
    private String destination;
    private String departureTime;
    private int seats;
    private int ticket;

    public Flight(int id, String destination, String departureTime, int seats, int ticket) {
        this.id = id;
        this.destination = destination;
        this.departureTime = departureTime;
        this.seats = seats;
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public int getSeats() {
        return seats;
    }

    public int getTicket() {
        return ticket;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", seats=" + seats +
                ", ticket=" + ticket +
                '}';
    }
}

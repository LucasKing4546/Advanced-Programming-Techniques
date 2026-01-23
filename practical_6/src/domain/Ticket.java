package domain;

public class Ticket {
    private int userID;
    private int flightID;
    private int seatNumber;
    private String time;

    public Ticket(int userID, int flightID, int seatNumber, String time) {
        this.userID = userID;
        this.flightID = flightID;
        this.seatNumber = seatNumber;
        this.time = time;
    }

    public int getUserID() {
        return userID;
    }

    public int getFlightID() {
        return flightID;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "userID=" + userID +
                ", flightID=" + flightID +
                ", seatNumber=" + seatNumber +
                ", time='" + time + '\'' +
                '}';
    }
}

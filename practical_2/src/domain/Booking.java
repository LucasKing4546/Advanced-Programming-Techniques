package domain;

import java.util.Date;

public class Booking {
    private int client;
    private int room_number;
    private String start_date;
    private String end_date;

    public Booking(int client, int room_number, String start_date, String end_date) {
        this.client = client;
        this.room_number = room_number;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getClient() {
        return client;
    }

    public int getRoom_number() {
        return room_number;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
}

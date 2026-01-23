package service;

import domain.Booking;
import domain.Room;
import repository.Repository;

import java.time.LocalDate;
import java.util.List;

public class HotelService implements Observer {
    private Repository repository;
    private Runnable onUpdate;

    public HotelService(Repository repository) {
        this.repository = repository;
    }

    public void setOnUpdateCallback(Runnable r) {
        this.onUpdate = r;
    }

    @Override
    public void update(Booking booking) {
        System.out.println("Hotel Staff Notification: New booking confirmed for Room " + booking.getRoom_number());
        if (onUpdate != null) {
            onUpdate.run();
        }
    }

    public int calculateRevenue() {
        String currentDate = LocalDate.now().toString(); // "YYYY-MM-DD"
        List<Booking> allBookings = repository.getBookings(null); // null returns all bookings
        List<Room> rooms = repository.getRooms();

        int total = 0;

        for (Booking b : allBookings) {
            if (b.getStart_date().compareTo(currentDate) >= 0) {

                int pricePerNight = 0;
                for (Room r : rooms) {
                    if (r.getNumber() == b.getRoom_number()) {
                        pricePerNight = r.getPrice();
                        break;
                    }
                }

                total += pricePerNight * getDays(b.getStart_date(), b.getEnd_date());
            }
        }
        return total;
    }

    public List<Booking> getBookingsForRoom(int roomNumber) {
        Room targetRoom = null;
        for (Room r : repository.getRooms()) {
            if (r.getNumber() == roomNumber) {
                targetRoom = r;
                break;
            }
        }
        List<Booking> bookings = repository.getBookings(targetRoom);
        return bookings;
    }

    private int getDays(String start, String end){
        int days = 0;
        String[] start_dates = start.split("-");
        String[] end_dates = end.split("-");
        days = days + 365*(Integer.parseInt(end_dates[0]) - Integer.parseInt(start_dates[0]));
        days = days + 30*(Integer.parseInt(end_dates[2]) - Integer.parseInt(start_dates[2]));
        days = days + (Integer.parseInt(end_dates[1]) - Integer.parseInt(start_dates[1]));
        return days;
    }
}
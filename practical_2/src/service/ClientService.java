package service;

import domain.Booking;
import domain.Room;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class ClientService implements Subject{
    private List<Observer> observers = new ArrayList<>();
    private Repository repository;

    public ClientService(Repository repository) {
        this.repository = repository;
    }

    public String makeBooking(int clientId, String roomType, String startDate, String endDate) {
        List<Room> rooms = repository.getRooms().stream()
                .filter(room -> room.getType().equals(roomType))
                .toList();
        for (Room room : rooms){
            if(repository.isRoomAvailable(room.getNumber(), startDate, endDate)){
                repository.addBooking(clientId, room.getNumber(), startDate, endDate);
                Booking booking = new Booking(clientId, room.getNumber(), startDate, endDate);
                notifyObservers(booking);
                int price = room.getPrice() * getDays(startDate, endDate);
                return "Booking successful! Total price: " + price;
            }
        }
        return "Booking unsuccessful";
    }

    public List<Room> getRooms(){
        return repository.getRooms();
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

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(Booking booking) {
        for (Observer observer : observers){
            observer.update(booking);
        }
    }
}
